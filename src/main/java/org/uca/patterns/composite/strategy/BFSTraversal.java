package org.uca.patterns.composite.strategy;

import org.uca.model.Person;
import org.uca.model.PersonNode;
import java.util.*;

public class BFSTraversal implements org.uca.patterns.strategy.TraversalStrategy {
    @Override
    public List<Person> traverse(Person person, int maxDepth) {
        List<Person> result = new ArrayList<>();
        Queue<Person> queue = new LinkedList<>();
        Queue<Integer> depthQueue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(person);
        depthQueue.offer(0);
        visited.add(person.getId());

        while (!queue.isEmpty()) {
            Person current = queue.poll();
            int depth = depthQueue.poll();

            result.add(current);

            if (depth < maxDepth && current instanceof PersonNode) {
                for (var child : ((PersonNode) current).getChildrenAsPersons()) {
                    if (visited.add(child.getId())) {
                        queue.offer(child);
                        depthQueue.offer(depth + 1);
                    }
                }
            }
        }
        return result;
    }
}