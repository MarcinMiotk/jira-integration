package com.snmill.jira;

import static com.snmill.jira.JiraApiConfiguration.jira;
import static java.lang.System.out;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class FasadeDefaultTest {

    public FasadeDefaultTest() {
    }

    Fasade fasade;

    @Before
    public void setUp() {
        JiraApiConfiguration cfg = jira().url("https://jira.ailleron.com/jira/rest/api/2/").user("mami").passwordFromEnv().configure();
        fasade = new FasadeDefault(cfg);
    }

    @Test
    public void issuesCount() {
        int count = fasade.issuesCount();
        out.println("issuesCount=" + count);
        assertTrue(count >= 0);
    }

    @Test
    public void commentsCount() {
        String issueKey = "HUMP-9";
        int count = fasade.commentsCount(issueKey);
        out.println("commentsCount=" + count);
    }

    @Test
    public void commenting() {
        String issueKey = "HUMP-9";
        int countBefore = fasade.commentsCount(issueKey);
        fasade.comment(issueKey, "This is test");
        int countAfter = fasade.commentsCount(issueKey);
        out.println("commenting. countBefore=" + countBefore);
        out.println("commenting. countAfter=" + countAfter);
        assertTrue(countAfter > countBefore);
    }

    @Test
    public void assign() {
        String issueKey = "HUMP-9";
        fasade.assign(issueKey, "mobr");
    }

    @Test
    public void listCommentsFirstElementHasAuthorMami() {
        String issueKey = "HUMP-9";
        List<Comment> comments = fasade.comments(issueKey);
        assertEquals("mami", comments.get(0).getAuthor());

        for (Comment comment : comments) {
            out.println(CommentPrinter.toString(comment));
        }
    }

    @Test
    public void listIssues() {
        List<Issue> issues = fasade.issues();
        for (Issue issue : issues) {
            out.println(IssuePrinter.toString(issue));
        }
    }

    @Test
    public void issueHasValidData() {
        String issueKey = "HUMP-9";
        Issue issue = fasade.issue(issueKey);
        assertEquals(189152, issue.getId());
        assertEquals("HUMP-9", issue.getKey());
        assertEquals("Testowaczka", issue.getSummary());
    }

}
