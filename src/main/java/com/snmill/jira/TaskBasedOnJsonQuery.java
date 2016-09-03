package com.snmill.jira;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class TaskBasedOnJsonQuery implements Tasks {

    private final String json;

    public TaskBasedOnJsonQuery(String json) {
        this.json = json;
    }

    @Override
    public int count() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            return root.get("total").asInt(6);
        } catch (IOException ex) {
            throw new IllegalStateException("Wrong response");
        }
    }

    @Override
    public List<Issue> issues() {
        try {
            List<Issue> tasks = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            Iterator<JsonNode> issues = root.get("issues").elements();
            while (issues.hasNext()) {
                JsonNode issue = issues.next();

                tasks.add(new Issue() {

                    @Override
                    public long getId() {
                        return issue.get("id").asLong();
                    }

                    @Override
                    public String getKey() {
                        return issue.get("key").asText();
                    }

                    @Override
                    public String getReporter() {
                        return issue.get("fields").get("reporter").get("name").asText();
                    }

                    @Override
                    public String getAssignee() {
                        return issue.get("fields").get("assignee").get("name").asText();
                    }

                    @Override
                    public String getStatus() {
                        return issue.get("fields").get("status").get("name").asText();
                    }

                    @Override
                    public String getSummary() {
                        return issue.get("fields").get("summary").asText();
                    }

                    @Override
                    public Date getCreated() {
                        try {
                            String asString = issue.get("fields").get("created").asText();
                            //2016-08-30T13:59:25.000+0200
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                            return sdf.parse(asString);
                        } catch (ParseException ex) {
                            // todo: log it
                            return null;
                        }
                    }

                    @Override
                    public int getPriority() {
                        return issue.get("fields").get("priority").get("id").asInt();
                    }

                    @Override
                    public Date getDueDate() {
                        try {
                            String asString = issue.get("fields").get("duedate").asText();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            return sdf.parse(asString);
                        } catch (ParseException ex) {
                            // todo: log it
                            return null;
                        }
                    }
                });
            }

            return tasks;
        } catch (IOException ex) {
            throw new IllegalStateException("Wrong response");
        }
    }

}
