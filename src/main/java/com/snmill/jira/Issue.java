package com.snmill.jira;

import java.util.Date;

/**
 *
 */
public interface Issue {

    long getId();

    String getKey();

    String getReporter();

    String getAssignee();

    String getStatus();

    String getSummary();

    Date getCreated();

    int getPriority();

    Date getDueDate();
}
