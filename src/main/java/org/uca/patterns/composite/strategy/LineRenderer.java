package org.uca.patterns.composite.strategy;

import org.uca.model.Person;
import java.util.List;
import java.util.stream.Collectors;

public class LineRenderer implements Renderer {
    @Override
    public String render(List<Person> people, Person startPerson) {
        return people.stream()
                .map(Person::toString)
                .collect(Collectors.joining("\n"));
    }
}
