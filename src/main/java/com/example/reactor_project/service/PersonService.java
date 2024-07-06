package com.example.reactor_project.service;

import com.example.reactor_project.entity.Person;
import com.example.reactor_project.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {


    private final PersonRepository personRepository;

    public Flux<Person> getAll(){
        return personRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Person> getById(final String id){
        return personRepository.findById(id);
    }

    public Mono save(final Person person) {
        return personRepository.save(person);
    }

    public Mono update(final String id, final Person person){
        return personRepository.findById(id).flatMap(existingPerson -> {
            existingPerson.setSex(person.getSex());
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setAge(person.getAge());
            existingPerson.setInterests(person.getInterests());
            existingPerson.setEmail(person.getEmail());
            return personRepository.save(existingPerson);
        });
    }

    public Mono delete(final String id){
        final Mono<Person> dbPerson = personRepository.findById(id);
        if(Objects.isNull(dbPerson)){
            return Mono.empty();
        }

        return personRepository.findById(id).switchIfEmpty(Mono.empty())
                .filter(Objects::nonNull)
                .flatMap(deletePerson -> personRepository.delete(deletePerson)
                        .then(Mono.just(deletePerson)));
    }

}
