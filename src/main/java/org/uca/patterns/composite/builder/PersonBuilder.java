package org.uca.patterns.composite.builder;

import org.uca.model.Gender;
import org.uca.model.PersonNode;

public class PersonBuilder {
    private String id;
    private String fullName;
    private Gender gender;
    private int birthYear;
    private Integer deathYear;

    public PersonBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PersonBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PersonBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder birthYear(int birthYear) {
        this.birthYear = birthYear;
        return this;
    }

    public PersonBuilder deathYear(Integer deathYear) {
        this.deathYear = deathYear;
        return this;
    }

    public PersonNode build() {
        if (id == null || fullName == null || gender == null) {
            throw new IllegalStateException("Missing required fields");
        }
        return new PersonNode(id, fullName, gender, birthYear, deathYear);
    }
}