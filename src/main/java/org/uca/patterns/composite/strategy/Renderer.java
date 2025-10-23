package org.uca.patterns.composite.strategy;

import org.uca.model.Person;
import java.util.List;

public interface Renderer {
    String render(List<Person> people, Person startPerson);
}