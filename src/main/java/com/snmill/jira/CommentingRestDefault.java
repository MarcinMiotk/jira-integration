package com.snmill.jira;

import java.nio.charset.Charset;
import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 */
class CommentingRestDefault implements Commenting {

    private final JiraApiConfiguration configuration;

    protected CommentingRestDefault(JiraApiConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void comment(String issueKey, String body) {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(configuration.getJiraApiUrl() + "issue/" + issueKey + "/comment");

        String jsonBody = "{\"body\": \"" + body + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader(configuration.getUsername(), configuration.getPassword()));
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.POST, request, String.class);
        //assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
    }

    String authHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

}
