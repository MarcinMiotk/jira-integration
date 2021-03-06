package com.snmill.jira;

import static java.lang.System.out;
import java.nio.charset.Charset;
import java.util.Base64;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class CommentsTest {

    public CommentsTest() {
    }

    HttpHeaders authorizationHeaders;
    String authorizationHeader;
    String jiraApiUrlPrefix;

    @Before
    public void setUp() {
        String password = System.getenv("jira.mami.password");
        authorizationHeaders = createAuthorizationHeaders("mami", password);
        authorizationHeader = authHeader("mami", password);
        jiraApiUrlPrefix = "https://jira.ailleron.com/jira/rest/api/2/";
    }

    @Test
    public void canListComments() {
        RestTemplate api = new RestTemplate();
        String issueKey = "HUMP-9";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "issue/" + issueKey + "/comment");
        builder.queryParam("orderBy", "-updated");
        builder.queryParam("maxResults", "200");
        HttpEntity request = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);
        out.println(response.getBody());
    }

    @Test
    public void canAddComment() {
        RestTemplate api = new RestTemplate();
        String issueKey = "HUMP-9";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "issue/" + issueKey + "/comment");

        String jsonBody = "{\"body\": \"This is a comment regarding the quality of the response.\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
    }

    String authHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
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

}
