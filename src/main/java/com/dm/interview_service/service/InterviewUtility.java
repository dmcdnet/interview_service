package com.dm.interview_service.service;

import com.dm.interview_service.model.*;

import java.util.ArrayList;
import java.util.List;

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

    public static InterviewNode sequenceSplitNode(InterviewNode toBeSplit, SplitRequestSequence splitRequestSequence){


        return null;
    }

}
