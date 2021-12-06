package com.dm.interview_service;

import com.dm.interview_service.definition.CaptureDefinition;
import com.dm.interview_service.definition.ContainerDefinition;
import com.dm.interview_service.definition.QuestionDefinition;
import com.dm.interview_service.model.*;
import com.dm.interview_service.service.InterviewDefinitionHandler;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBasicStructure {

    Interview interview;
    CaptureDefinition definition;

    @BeforeAll
    public void buildBasicStructure(){
        // -- GROUP 1 -- START
        Answer ans1 = new Answer("ans1", "Bla de Bla");
        Answer ans2 = new Answer("ans2", "Bla de Bla Two");
        Set<Answer> ansSet1 = new HashSet<>(); ansSet1.add(ans1); ansSet1.add(ans2);
        InterviewQuestion quest1 = new InterviewQuestion("q1", "Question 1", ansSet1);

        Answer ans3 = new Answer("ans3", "Bla de Bla Three");
        Answer ans4 = new Answer("ans4", "Bla de Bla Four");
        Set<Answer> ansSet2 = new HashSet<>(); ansSet2.add(ans3); ansSet2.add(ans4);
        InterviewQuestion quest2 = new InterviewQuestion("q2", "Question 2", ansSet2);

        List<InterviewNode> list1 = new ArrayList<>();
        list1.add(quest1);
        list1.add(quest2);
        InterviewContainer cont1 = new InterviewContainer("cont1", list1);
        // -- GROUP 1 -- END

        // -- GROUP 2 -- START
        Answer ans5 = new Answer("ans5", "Bla de Bla Five");
        Answer ans6 = new Answer("ans6", "Bla de Bla Six");
        Set<Answer> ansSet3 = new HashSet<>(); ansSet3.add(ans5); ansSet3.add(ans6);
        InterviewQuestion quest3 = new InterviewQuestion("q3", "Question 3", ansSet3);

        Answer ans7 = new Answer("ans7", "Bla de Bla Seven");
        Answer ans8 = new Answer("ans8", "Bla de Bla Eight");
        Set<Answer> ansSet4 = new HashSet<>(); ansSet4.add(ans7); ansSet4.add(ans8);
        InterviewQuestion quest4 = new InterviewQuestion("q4", "Question 4", ansSet4);

        List<InterviewNode> list2 = new ArrayList<>();
        list2.add(quest3);
        list2.add(quest4);
        InterviewContainer optLevelCont1 = new InterviewContainer("cont1", list2);
        // -- GROUP 2 -- END

        // topLevel
        List<InterviewNode> topLevel1 = new ArrayList<>();
        topLevel1.add(cont1);
        InterviewContainer optLevelCont2 = new InterviewContainer("cont2", topLevel1);

        List<InterviewContainer> topLevel = new ArrayList<>();
        topLevel.add(optLevelCont1);
        topLevel.add(optLevelCont2);
        interview = new Interview(topLevel);


        // generate simple definition
        List<ContainerDefinition> path1 = new ArrayList<>();
        path1.add(new ContainerDefinition("c1", "clause1", ""));
        path1.add(new ContainerDefinition("g1", "group1", ""));
        QuestionDefinition q1 = new QuestionDefinition(path1, "1", "Question 1", "", "");

        List<ContainerDefinition> path2 = new ArrayList<>();
        path2.add(new ContainerDefinition("c1", "clause1", ""));
        path2.add(new ContainerDefinition("g1", "group1", ""));
        QuestionDefinition q2 = new QuestionDefinition(path2, "2", "Question 2", "", "");

        List<ContainerDefinition> path3 = new ArrayList<>();
        path3.add(new ContainerDefinition("c2", "clause2", ""));
        path3.add(new ContainerDefinition("g2", "group2", ""));
        QuestionDefinition q3 = new QuestionDefinition(path3, "3", "Question 3", "", "");

        List<QuestionDefinition> questions = new ArrayList<>();
        questions.add(q1); questions.add(q2); questions.add(q3);

        definition = new CaptureDefinition(questions, "def1", "");
    }

    @Test
    public void testBasicStructureCountQuestions(){
        Assert.assertEquals(InterviewUtility.getAllQuestions(interview).size(), 4);
    }

    @Test
    public void testWriteSimpleDefinition() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("src//main//resources//definitions//simpleDef1.yaml"), definition);
        //System.out.println(interview);
    }

    @Test
    public void testReadSimpleDefinition() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CaptureDefinition readDefinition = mapper.readValue(new File("src//main//resources//definitions//simpleDef1.yaml"), CaptureDefinition.class);
        System.out.println(readDefinition);
    }

    @Test
    public void testCreateSimpleDefinition() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CaptureDefinition definition = mapper.readValue(new File("src//main//resources//definitions//simpleDef1.yaml"), CaptureDefinition.class);
        Interview newInterview = InterviewDefinitionHandler.generateInterview(definition);
        mapper.writeValue(new File("src//main//resources//definitions//interview1_simpleDef1.yaml"), newInterview);
    }

}
