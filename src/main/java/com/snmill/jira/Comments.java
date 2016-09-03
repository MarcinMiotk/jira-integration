package com.snmill.jira;

import java.util.List;

/**
 *
 */
interface Comments {

    int size(String issueKey);

    List<Comment> comments(String issueKey);

}
