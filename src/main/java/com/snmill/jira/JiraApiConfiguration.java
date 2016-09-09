package com.snmill.jira;

/**
 *
 */
public class JiraApiConfiguration {

    private String jiraApiUrl;
    private String username;
    private String password;
    private String jql;

    public static Builder jira() {
        return new Builder();
    }

    static class Builder {

        private String jiraApiUrl;
        private String username;
        private String password;
        private String jql;

        public Builder() {
        }

        public Builder url(String url) {
            this.jiraApiUrl = url;
            return this;
        }

        public Builder user(String user) {
            this.username = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder passwordFromEnv() {
            this.password = System.getenv("jira.mami.password");
            return this;
        }

        public Builder jql(String jql) {
            this.jql = jql;
            return this;
        }

        public JiraApiConfiguration configure() {
            return new JiraApiConfiguration(jiraApiUrl, username, password, jql);
        }
    }

    private JiraApiConfiguration() {
    }

    private JiraApiConfiguration(String jiraApiUrl, String username, String password, String jql) {
        this.jiraApiUrl = jiraApiUrl;
        this.username = username;
        this.password = password;
        this.jql = jql;
    }

    protected String getJiraApiUrl() {
        return jiraApiUrl;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected String getJql() {
        return jql;
    }

}
