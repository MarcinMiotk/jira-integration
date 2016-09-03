package com.snmill.jira;

/**
 *
 */
class CommentPrinter {

    protected CommentPrinter() {
    }

    static String toString(Comment comment) {
        StringBuilder sb = new StringBuilder();
        sb.append(comment.getId());
        sb.append("|");
        sb.append(comment.getAuthor());
        sb.append("|");
        sb.append(comment.getCreated());
        sb.append("|");
        sb.append(comment.getBody());
        sb.append("|");
        return sb.toString();
    }

}
