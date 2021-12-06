package com.dm.interview_service.definition;

public class ContainerDefinition {

    private String id;
    private String name;
    private String splitDefinition;

    public ContainerDefinition(String id, String name, String splitDefinition) {
        this.id = id;
        this.name = name;
        this.splitDefinition = splitDefinition;
    }

    public ContainerDefinition() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSplitDefinition() {
        return splitDefinition;
    }

    public void setSplitDefinition(String splitDefinition) {
        this.splitDefinition = splitDefinition;
    }

    @Override
    public String toString() {
        return "ContainerDefinition{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", splitDefinition='" + splitDefinition + '\'' +
                '}';
    }
}
