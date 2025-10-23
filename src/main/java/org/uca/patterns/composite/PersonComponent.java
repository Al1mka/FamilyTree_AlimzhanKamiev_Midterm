package org.uca.patterns.composite;

import java.util.List;

public interface PersonComponent {
    String getId();
    String getFullName();
    List<PersonComponent> getChildren();
    void addChild(PersonComponent child);
    void removeChild(PersonComponent child);
}