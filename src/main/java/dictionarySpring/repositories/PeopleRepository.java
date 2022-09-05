package dictionarySpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dictionarySpring.model.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);
}
