package org.uca.model;

import org.uca.patterns.composite.PersonComponent;
import java.util.*;

public abstract class Person implements PersonComponent {
    private final String id;
    private String fullName;
    private Gender gender;
    private int birthYear;
    private Integer deathYear;

    protected Person(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        validateYears(birthYear, deathYear);
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    private void validateYears(int birthYear, Integer deathYear) {
        int currentYear = java.time.Year.now().getValue();
        if (birthYear < 1000 || birthYear > currentYear) {
            throw new IllegalArgumentException("Invalid birth year: " + birthYear);
        }
        if (deathYear != null && (deathYear < birthYear || deathYear > currentYear)) {
            throw new IllegalArgumentException("Invalid death year: " + deathYear);
        }
    }

    // Getters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public Gender getGender() { return gender; }
    public int getBirthYear() { return birthYear; }
    public Integer getDeathYear() { return deathYear; }

    // Setters with validation
    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.fullName = fullName.trim();
    }

    public void setBirthYear(int birthYear) {
        validateYears(birthYear, this.deathYear);
        this.birthYear = birthYear;
    }

    public void setDeathYear(Integer deathYear) {
        validateYears(this.birthYear, deathYear);
        this.deathYear = deathYear;
    }

    // Computed methods
    public boolean isAlive() {
        return deathYear == null;
    }

    public int ageIn(int year) {
        if (year < birthYear) return 0;
        if (deathYear != null && year > deathYear) {
            return deathYear - birthYear;
        }
        return year - birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s %s (b.%d%s)", id, fullName, birthYear,
                deathYear != null ? " d." + deathYear : "");
    }
}
