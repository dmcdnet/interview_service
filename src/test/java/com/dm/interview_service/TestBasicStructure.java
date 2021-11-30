package com.dm.interview_service;

import com.dm.interview_service.model.*;
import com.dm.interview_service.service.InterviewProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBasicStructure {

    Interview interview;

    @BeforeAll
    public void buildBasicStructure(){
        // questions
        Answer ans1 = new Answer("ans1", "Bla de Bla");
        Answer ans2 = new Answer("ans2", "Bla de Bla Two");
        Set<Answer> ansSet1 = new HashSet<>(); ansSet1.add(ans1); ansSet1.add(ans2);
        InterviewQuestion quest1 = new InterviewQuestion("q1", "Question 1", ansSet1);

        //InterviewQuestion quest2 = new InterviewQuestion("q2", "Question 2", new HashSet<>());

        //InterviewQuestion quest3 = new InterviewQuestion("q3", "Question 3", new HashSet<>());

        // groups
        List<InterviewNode> list1 = new ArrayList<>();
        list1.add(quest1);
        InterviewContainer cont1 = new InterviewContainer("cont1", list1);

        List<InterviewContainer> topLevel = new ArrayList<>();
        topLevel.add(cont1);
        interview = new Interview(topLevel);
    }

    @Test
    public void testBasicStructure(){
        System.out.println(InterviewProcessor.getAllQuestions(interview));
    }

}
