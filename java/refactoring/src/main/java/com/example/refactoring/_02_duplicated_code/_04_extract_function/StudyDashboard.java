package com.example.refactoring._02_duplicated_code._04_extract_function;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StudyDashboard {

    private void printParticipants(int eventId) throws IOException {
        // 메소드를 추출하여 해당 코드의 의도를 명확하게 전달 할 수 있다.(구현은 메소드에서)
        GHIssue issue = getGhIssue(eventId);
        Set<String> participants = getUsernames(issue);
        print(participants);
    }

    private void print(Set<String> participants) {
        participants.forEach(System.out::println);
    }

    private Set<String> getUsernames(GHIssue issue) throws IOException {
        Set<String> usernames = new HashSet<>();
        issue.getComments().forEach(c -> usernames.add(c.getUserName()));
        return usernames;
    }

    private GHIssue getGhIssue(int eventId) throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("haerong22/Study");
        GHIssue issue = repository.getIssue(eventId);
        return issue;
    }

    private void printReviewers() throws IOException {
        GHIssue issue = getGhIssue(1);
        Set<String> reviewers = getUsernames(issue);
        print(reviewers);
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.printReviewers();
        studyDashboard.printParticipants(1);

    }
}
