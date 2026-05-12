package com.dm.interview_service.model;

public class Counterparty implements SplitBasis {

    private String id;
    private String name;

    public Counterparty(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
