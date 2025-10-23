package org.uca.patterns.composite.factory;

import org.uca.model.Gender;
import org.uca.model.PersonNode;
import org.uca.patterns.composite.builder.PersonBuilder;

public class PersonFactory {
    private static int nextId = 1;

    public static PersonNode createPerson(String fullName, Gender gender, int birthYear, Integer deathYear) {
        String id = "P" + String.format("%03d", nextId++);
        return new PersonBuilder()
                .id(id)
                .fullName(fullName)
                .gender(gender)
                .birthYear(birthYear)
                .deathYear(deathYear)
                .build();
    }

    public static void resetCounter() {
        nextId = 1;
    }
}