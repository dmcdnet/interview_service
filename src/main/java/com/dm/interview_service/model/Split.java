package com.dm.interview_service.model;

public class Split {

    private SplitType splitType;
    private Integer sequenceNumber = 0;
    private boolean isSplit = false;
    private SplitBasis basis;

    public Split(SplitType splitType) {
        this.splitType = splitType;
    }

    public Split(SplitType splitType, Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        this.splitType = splitType;
    }

    public Split(SplitType splitType, Integer sequenceNumber, SplitBasis basis) {
        this.splitType = splitType;
        this.sequenceNumber = sequenceNumber;
        this.basis = basis;
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

    public SplitBasis getBasis() {
        return basis;
    }

    public void setBasis(SplitBasis basis) {
        this.basis = basis;
    }

    public Split clone(){
        return new Split(this.getSplitType(), this.getSequenceNumber(), this.getBasis());
    }
}
