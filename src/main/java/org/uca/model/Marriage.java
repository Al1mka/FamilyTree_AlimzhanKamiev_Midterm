package org.uca.model;

public class Marriage {
    private final Person person1;
    private final Person person2;
    private final int marriageYear;
    private Integer divorceYear;

    public Marriage(Person person1, Person person2, int marriageYear) {
        this.person1 = person1;
        this.person2 = person2;
        this.marriageYear = marriageYear;
    }

    public void divorce(int divorceYear) {
        if (divorceYear <= marriageYear) {
            throw new IllegalArgumentException("Divorce year must be after marriage year");
        }
        this.divorceYear = divorceYear;
    }

    public boolean isActive() {
        return divorceYear == null;
    }

    // Getters
    public Person getPerson1() { return person1; }
    public Person getPerson2() { return person2; }
    public int getMarriageYear() { return marriageYear; }
    public Integer getDivorceYear() { return divorceYear; }
}