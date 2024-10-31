package util;

public class Article {
    private String level;
    private String groupId;
    private String title;
    private String shortDescription;
    private String body;
    private boolean beginner;
    private boolean intermediate;

    // Constructor
    public Article(String level, String groupId, String title, String shortDescription, String body) {
        this.level = level;
        this.groupId = groupId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;

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
                "level='" + level + '\'' +
                ", groupId='" + groupId + '\'' +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
