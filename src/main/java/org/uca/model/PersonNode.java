package org.uca.model;

import org.uca.patterns.composite.PersonComponent;
import java.util.*;
import java.util.stream.Collectors;

public class PersonNode extends Person {
    private final List<PersonComponent> children = new ArrayList<>();
    private Person spouse;
    private final List<Person> parents = new ArrayList<>();
    private Marriage marriage;

    public PersonNode(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    // Composite pattern methods
    @Override
    public List<PersonComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void addChild(PersonComponent child) {
        if (child == null) throw new IllegalArgumentException("Child cannot be null");
        if (children.contains(child)) {
            throw new IllegalArgumentException("Child already exists: " + ((Person) child).getId());
        }
        if (isAncestor((Person) child)) {
            throw new IllegalArgumentException("Cannot add child - would create cycle: " + ((Person) child).getId());
        }
        children.add(child);
        if (child instanceof PersonNode) {
            ((PersonNode) child).addParent(this);
        }
    }

    @Override
    public void removeChild(PersonComponent child) {
        children.remove(child);
        if (child instanceof PersonNode) {
            ((PersonNode) child).removeParent(this);
        }
    }

    // Relationship methods
    public void addParent(Person parent) {
        if (parents.size() >= 2) {
            throw new IllegalArgumentException("Cannot have more than 2 parents");
        }
        if (!parents.contains(parent)) {
            parents.add(parent);
        }
    }

    public void removeParent(Person parent) {
        parents.remove(parent);
    }

    public List<Person> getParents() {
        return Collections.unmodifiableList(parents);
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse, int marriageYear) {
        if (this.spouse != null && this.spouse != spouse) {
            throw new IllegalArgumentException("Person already has a spouse");
        }
        this.spouse = spouse;
        this.marriage = new Marriage(this, spouse, marriageYear);
    }

    public void divorce() {
        this.spouse = null;
        this.marriage = null;
    }

    public Marriage getMarriage() {
        return marriage;
    }

    // Cycle detection for composite pattern
    private boolean isAncestor(Person potentialDescendant) {
        return isAncestorHelper(this, potentialDescendant, new HashSet<>());
    }

    private boolean isAncestorHelper(Person ancestor, Person descendant, Set<String> visited) {
        if (!visited.add(descendant.getId())) return false;

        if (descendant.equals(ancestor)) return true;

        for (Person parent : ((PersonNode) descendant).getParents()) {
            if (isAncestorHelper(ancestor, parent, visited)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to get children as Person objects
    public List<Person> getChildrenAsPersons() {
        return children.stream()
                .map(child -> (Person) child)
                .collect(Collectors.toList());
    }
}