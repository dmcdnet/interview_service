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

public class TestInterviewChainedSplits {

    Interview interview;
    InterviewProcessor interviewProcessor;

    @BeforeEach
    public void createInterview() {
        // q1: COUNTERPARTY-splittable, q2: SEQUENCE-splittable, q3: not splittable
        List<ContainerDefinition> path = new ArrayList<>();
        path.add(new ContainerDefinition("c1", "clause1", ""));
        path.add(new ContainerDefinition("g1", "group1", ""));

        List<QuestionDefinition> questions = new ArrayList<>();
        questions.add(new QuestionDefinition(new ArrayList<>(path), "q1", "Position?", "COUNTERPARTY", ""));
        questions.add(new QuestionDefinition(new ArrayList<>(path), "q2", "Notional?", "SEQUENCE", ""));
        questions.add(new QuestionDefinition(new ArrayList<>(path), "q3", "Trade date?", "", ""));

        interview = InterviewDefinitionHandler.generateInterview(
                new CaptureDefinition(questions, "chained_def1", "Chained Split Test"));
        interviewProcessor = new InterviewProcessor();
    }

    @Test
    public void testCounterpartyThenSequence() {
        interviewProcessor.splitNode(interview, cpRequest("q1"));
        boolean result = interviewProcessor.splitNode(interview, seqRequest("q1[CP]"));

        Assert.assertTrue(result);

        List<InterviewNode> questions = InterviewUtility.getAllQuestions(interview);
        Assert.assertEquals(3L, questions.stream().filter(n -> n.getId().equals("q1")).count());

        // q1[CP][SN=1] must be reachable by path
        List<String> path = new ArrayList<>();
        path.add("c1"); path.add("g1"); path.add("q1[CP][SN=1]");
        InterviewNode node = interview.getNodeByNodePathReference(new InterviewNodePath(path));
        Assert.assertNotNull(node);
        Assert.assertEquals("q1", node.getId());
    }

    @Test
    public void testOriginalPathsUnaffected() {
        interviewProcessor.splitNode(interview, cpRequest("q1"));
        interviewProcessor.splitNode(interview, seqRequest("q1[CP]"));

        List<String> originalPath = new ArrayList<>();
        originalPath.add("c1"); originalPath.add("g1"); originalPath.add("q1");
        Assert.assertNotNull(interview.getNodeByNodePathReference(new InterviewNodePath(originalPath)));

        List<String> cpPath = new ArrayList<>();
        cpPath.add("c1"); cpPath.add("g1"); cpPath.add("q1[CP]");
        Assert.assertNotNull(interview.getNodeByNodePathReference(new InterviewNodePath(cpPath)));
    }

    @Test
    public void testCannotDoubleCounterparty() {
        interviewProcessor.splitNode(interview, cpRequest("q1"));
        // q1[CP] already has CP in its history — cannot be CP-split again
        boolean result = interviewProcessor.splitNode(interview, cpRequest("q1[CP]"));

        Assert.assertFalse(result);
        Assert.assertEquals(2L, InterviewUtility.getAllQuestions(interview).stream()
                .filter(n -> n.getId().equals("q1")).count());
    }

    @Test
    public void testSequenceThenCounterparty() {
        interviewProcessor.splitNode(interview, seqRequest("q2"));
        boolean result = interviewProcessor.splitNode(interview, cpRequest("q2[SN=1]"));

        Assert.assertTrue(result);

        Assert.assertEquals(3L, InterviewUtility.getAllQuestions(interview).stream()
                .filter(n -> n.getId().equals("q2")).count());

        List<String> path = new ArrayList<>();
        path.add("c1"); path.add("g1"); path.add("q2[SN=1][CP]");
        Assert.assertNotNull(interview.getNodeByNodePathReference(new InterviewNodePath(path)));
    }

    private SplitRequestCounterparty cpRequest(String questionId) {
        List<String> path = new ArrayList<>();
        path.add("c1"); path.add("g1"); path.add(questionId);
        return new SplitRequestCounterparty(questionId, new InterviewNodePath(path),
                new Counterparty("cp1", "Goldman Sachs"));
    }

    private SplitRequestSequence seqRequest(String questionId) {
        List<String> path = new ArrayList<>();
        path.add("c1"); path.add("g1"); path.add(questionId);
        return new SplitRequestSequence(questionId, new InterviewNodePath(path), true);
    }
}
