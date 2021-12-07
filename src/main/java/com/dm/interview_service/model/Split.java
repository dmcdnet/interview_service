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

    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }
}
