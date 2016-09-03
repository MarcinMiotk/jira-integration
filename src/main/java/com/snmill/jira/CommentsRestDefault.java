package com.snmill.jira;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 */
public class CommentsRestDefault implements Comments {

    private final JiraApiConfiguration configuration;

    public CommentsRestDefault(JiraApiConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int size(String issueKey) {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(configuration.getJiraApiUrl() + "issue/" + issueKey + "/comment");
        builder.queryParam("orderBy", "-updated");
        builder.queryParam("maxResults", "200");

        HttpEntity request = new HttpEntity<>(createAuthorizationHeaders(configuration.getUsername(), configuration.getPassword()));
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);

        return new CommentsBasedOnJson(response.getBody()).size(issueKey);
    }

    @Override
    public List<Comment> comments(String issueKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
