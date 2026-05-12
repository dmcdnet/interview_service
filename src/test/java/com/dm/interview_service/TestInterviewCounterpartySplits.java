package com.dm.interview_service;

import com.dm.interview_service.definition.CaptureDefinition;
import com.dm.interview_service.definition.ContainerDefinition;
import com.dm.interview_service.definition.QuestionDefinition;
import com.dm.interview_service.model.*;
import com.dm.interview_service.service.InterviewDefinitionHandler;
import com.dm.interview_service.service.InterviewProcessor;
import com.dm.interview_service.service.InterviewUtility;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestInterviewCounterpartySplits {

    Interview interview;
    InterviewProcessor interviewProcessor;

    @BeforeEach
    public void createInterview() {
        // Structure: c1 -> g1 -> [q1 (COUNTERPARTY splittable), q2 (not splittable)]
        List<ContainerDefinition> path1 = new ArrayList<>();
        path1.add(new ContainerDefinition("c1", "clause1", ""));
        path1.add(new ContainerDefinition("g1", "group1", ""));
        QuestionDefinition q1Def = new QuestionDefinition(path1, "q1", "What is your position?", "COUNTERPARTY", "");

        List<ContainerDefinition> path2 = new ArrayList<>();
        path2.add(new ContainerDefinition("c1", "clause1", ""));
        path2.add(new ContainerDefinition("g1", "group1", ""));
        QuestionDefinition q2Def = new QuestionDefinition(path2, "q2", "What is your notional?", "", "");

        List<QuestionDefinition> questions = new ArrayList<>();
        questions.add(q1Def);
        questions.add(q2Def);

        CaptureDefinition definition = new CaptureDefinition(questions, "cp_def1", "Counterparty Trade");
        interview = InterviewDefinitionHandler.generateInterview(definition);
        interviewProcessor = new InterviewProcessor();
    }

    @Test
    public void testCounterpartySplitCreatesSecondQuestion() {
        boolean result = interviewProcessor.splitNode(interview, counterpartySplitRequest("q1"));

        Assert.assertTrue(result);

        List<InterviewNode> questions = InterviewUtility.getAllQuestions(interview);
        Assert.assertEquals(2L, questions.stream().filter(n -> n.getId().equals("q1")).count());
        Assert.assertEquals(1L, questions.stream().filter(n -> n.getId().equals("q2")).count());
    }

    @Test
    public void testCounterpartySplitCannotRepeat() {
        interviewProcessor.splitNode(interview, counterpartySplitRequest("q1"));
        boolean second = interviewProcessor.splitNode(interview, counterpartySplitRequest("q1"));

        Assert.assertFalse(second);

        List<InterviewNode> questions = InterviewUtility.getAllQuestions(interview);
        Assert.assertEquals(2L, questions.stream().filter(n -> n.getId().equals("q1")).count());
    }

    @Test
    public void testCounterpartyCopyAccessibleByPath() {
        interviewProcessor.splitNode(interview, counterpartySplitRequest("q1"));

        List<String> cpPath = new ArrayList<>();
        cpPath.add("c1"); cpPath.add("g1"); cpPath.add("q1[CP]");
        InterviewNode cpNode = interview.getNodeByNodePathReference(new InterviewNodePath(cpPath));

        Assert.assertNotNull(cpNode);
        Assert.assertEquals("q1", cpNode.getId());
        Assert.assertEquals(SplitType.COUNTERPARTY, cpNode.getSplit().getSplitType());
        Assert.assertEquals(Integer.valueOf(1), cpNode.getSplit().getSequenceNumber());
    }

    @Test
    public void testOriginalStillAccessibleByUnqualifiedPath() {
        interviewProcessor.splitNode(interview, counterpartySplitRequest("q1"));

        List<String> originalPath = new ArrayList<>();
        originalPath.add("c1"); originalPath.add("g1"); originalPath.add("q1");
        InterviewNode original = interview.getNodeByNodePathReference(new InterviewNodePath(originalPath));

        Assert.assertNotNull(original);
        Assert.assertEquals("q1", original.getId());
        Assert.assertEquals(Integer.valueOf(0), original.getSplit().getSequenceNumber());
    }

    @Test
    public void testNonSplittableQuestionNotSplit() {
        boolean result = interviewProcessor.splitNode(interview, counterpartySplitRequest("q2"));

        Assert.assertFalse(result);

        List<InterviewNode> questions = InterviewUtility.getAllQuestions(interview);
        Assert.assertEquals(1L, questions.stream().filter(n -> n.getId().equals("q2")).count());
    }

    private SplitRequestCounterparty counterpartySplitRequest(String questionId) {
        List<String> path = new ArrayList<>();
        path.add("c1"); path.add("g1"); path.add(questionId);
        return new SplitRequestCounterparty(questionId, new InterviewNodePath(path), new Counterparty("cp1", "Goldman Sachs"));
    }
}
