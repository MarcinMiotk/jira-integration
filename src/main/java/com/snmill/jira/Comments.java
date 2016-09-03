package com.snmill.jira;

import java.util.List;

/**
 *
 */
public interface Comments {

    int size(String issueKey);

    List<Comment> comments(String issueKey);

}
