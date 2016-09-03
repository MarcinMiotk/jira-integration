package com.snmill.jira;

import java.util.List;

/**
 *
 */
public class FasadeDefault implements Fasade {
    
    private final JiraApiConfiguration configuration;
    
    private final Issues issues;
    private final Comments comments;
    private final Commenting commenting;
    private final Assigning assigning;
    
    public FasadeDefault(JiraApiConfiguration configuration) {
        this.configuration = configuration;
        
        this.issues = new IssuesRestDefault(configuration);
        this.comments = new CommentsRestDefault(configuration);
        this.commenting = new CommentingRestDefault(configuration);
        this.assigning = new AssigningRestDefault(configuration);
    }
    
    @Override
    public void assign(String issueKey, String assigneee) {
        assigning.assign(issueKey, assigneee);
    }
    
    @Override
    public List<Comment> comments(String issueKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int commentsCount(String issueKey) {
        return comments.size(issueKey);
    }
    
    @Override
    public int issuesCount() {
        return issues.count();
    }
    
    @Override
    public List<Issue> issues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void comment(String issueKey, String body) {
        this.commenting.comment(issueKey, body);
    }
    
}
