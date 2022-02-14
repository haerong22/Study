package com.example.refactoring._01_mysterious_name._03_rename_field;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudyDashboard {

    private final Set<StudyReview> studyReviews = new HashSet<>(); // record 자료구조 사용

    /**
     * 이슈에 작성된 리뷰어의 목록과 리뷰를 읽어옵니다.
     * @throws IOException
     */
    private void loadReviews() throws IOException { // 메소드의 목적에 맞게 메소드명을 변경
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("haerong22/Study");
        GHIssue issue = repository.getIssue(1);

        // 메소드명이 loadReviews 이므로 comments 보다는 reviews 가 더 이해하기 좋다고 생각
        List<GHIssueComment> reviews = issue.getComments();
        for (GHIssueComment review : reviews) {
            studyReviews.add(new StudyReview(review.getUserName(), review.getBody()));
        }
    }

    public Set<StudyReview> getStudyReviews() {
        return studyReviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getStudyReviews().forEach(System.out::println);
    }
}
