package com.dm.interview_service.service;

import com.dm.interview_service.model.Interview;
import com.dm.interview_service.model.InterviewNode;
import com.dm.interview_service.model.SplitRequest;

public class InterviewProcessor {

    public Interview splitNode(Interview interview, SplitRequest request) {
        // find node
        InterviewNode toSplit = interview.getNodeByNodePathReference(request.getNodePath());

        // sequence split node



        return null;
    }



}
