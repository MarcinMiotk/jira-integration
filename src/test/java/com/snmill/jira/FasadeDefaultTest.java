package com.snmill.jira;

import static com.snmill.jira.JiraApiConfiguration.jira;
import static java.lang.System.out;
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

}
