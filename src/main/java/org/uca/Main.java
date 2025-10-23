package org.uca;

import org.uca.cli.FamilyTreeCLI;
import org.uca.model.Gender;

public class Main {
    public static void main(String[] args) {
        FamilyTreeCLI cli = new FamilyTreeCLI();

        // Add some test data
        try {
            cli.getFamilyTree().addPerson("Aizada Toktobekova", Gender.FEMALE, 1975, null);
            cli.getFamilyTree().addPerson("Nurlan Shaidullaev", Gender.MALE, 1983, null);
            cli.getFamilyTree().addPerson("Aman Shaidullaev", Gender.MALE, 2010, null);
            cli.getFamilyTree().marry("P001", "P002", 2009);
            cli.getFamilyTree().addParentChild("P001", "P003");
            cli.getFamilyTree().addParentChild("P002", "P003");

            System.out.println("Test data loaded successfully!");
            System.out.println("P001: Aizada Toktobekova");
            System.out.println("P002: Nurlan Shaidullaev");
            System.out.println("P003: Aman Shaidullaev");
            System.out.println();

        } catch (Exception e) {
            System.out.println("Setup error: " + e.getMessage());
        }

        cli.start();
    }
}