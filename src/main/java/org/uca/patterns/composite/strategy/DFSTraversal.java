package org.uca.patterns.composite.strategy;

import org.uca.model.Person;
import org.uca.model.PersonNode;
import java.util.*;

public class DFSTraversal implements org.uca.patterns.strategy.TraversalStrategy {
    @Override
    public List<Person> traverse(Person person, int maxDepth) {
        List<Person> result = new ArrayList<>();
        traverseDFS(person, maxDepth, 0, result, new HashSet<>());
        return result;
    }

    private void traverseDFS(Person person, int maxDepth, int currentDepth,
                             List<Person> result, Set<String> visited) {
        if (currentDepth > maxDepth || !visited.add(person.getId())) {
            return;
        }

        result.add(person);

        if (currentDepth < maxDepth && person instanceof PersonNode) {
            for (var child : ((PersonNode) person).getChildrenAsPersons()) {
                traverseDFS(child, maxDepth, currentDepth + 1, result, visited);
            }
        }
    }
}