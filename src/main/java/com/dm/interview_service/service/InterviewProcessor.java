package com.dm.interview_service.service;

import com.dm.interview_service.definition.QuestionDefinition;
import com.dm.interview_service.model.*;

public class InterviewProcessor {

    public boolean splitNode(Interview interview, SplitRequestSequence request) {
        // find node
        InterviewNode toSplit = interview.getNodeByNodePathReference(request.getNodePath());

        if(toSplit!=null && toSplit.getSplit().getSplitType().equals(SplitType.SEQUENCE)){
            InterviewUtility.sequenceSplitNode(toSplit, request);
            return true;
        }
        return false;
    }

    public boolean splitNode(Interview interview, SplitRequestCounterparty requestCounterparty){



        return true;
    }



}

