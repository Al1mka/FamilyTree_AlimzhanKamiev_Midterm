package org.uca.cli;

import org.uca.service.FamilyTree;
import org.uca.model.*;
import org.uca.patterns.composite.strategy.DFSTraversal;
import org.uca.patterns.composite.strategy.BFSTraversal;
import org.uca.patterns.composite.strategy.IndentedTreeRenderer;
import org.uca.patterns.composite.strategy.LineRenderer;

import java.util.*;

public class FamilyTreeCLI {
    private final FamilyTree familyTree = new FamilyTree();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Family Tree CLI - Type 'HELP' for commands, 'EXIT' to quit");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                break;
            } else if (input.equalsIgnoreCase("HELP")) {
                showHelp();
            } else {
                processCommand(input);
            }
        }

        scanner.close();
    }

    private void showHelp() {
        System.out.println("""
            Available Commands:
            ADD_PERSON "Full Name" GENDER BIRTH_YEAR [DEATH_YEAR]
            ADD_PARENT_CHILD PARENT_ID CHILD_ID
            MARRY PERSON1_ID PERSON2_ID YEAR
            ANCESTORS PERSON_ID GENERATIONS
            DESCENDANTS PERSON_ID GENERATIONS  
            SIBLINGS PERSON_ID
            CHILDREN PERSON_ID
            SPOUSE PERSON_ID
            SHOW PERSON_ID
            SWITCH_TRAVERSAL DFS|BFS
            SWITCH_RENDERER TREE|LINE
            LIST_ALL
            HELP
            EXIT
            """);
    }

    private void processCommand(String input) {
        try {
            String[] parts = input.split("\\s+");
            String command = parts[0].toUpperCase();

            switch (command) {
                case "ADD_PERSON":
                    handleAddPerson(input);
                    break;
                case "ADD_PARENT_CHILD":
                    if (parts.length != 3) throw new IllegalArgumentException("Usage: ADD_PARENT_CHILD PARENT_ID CHILD_ID");
                    familyTree.addParentChild(parts[1], parts[2]);
                    System.out.println("OK");
                    break;
                case "MARRY":
                    if (parts.length != 4) throw new IllegalArgumentException("Usage: MARRY PERSON1_ID PERSON2_ID YEAR");
                    familyTree.marry(parts[1], parts[2], Integer.parseInt(parts[3]));
                    System.out.println("OK");
                    break;
                case "ANCESTORS":
                    if (parts.length != 3) throw new IllegalArgumentException("Usage: ANCESTORS PERSON_ID GENERATIONS");
                    List<Person> ancestors = familyTree.getAncestors(parts[1], Integer.parseInt(parts[2]));
                    System.out.println(familyTree.renderTree(ancestors, familyTree.getPersonById(parts[1])));
                    break;
                case "DESCENDANTS":
                    if (parts.length != 3) throw new IllegalArgumentException("Usage: DESCENDANTS PERSON_ID GENERATIONS");
                    List<Person> descendants = familyTree.getDescendants(parts[1], Integer.parseInt(parts[2]));
                    System.out.println(familyTree.renderTree(descendants, familyTree.getPersonById(parts[1])));
                    break;
                case "SIBLINGS":
                    if (parts.length != 2) throw new IllegalArgumentException("Usage: SIBLINGS PERSON_ID");
                    List<Person> siblings = familyTree.getSiblings(parts[1]);
                    if (siblings.isEmpty()) {
                        System.out.println("<none>");
                    } else {
                        siblings.forEach(System.out::println);
                    }
                    break;
                case "CHILDREN":
                    if (parts.length != 2) throw new IllegalArgumentException("Usage: CHILDREN PERSON_ID");
                    List<Person> children = familyTree.getChildren(parts[1]);
                    if (children.isEmpty()) {
                        System.out.println("<none>");
                    } else {
                        children.forEach(System.out::println);
                    }
                    break;
                case "SPOUSE":
                    if (parts.length != 2) throw new IllegalArgumentException("Usage: SPOUSE PERSON_ID");
                    Person spouse = familyTree.getSpouse(parts[1]);
                    System.out.println(spouse != null ? spouse : "<none>");
                    break;
                case "SHOW":
                    if (parts.length != 2) throw new IllegalArgumentException("Usage: SHOW PERSON_ID");
                    showPerson(parts[1]);
                    break;
                case "SWITCH_TRAVERSAL":
                    if (parts.length != 2) throw new IllegalArgumentException("Usage: SWITCH_TRAVERSAL DFS|BFS");
                    familyTree.setTraversalStrategy(
                            parts[1].equalsIgnoreCase("BFS") ? new BFSTraversal() : new DFSTraversal()
                    );
                    System.out.println("OK");
                    break;
                case "SWITCH_RENDERER":
                    if (parts.length != 2) throw new IllegalArgumentException("Usage: SWITCH_RENDERER TREE|LINE");
                    familyTree.setRenderer(
                            parts[1].equalsIgnoreCase("LINE") ? new LineRenderer() : new IndentedTreeRenderer()
                    );
                    System.out.println("OK");
                    break;
                case "LIST_ALL":
                    familyTree.getAllPeople().forEach(p ->
                            System.out.println(p.getId() + " | " + p.getFullName() + " | " +
                                    p.getGender() + " | b." + p.getBirthYear()));
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddPerson(String input) {
        // Parse quoted name
        int firstQuote = input.indexOf('"');
        int lastQuote = input.lastIndexOf('"');
        if (firstQuote == -1 || lastQuote == -1) {
            throw new IllegalArgumentException("Name must be quoted");
        }

        String name = input.substring(firstQuote + 1, lastQuote);
        String[] remaining = input.substring(lastQuote + 1).trim().split("\\s+");

        if (remaining.length < 2 || remaining.length > 3) {
            throw new IllegalArgumentException("Usage: ADD_PERSON \"Full Name\" GENDER BIRTH_YEAR [DEATH_YEAR]");
        }

        Gender gender = Gender.valueOf(remaining[0].toUpperCase());
        int birthYear = Integer.parseInt(remaining[1]);
        Integer deathYear = remaining.length > 2 ? Integer.parseInt(remaining[2]) : null;

        PersonNode person = familyTree.addPerson(name, gender, birthYear, deathYear);
        System.out.println("-> " + person.getId());
    }

    private void showPerson(String personId) {
        PersonNode person = familyTree.getPersonById(personId);
        if (person == null) {
            throw new IllegalArgumentException("Person not found: " + personId);
        }

        String spouseInfo = person.getSpouse() != null ?
                "spouse=" + person.getSpouse().getId() : "spouse=<none>";
        String childrenInfo = "children=" + person.getChildrenAsPersons().size();

        System.out.printf("%s | %s | %s | b.%d | %s | %s%n",
                person.getId(), person.getFullName(), person.getGender(),
                person.getBirthYear(), spouseInfo, childrenInfo);
    }

    public FamilyTree getFamilyTree() {
        return familyTree;
    }
}