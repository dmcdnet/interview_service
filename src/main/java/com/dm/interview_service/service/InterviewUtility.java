package com.dm.interview_service.service;

import com.dm.interview_service.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterviewUtility {

    public static List<InterviewNode> getAllQuestions(Interview interview){
        List<InterviewContainer> nodes = interview.getNodes();
        if(nodes!=null && !nodes.isEmpty()){
            List<InterviewNode> leafs = new ArrayList<>();
            for(InterviewContainer node : interview.getNodes()){
                leafs.addAll(getAllLeafNodes(node));
            }
            return leafs;
        } else {
            return new ArrayList<>();
        }
    }

    private static List<InterviewNode> getAllLeafNodes(InterviewContainer container){
        List<InterviewNode> leafs = new ArrayList<>();
        if(!container.getNodes().isEmpty() && container.getNodes().get(0) instanceof InterviewQuestion){
            leafs.addAll(container.getNodes());
        } else {
            for(InterviewNode node : container.getNodes()){
                leafs.addAll(getAllLeafNodes((InterviewContainer) node));
            }
        }
        return leafs;
    }

    public static InterviewNode counterpartySplitNode(InterviewNode toBeSplit, Counterparty counterparty){
        InterviewNode cpNode = toBeSplit.split(true);

        List<Split> newHistory = new ArrayList<>(toBeSplit.getSplitHistory());
        Split cpSplit = new Split(SplitType.COUNTERPARTY, 1, counterparty);
        cpSplit.setSplit(true);
        newHistory.add(cpSplit);
        cpNode.setSplitHistory(newHistory);
        cpNode.setSplit(cpSplit);

        if(toBeSplit.getParent() instanceof InterviewContainer parent){
            parent.addNode(cpNode);
        }
        return cpNode;
    }

    public static InterviewNode sequenceSplitNode(InterviewNode toBeSplit, SplitRequestSequence splitRequestSequence){
        InterviewNode newNode = toBeSplit.split(true);

        List<Split> base = baseHistory(toBeSplit.getSplitHistory());
        int nextSN = computeNextSequenceNumber(toBeSplit, base);

        Split seqSplit = new Split(SplitType.SEQUENCE, nextSN);
        seqSplit.setSplit(true);

        List<Split> newHistory = new ArrayList<>(base);
        newHistory.add(seqSplit);
        newNode.setSplitHistory(newHistory);
        newNode.setSplit(seqSplit);

        if(toBeSplit.getParent() instanceof InterviewContainer parent){
            parent.addNode(newNode);
        }
        return newNode;
    }

    private static List<Split> baseHistory(List<Split> history) {
        if (!history.isEmpty() && history.get(history.size() - 1).getSplitType() == SplitType.SEQUENCE)
            return history.subList(0, history.size() - 1);
        return history;
    }

    private static int computeNextSequenceNumber(InterviewNode toBeSplit, List<Split> base) {
        if (!(toBeSplit.getParent() instanceof InterviewContainer parent)) return 1;
        return parent.getNodes().stream()
                .filter(n -> n.getId().equals(toBeSplit.getId()))
                .filter(n -> baseHistory(n.getSplitHistory()).equals(base))
                .map(InterviewNode::getSplitHistory)
                .filter(h -> !h.isEmpty() && h.get(h.size() - 1).getSplitType() == SplitType.SEQUENCE)
                .mapToInt(h -> h.get(h.size() - 1).getSequenceNumber())
                .max().orElse(0) + 1;
    }

}
