package com.dm.interview_service.service;

import com.dm.interview_service.definition.CaptureDefinition;
import com.dm.interview_service.definition.ContainerDefinition;
import com.dm.interview_service.definition.QuestionDefinition;
import com.dm.interview_service.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InterviewDefinitionHandler {

    public static Interview generateInterview(CaptureDefinition captureDefinition){
        Interview interview = new Interview();
        interview.setId(captureDefinition.getId());
        interview.setName(captureDefinition.getName());

        captureDefinition.getQuestions().forEach(questionDefinition -> generatePath(interview, questionDefinition));

        return interview;
    }

    private static Interview generatePath(Interview interview, QuestionDefinition questionDefinition){
        List<ContainerDefinition> path = questionDefinition.getPath();
        InterviewContainer topLevel = interview.getNodeByID(path.get(0).getId());
        if(topLevel==null){
            topLevel = new InterviewContainer(path.get(0).getId(), generateSplit(path.get(0).getSplitDefinition()), new ArrayList<>());
            interview.addNode(topLevel);
        }
        return generatePath(interview, topLevel, questionDefinition.getPath().subList(1, questionDefinition.getPath().size()),
                new InterviewQuestion(questionDefinition.getId(), generateSplit(questionDefinition.getSplitDefinition()), questionDefinition.getQuestion(), new HashSet<>()));
    }

    private static Interview generatePath(Interview interview, InterviewContainer container, List<ContainerDefinition> path, InterviewQuestion question){
        InterviewContainer newContainer = (InterviewContainer) container.childExists(path.get(0).getId());
        if(newContainer==null){
            newContainer = new InterviewContainer(path.get(0).getId(), generateSplit(path.get(0).getSplitDefinition()), new ArrayList<>());
            container.addNode(newContainer);
        }
        newContainer.addNode(question);
        if(path.size()==1){
            return interview;
        }
        return generatePath(interview, newContainer, path.subList(1, path.size()), question);
    }

    private static Split generateSplit(String definition){
        return switch (definition){
            case "SEQUENCE" -> new Split(SplitType.SEQUENCE);
            default -> null;
        };
    }

}
