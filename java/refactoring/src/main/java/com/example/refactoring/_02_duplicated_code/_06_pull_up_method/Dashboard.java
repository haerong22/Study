package com.example.refactoring._02_duplicated_code._06_pull_up_method;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// 중복되는 코드를 상위 클래스로 이동
public class Dashboard {

    public static void main(String[] args) throws IOException {
        ReviewerDashboard reviewerDashboard = new ReviewerDashboard();
        reviewerDashboard.printReviewers();

        ParticipantDashboard participantDashboard = new ParticipantDashboard();
        participantDashboard.printUsernames(1);
    }

    public void printUsernames(int eventId) throws IOException {
        // Get github issue to check homework
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("haerong22/Study");
        GHIssue issue = repository.getIssue(eventId);

        // Get participants
        Set<String> participants = new HashSet<>();
        issue.getComments().forEach(c -> participants.add(c.getUserName()));

        // Print participants
        participants.forEach(System.out::println);
    }
}
