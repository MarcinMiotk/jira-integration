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
class IssuesRestDefault implements Issues {

    private final JiraApiConfiguration configuration;

    protected IssuesRestDefault(JiraApiConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int count() {
        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(configuration.getJiraApiUrl() + "search");
        builder.queryParam("jql", jqlForTeamIssues());
        builder.queryParam("fields", "id,key,summary,reporter,assignee,created,duedate,priority,status");
        builder.queryParam("maxResults", "200");
        HttpEntity request = new HttpEntity<>(createAuthorizationHeaders(configuration.getUsername(), configuration.getPassword()));
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);
        Issues issues = new IssuesBasedOnJsonQuery(response.getBody());
        return issues.count();
    }

    @Override
    public List<Issue> issues() {

        RestTemplate api = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(configuration.getJiraApiUrl() + "search");
        builder.queryParam("jql", jqlForTeamIssues());
        builder.queryParam("fields", "id,key,summary,reporter,assignee,created,duedate,priority,status");
        builder.queryParam("maxResults", "200");
        HttpEntity request = new HttpEntity<>(createAuthorizationHeaders(configuration.getUsername(), configuration.getPassword()));
        ResponseEntity<String> response = api.exchange(builder.build().toUri(), HttpMethod.GET, request, String.class);
        Issues issues = new IssuesBasedOnJsonQuery(response.getBody());
        return issues.issues();
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
        String jql = "(assignee=mami OR assignee=ankr OR assignee=mapo OR assignee=kasw OR assignee=mamk) AND (status='OPEN' OR status='CLIENT VERIFICATION' OR status='REOPENED' OR status='IN PROGRESS') order by created";
        return jql;
    }

}
