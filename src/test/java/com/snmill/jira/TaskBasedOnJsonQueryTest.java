package com.snmill.jira;

import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class TaskBasedOnJsonQueryTest {

    public TaskBasedOnJsonQueryTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCaseJsonIsAvailable() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("query_example_result.json").getFile());
        String json = new String(Files.readAllBytes(file.toPath()), "UTF-8");
        out.println("json=" + json);
        assertNotNull(json);
    }

    @Test
    public void countReturns19() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals(19, task.count());
    }

    @Test
    public void listHas19Issues() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals(19, task.issues().size());
    }

    @Test
    public void firstIssueHasId() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals(188461, task.issues().get(0).getId());
    }

    @Test
    public void firstIssueHasKey() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals("SUPTMPLHER-635", task.issues().get(0).getKey());
    }

    @Test
    public void firstIssueHasReporterAndAssignee() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals("mapo", task.issues().get(0).getReporter());
        assertEquals("mami", task.issues().get(0).getAssignee());
    }

    @Test
    public void firstIssueHasOpenStatus() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals("Open", task.issues().get(0).getStatus());
    }

    @Test
    public void firstIssueHasSummary() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals("Jakiś tytuł nr 17", task.issues().get(0).getSummary());
    }

    @Test
    public void firstIssueHasCreated() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals("2016-08-30 13:59:25", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.issues().get(0).getCreated()));
    }

    @Test
    public void firstIssueHasDueTo() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals("2016-09-25", new SimpleDateFormat("yyyy-MM-dd").format(task.issues().get(0).getDueDate()));
    }

    @Test
    public void firstIssueHasPriority() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        assertEquals(5, task.issues().get(0).getPriority());
    }

    @Test
    public void printAll() throws IOException {
        Tasks task = new TaskBasedOnJsonQuery(sampleJson());
        for (Issue issue : task.issues()) {
            StringBuilder sb = new StringBuilder();
            sb.append(issue.getId());
            sb.append("|");
            sb.append(issue.getKey());
            sb.append("|");
            sb.append(issue.getPriority());
            sb.append("|");
            sb.append(issue.getStatus());
            sb.append("|");
            sb.append(issue.getReporter());
            sb.append("|");
            sb.append(issue.getAssignee());
            sb.append("|");
            sb.append(issue.getDueDate());
            sb.append("|");
            sb.append(issue.getCreated());
            sb.append("|");
            sb.append(issue.getSummary());
            sb.append("|");
            out.println(sb.toString());
        }
    }

    private String sampleJson() {
        try {
            File file = new File(getClass().getClassLoader().getResource("query_example_result.json").getFile());
            String json = new String(Files.readAllBytes(file.toPath()), "UTF-8");
            return json;
        } catch (IOException ex) {
            throw new IllegalStateException("File doesnt exists");
        }
    }

}
