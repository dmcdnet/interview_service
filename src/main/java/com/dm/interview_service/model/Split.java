package com.dm.interview_service.model;

public class Split {

    private SplitType splitType;
    private Integer sequenceNumber;

    public Split(SplitType splitType) {
        this.splitType = splitType;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
