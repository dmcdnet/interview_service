package com.dm.interview_service.service;

import com.dm.interview_service.model.*;

import java.util.List;

public class InterviewProcessor {

    public boolean splitNode(Interview interview, SplitRequestSequence request) {
        InterviewNode toSplit = interview.getNodeByNodePathReference(request.getNodePath());
        if (toSplit == null) return false;

        boolean eligible = (toSplit.getSplit() != null && toSplit.getSplit().getSplitType() == SplitType.SEQUENCE)
                || !toSplit.getSplitHistory().isEmpty();
        if (!eligible) return false;

        InterviewUtility.sequenceSplitNode(toSplit, request);
        return true;
    }

    public boolean splitNode(Interview interview, SplitRequestCounterparty requestCounterparty){
        InterviewNode toSplit = interview.getNodeByNodePathReference(requestCounterparty.getNodePath());
        if (toSplit == null) return false;

        boolean eligible = (toSplit.getSplit() != null && toSplit.getSplit().getSplitType() == SplitType.COUNTERPARTY)
                || !toSplit.getSplitHistory().isEmpty();
        if (!eligible) return false;

        // A node that already has a CP entry in its chain cannot be CP-split again
        boolean alreadyHasCP = toSplit.getSplitHistory().stream()
                .anyMatch(s -> s.getSplitType() == SplitType.COUNTERPARTY);
        if (alreadyHasCP) return false;

        // Guard: only one CP copy allowed per node at this chain depth
        if (toSplit.getParent() instanceof InterviewContainer parent) {
            List<Split> toSplitHistory = toSplit.getSplitHistory();
            boolean cpCopyExists = parent.getNodes().stream().anyMatch(n ->
                    n.getId().equals(toSplit.getId())
                    && n.getSplitHistory().size() == toSplitHistory.size() + 1
                    && n.getSplitHistory().subList(0, toSplitHistory.size()).equals(toSplitHistory)
                    && n.getSplitHistory().get(n.getSplitHistory().size() - 1).getSplitType() == SplitType.COUNTERPARTY);
            if (cpCopyExists) return false;
        }

        InterviewUtility.counterpartySplitNode(toSplit, requestCounterparty.getCounterparty());
        return true;
    }



}

