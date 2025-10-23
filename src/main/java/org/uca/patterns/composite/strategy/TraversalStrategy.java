package org.uca.patterns.strategy;

import org.uca.model.Person;
import java.util.List;

public interface TraversalStrategy {
    List<Person> traverse(Person person, int maxDepth);
}