package com.snmill.jira;

import java.util.List;

/**
 *
 */
public interface Fasade {

    void assign(String issueKey, String assigneee);

    void comment(String issueKey, String body);

    List<Comment> comments(String issueKey);

    int commentsCount(String issueKey);

    int issuesCount();

    List<Issue> issues();

}
