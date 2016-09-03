package com.snmill.jira;

import java.nio.charset.Charset;
import java.util.Base64;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class AssigningTest {

    public AssigningTest() {
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
    public void canAddComment() {
        RestTemplate api = new RestTemplate();
        String issueKey = "HUMP-9";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(jiraApiUrlPrefix + "issue/" + issueKey /*+ "/editmeta"*/);

        String jsonBody = "{\"fields\": { \"assignee\": { \"name\":\"mami\" }}}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        api.put(builder.build().toUri(), request);
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
