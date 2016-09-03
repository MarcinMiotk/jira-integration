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
class CommentsBasedOnJson implements Comments {

    final String json;

    protected CommentsBasedOnJson(String json) {
        this.json = json;
    }

    @Override
    public int size(String issueKey) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            return root.get("total").asInt(0);
        } catch (IOException ex) {
            throw new IllegalStateException("Wrong response");
        }
    }

    @Override
    public List<Comment> comments(String issueKey) {
        try {
            List<Comment> tasks = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            Iterator<JsonNode> issues = root.get("comments").elements();
            while (issues.hasNext()) {
                JsonNode issue = issues.next();

                tasks.add(new Comment() {

                    @Override
                    public long getId() {
                        return issue.get("id").asLong();
                    }

                    @Override
                    public String getBody() {
                        return issue.get("body").asText();
                    }

                    @Override
                    public Date getCreated() {
                        try {
                            String asString = issue.get("created").asText();
                            //2016-08-30T13:59:25.000+0200
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                            return sdf.parse(asString);
                        } catch (ParseException ex) {
                            // todo: log it
                            return null;
                        }

                    }

                    @Override
                    public String getAuthor() {
                        return issue.get("author").get("name").asText();
                    }

                });
            }

            return tasks;
        } catch (IOException ex) {
            throw new IllegalStateException("Wrong response");
        }
    }

}
