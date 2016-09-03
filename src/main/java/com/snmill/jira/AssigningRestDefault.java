package com.snmill.jira;

import java.nio.charset.Charset;
import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 */
public class AssigningRestDefault implements Assigning {

    private final JiraApiConfiguration configuration;

    public AssigningRestDefault(JiraApiConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void assign(String issueKey, String assigneee) {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(configuration.getJiraApiUrl() + "issue/" + issueKey /*+ "/editmeta"*/);
        String jsonBody = "{\"fields\": { \"assignee\": { \"name\":\"" + assigneee + "\" }}}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader(configuration.getUsername(), configuration.getPassword()));
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

}
