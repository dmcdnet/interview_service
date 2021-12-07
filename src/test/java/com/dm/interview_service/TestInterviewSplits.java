package com.dm.interview_service;

import com.dm.interview_service.definition.CaptureDefinition;
import com.dm.interview_service.model.*;
import com.dm.interview_service.service.InterviewDefinitionHandler;
import com.dm.interview_service.service.InterviewProcessor;
import com.dm.interview_service.service.InterviewUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInterviewSplits {

    Interview interview;
    InterviewProcessor interviewProcessor;

    @BeforeAll
    public void createDefinition() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CaptureDefinition definition = mapper.readValue(new File("src//main//resources//definitions//simpleDef1.yaml"), CaptureDefinition.class);
        interview = InterviewDefinitionHandler.generateInterview(definition);

        interviewProcessor = new InterviewProcessor();
    }

    @Test
    public void testSequenceSplit(){
        interviewProcessor.splitNode(interview, generateSequenceSplitRequest());

        // check split has happened
        List<InterviewNode> questions = InterviewUtility.getAllQuestions(interview);
        Assert.assertEquals(questions.stream().filter(node -> node.getId().equals("4")).count(), 2L);

    }

    private SplitRequestSequence generateSequenceSplitRequest(){
        List<String> pathToSplit = new ArrayList<>();
        pathToSplit.add("c2"); pathToSplit.add("g2"); pathToSplit.add("4");
        return new SplitRequestSequence("4", new InterviewNodePath(pathToSplit), true);
    }
}
