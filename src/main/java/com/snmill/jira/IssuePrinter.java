package com.snmill.jira;

/**
 *
 */
public class IssuePrinter {

    static String toString(Issue issue) {
        StringBuilder sb = new StringBuilder();
        sb.append(issue.getId());
        sb.append("|");
        sb.append(issue.getPriority());
        sb.append("|");
        sb.append(issue.getReporter());
        sb.append("|");
        sb.append(issue.getAssignee());     
        sb.append("|");
        sb.append(issue.getKey());
        sb.append("|");
        sb.append(issue.getStatus());
        sb.append("|");
        sb.append(issue.getDueDate());
        sb.append("|");
        sb.append(issue.getCreated());
        sb.append("|");
        sb.append(issue.getSummary());
        sb.append("|");
        return sb.toString();
    }

}
