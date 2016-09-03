package com.snmill.jira;

import java.util.Date;

/**
 *
 */
public interface Comment {

    long getId();

    String getBody();

    Date getCreated();

    String getAuthor();
}
