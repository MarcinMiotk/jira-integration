package com.snmill.jira;

import static com.snmill.jira.JiraApiConfiguration.jira;
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
    public void comments() {
        fasade.issuesCount();
    }

}
