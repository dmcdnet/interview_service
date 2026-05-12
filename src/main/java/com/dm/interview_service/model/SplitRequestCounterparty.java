package com.dm.interview_service.model;

public class SplitRequestCounterparty extends SplitRequest {

    private Counterparty counterparty;

    public SplitRequestCounterparty(String idToSplit, InterviewNodePath nodePath, Counterparty counterparty) {
        super(idToSplit, nodePath);
        this.counterparty = counterparty;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }
}
