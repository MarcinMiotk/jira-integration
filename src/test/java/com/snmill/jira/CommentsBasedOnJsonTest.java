package com.snmill.jira;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 */
public class CommentsBasedOnJsonTest {

    public CommentsBasedOnJsonTest() {
    }

    @Test
    public void countReturns3() throws IOException {
        Comments comments = new CommentsBasedOnJson(sampleJson());
        assertEquals(3, comments.size(""));
    }

    @Test
    public void firstCommentHasId() throws IOException {
        Comments comments = new CommentsBasedOnJson(sampleJson());
        assertEquals(439624, comments.comments("").get(0).getId());
    }

    @Test
    public void firstCommentHasBody() throws IOException {
        Comments comments = new CommentsBasedOnJson(sampleJson());
        assertEquals("This is a comment regarding the quality of the response.", comments.comments("").get(0).getBody());
    }

    @Test
    public void firstCommentHasAuthor() throws IOException {
        Comments comments = new CommentsBasedOnJson(sampleJson());
        assertEquals("mami", comments.comments("").get(0).getAuthor());
    }

    @Test
    public void firstCommentHasCreated() throws IOException {
        Comments comments = new CommentsBasedOnJson(sampleJson());
        assertEquals("2016-09-03 07:45:07", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(comments.comments("").get(0).getCreated()));
    }

    private String sampleJson() {
        try {
            File file = new File(getClass().getClassLoader().getResource("query_comments_example_result.json").getFile());
            String json = new String(Files.readAllBytes(file.toPath()), "UTF-8");
            return json;
        } catch (IOException ex) {
            throw new IllegalStateException("File doesnt exists");
        }
    }
}
