package org.uca.patterns.composite.strategy;

import org.uca.model.Person;
import org.uca.model.PersonNode;
import java.util.*;

public class IndentedTreeRenderer implements Renderer {
    @Override
    public String render(List<Person> people, Person startPerson) {
        StringBuilder sb = new StringBuilder();
        renderHelper(startPerson, people, 0, sb, new HashSet<>());
        return sb.toString();
    }

    private void renderHelper(Person person, List<Person> allPeople, int level,
                              StringBuilder sb, Set<String> rendered) {
        if (!rendered.add(person.getId())) return;

        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append("- ").append(person).append("\n");

        if (person instanceof PersonNode) {
            // Find and render children
            for (var child : ((PersonNode) person).getChildrenAsPersons()) {
                if (allPeople.contains(child)) {
                    renderHelper(child, allPeople, level + 1, sb, rendered);
                }
            }
        }
    }
}