package com.snmill.jira;

import static java.lang.System.out;
import java.nio.charset.Charset;
import java.util.Base64;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 */
public class IssuesTest {

    public IssuesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    HttpHeaders authorizationHeaders;
    String jiraApiUrlPrefix;

    @Before
    public void setUp() {
        String password = System.getenv("jira.mami.password");
        authorizationHeaders = createAuthorizationHeaders("mami", password);
        jiraApiUrlPrefix = "https://jira.ailleron.com/jira/rest/api/2/";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void canAuthenticateWithDefinedUser() {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "search");
        builder.queryParam("jql", jqlForTeamIssues());
        HttpEntity request = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);

        out.println("BODY=" + response.getBody());
        out.println("HEADERS=" + response.getHeaders());
        out.println("HEADERS keys=" + response.getHeaders().keySet());
        out.println("STATUS=" + response.getStatusCode());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    public void queryOnlySpecifiedFields() {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "search");
        builder.queryParam("jql", jqlForTeamIssues());
        builder.queryParam("fields", "id,key,summary,reporter,assignee,created,duedate,priority,status");
        HttpEntity request = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    public void jsonCanBeConverted() {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "search");
        builder.queryParam("jql", jqlForTeamIssues());
        builder.queryParam("fields", "id,key,summary,reporter,assignee,created,duedate,priority,status");
        builder.queryParam("maxResults", "200");
        HttpEntity request = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);
        Issues task = new IssuesBasedOnJsonQuery(response.getBody());
        for (Issue issue : task.issues()) {
            out.println(IssuePrinter.toString(issue));
        }
        out.println("SIZE=" + task.count());
    }

    @Test
    public void issueSingleGet() {
        String issueKey = "HUMP-9";
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "issue/" + issueKey);
        builder.queryParam("fields", "id,key,summary,reporter,assignee,created,duedate,priority,status");
        HttpEntity request = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);
        out.println(response.getBody());
    }

    HttpHeaders createAuthorizationHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

    String jqlForTeamIssues() {
        String jql = "(assignee=mami OR assignee=ankr OR assignee=mapo OR assignee=kasw OR assignee=mamk) AND (status='OPEN' OR status='CLIENT VERIFICATION' OR status='REOPENED' OR status='IN PROGRESS' OR status='HOLD') order by created";
        return jql;
    }

}
