package com.kthevathasan.springBootDemo.dao;

import com.kthevathasan.springBootDemo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDAO")
public class FakePersonDataAccessService implements PersonDAO {

    private static List<Person> DB = new ArrayList<Person>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (personMaybe.isPresent()) {
            DB.remove(personMaybe.get());
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id).map(person -> {
            int indexOfPersonPersonToUpdate = DB.indexOf(person);
            if (indexOfPersonPersonToUpdate >= 0) {
                DB.set(indexOfPersonPersonToUpdate, new Person(id, update.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}
