package com.snmill.jira;

import java.util.List;

/**
 *
 */
public class FasadeDefault implements Fasade {

    private final JiraApiConfiguration configuration;

    private final Issues issues;
    private final Comments comments;
    private final Commenting commenting;
    private final Assigning assigning;

    public FasadeDefault(JiraApiConfiguration configuration) {
        this.configuration = configuration;

        this.issues = new IssuesRestDefault(configuration);
        this.comments = new CommentsRestDefault(configuration);
        this.commenting = new CommentingRestDefault(configuration);
        this.assigning = new AssigningRestDefault(configuration);
    }

    @Override
    public void assign(String issueKey, String assigneee) {
        assigning.assign(issueKey, assigneee);
    }

    @Override
    public List<Comment> comments(String issueKey) {
        return comments.comments(issueKey);
    }

    @Override
    public int commentsCount(String issueKey) {
        return comments.size(issueKey);
    }

    @Override
    public int issuesCount() {
        return issues.count();
    }

    @Override
    public List<Issue> issues() {
        return issues.issues();
    }

    @Override
    public void comment(String issueKey, String body) {
        this.commenting.comment(issueKey, body);
    }

    @Override
    public Issue issue(String issueKey) {
        return issues.issue(issueKey);
    }

}
