package com.dm.interview_service.model;

public class Split {

    private SplitType splitType;
    private Integer sequenceNumber = 0;
    private boolean isSplit = false;

    public Split(SplitType splitType) {
        this.splitType = splitType;
    }

    public Split(SplitType splitType, Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        this.splitType = splitType;
    }

    public boolean isSplit() {
        return isSplit;
    }

    public void setSplit(boolean split) {
        isSplit = split;
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

    public Split clone(){
        return new Split(this.getSplitType(), this.getSequenceNumber());
    }
}
