package util;

import java.util.Objects;

public class Article {
    private String level;
    private String groupId;
    private String title;
    private String shortDescription;
    private String body;
    private String references, keywords, groups;

    // Constructor
    public Article(String level, String groupId, String title, String shortDescription, String body, String keywords, String references, String groups) {
        this.level = level;
        this.groupId = groupId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;
        this.keywords = keywords;
        this.references = references;
        this.groups = groups;
    }

    public String getReferences() {
        return references;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getGroups() {
        return groups;
    }

    // Getters
    public String getLevel() {
        return level;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getBody() {
        return body;
    }

    // Optionally, you can add a toString method for easy printing
    @Override
    public String toString() {
        return "Article{" +
                "keywords='" + keywords + '\'' +
                ", references='" + references + '\'' +
                ", body='" + body + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", title='" + title + '\'' +
                ", groupId='" + groupId + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(level, article.level) && Objects.equals(groupId, article.groupId) && Objects.equals(title, article.title) && Objects.equals(shortDescription, article.shortDescription) && Objects.equals(body, article.body) && Objects.equals(references, article.references) && Objects.equals(keywords, article.keywords) && Objects.equals(groups, article.groups);
    }

    public String getAbstract() {
        return this.shortDescription;
    }
}
