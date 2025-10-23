package org.uca.service;

import org.uca.model.*;
import org.uca.patterns.strategy.TraversalStrategy;
import org.uca.patterns.composite.strategy.Renderer;
import org.uca.patterns.composite.strategy.DFSTraversal;
import org.uca.patterns.composite.strategy.IndentedTreeRenderer;
import org.uca.patterns.composite.factory.PersonFactory;

import java.util.*;

public class FamilyTree {
    private final Map<String, PersonNode> people = new HashMap<>();
    private TraversalStrategy traversalStrategy = new DFSTraversal();
    private Renderer renderer = new IndentedTreeRenderer();

    public PersonNode addPerson(String fullName, Gender gender, int birthYear, Integer deathYear) {
        PersonNode person = PersonFactory.createPerson(fullName, gender, birthYear, deathYear);
        people.put(person.getId(), person);
        return person;
    }

    public void addParentChild(String parentId, String childId) {
        PersonNode parent = getPerson(parentId);
        PersonNode child = getPerson(childId);
        parent.addChild(child);
    }

    public void marry(String person1Id, String person2Id, int marriageYear) {
        PersonNode person1 = getPerson(person1Id);
        PersonNode person2 = getPerson(person2Id);

        if (person1.getSpouse() != null || person2.getSpouse() != null) {
            throw new IllegalArgumentException("One or both persons are already married");
        }

        person1.setSpouse(person2, marriageYear);
        person2.setSpouse(person1, marriageYear);
    }

    public List<Person> getAncestors(String personId, int generations) {
        PersonNode person = getPerson(personId);
        return getAncestorsHelper(person, generations, new ArrayList<>(), new HashSet<>());
    }

    private List<Person> getAncestorsHelper(PersonNode person, int generations,
                                            List<Person> ancestors, Set<String> visited) {
        if (generations <= 0 || !visited.add(person.getId())) {
            return ancestors;
        }

        ancestors.add(person);

        if (generations > 1) {
            for (Person parent : person.getParents()) {
                getAncestorsHelper((PersonNode) parent, generations - 1, ancestors, visited);
            }
        }

        return ancestors;
    }

    public List<Person> getDescendants(String personId, int generations) {
        PersonNode person = getPerson(personId);
        return traversalStrategy.traverse(person, generations);
    }

    public List<Person> getSiblings(String personId) {
        PersonNode person = getPerson(personId);
        Set<Person> siblings = new HashSet<>();

        for (Person parent : person.getParents()) {
            for (var child : ((PersonNode) parent).getChildrenAsPersons()) {
                if (!child.equals(person)) {
                    siblings.add(child);
                }
            }
        }

        return new ArrayList<>(siblings);
    }

    public List<Person> getChildren(String personId) {
        PersonNode person = getPerson(personId);
        return person.getChildrenAsPersons();
    }

    public Person getSpouse(String personId) {
        PersonNode person = getPerson(personId);
        return person.getSpouse();
    }

    public String renderTree(List<Person> people, Person startPerson) {
        return renderer.render(people, startPerson);
    }

    // Strategy pattern setters
    public void setTraversalStrategy(TraversalStrategy strategy) {
        this.traversalStrategy = strategy;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    private PersonNode getPerson(String id) {
        PersonNode person = people.get(id);
        if (person == null) {
            throw new IllegalArgumentException("Person not found: " + id);
        }
        return person;
    }

    public PersonNode getPersonById(String id) {
        return people.get(id);
    }

    public Collection<PersonNode> getAllPeople() {
        return people.values();
    }
}