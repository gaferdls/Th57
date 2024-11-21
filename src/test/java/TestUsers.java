import database.Database;
import database.DatabaseHelper;
import org.junit.jupiter.api.Test;
import util.Article;
import util.User;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestUsers {

    // Tests adding a user and then getting it from the database
    @Test
    public void testAddUser() {
        Database.init();
        Database.clearDatabase();

        // Add the new user
        User user = new User("username", "firstName", "middleName", "lastName", "preferredName", "email", "password".toCharArray(), false, null, "skillLevel", false, false, true, "groups");
        Database.addUser(user);

        // Get the new user from the database
        User user2 = Database.db.getUserInformationFromUsername(user.getUsername());
        Database.close();

        // Check that they are the same
        assertEquals(user, user2);
    }

    @Test
    public void testUpdateArticle() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Add initial article
        Article article = new Article("Beginner", "group1", "Update Test",
                "Original Abstract", "Original Body", "test", "refs", "groups");
        Database.addArticle(article);

        // Create updated version
        Article updatedArticle = new Article("Intermediate", "group1", "Update Test",
                "Updated Abstract", "Updated Body", "updated,test", "new refs", "groups");
        Database.updateArticle(updatedArticle);

        // Retrieve and verify
        Article retrieved = Database.db.getArticleByTitle("Update Test");
        Database.close();

        assertEquals("Updated Abstract", retrieved.getAbstract());
        assertEquals("Updated Body", retrieved.getBody());
        assertEquals("Intermediate", retrieved.getLevel());
    }

    @Test
    public void testGetArticlesByGroup() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Add articles with different groups
        Article article1 = new Article("Beginner", "group1", "Group1 Article",
                "Abstract1", "Body1", "test", "refs", "group1");
        Article article2 = new Article("Beginner", "group2", "Group2 Article",
                "Abstract2", "Body2", "test", "refs", "group2");
        Article article3 = new Article("Beginner", "group1", "Another Group1",
                "Abstract3", "Body3", "test", "refs", "group1");

        Database.addArticle(article1);
        Database.addArticle(article2);
        Database.addArticle(article3);

        ArrayList<Article> group1Articles = Database.db.getArticlesByGroup("group1");
        Database.close();

        assertEquals(2, group1Articles.size());
        assertTrue(group1Articles.stream().allMatch(a -> a.getGroupId().equals("group1")));
    }

    @Test
    public void testSearchArticlesByKeyword() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Add articles with different keywords
        Article article1 = new Article("Beginner", "group1", "Java Basics",
                "Abstract1", "Body1", "java,programming", "refs", "groups");
        Article article2 = new Article("Intermediate", "group1", "Python Intro",
                "Abstract2", "Body2", "python,programming", "refs", "groups");
        Article article3 = new Article("Expert", "group1", "Advanced Java",
                "Abstract3", "Body3", "java,advanced", "refs", "groups");

        Database.addArticle(article1);
        Database.addArticle(article2);
        Database.addArticle(article3);

        ArrayList<Article> javaArticles = Database.db.searchArticlesByKeyword("java");
        Database.close();

        assertEquals(2, javaArticles.size());
        assertTrue(javaArticles.stream().anyMatch(a -> a.getTitle().equals("Java Basics")));
        assertTrue(javaArticles.stream().anyMatch(a -> a.getTitle().equals("Advanced Java")));
    }

    @Test
    public void testInvalidArticleOperations() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Test getting non-existent article
        assertNull(Database.db.getArticleByTitle("NonExistent"));

        // Test deleting non-existent article
        assertThrows(SQLException.class, () -> {
            Database.db.deleteArticle("NonExistent");
        });

        // Test adding duplicate article
        Article article = new Article("Beginner", "group1", "Duplicate Test",
                "Abstract", "Body", "test", "refs", "groups");
        Database.addArticle(article);

        assertThrows(SQLException.class, () -> {
            Database.addArticle(article);
        });

        Database.close();
    }

    @Test
    public void testArticleValidation() {
        Database.init();
        Database.clearDatabase();

        // Test with empty title
        assertThrows(IllegalArgumentException.class, () -> {
            new Article("Beginner", "group1", "", "Abstract", "Body", "test", "refs", "groups");
        });

        // Test with null level
        assertThrows(IllegalArgumentException.class, () -> {
            new Article(null, "group1", "Title", "Abstract", "Body", "test", "refs", "groups");
        });

        // Test with invalid level
        assertThrows(IllegalArgumentException.class, () -> {
            new Article("InvalidLevel", "group1", "Title", "Abstract", "Body", "test", "refs", "groups");
        });

        Database.close();
    }

}
