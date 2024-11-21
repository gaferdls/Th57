import database.Database;
import org.junit.jupiter.api.Test;
import util.Article;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestArticles {

    // Test adding a new article then getting it from the database
    @Test
    public void testAddArticle() {
        Database.init();
        Database.clearDatabase();

        // Add a new article to the database
        Article article = new Article("level", "groupid", "title", "abstract", "body", "keywords", "references", "groups");
        Database.addArticle(article);

        // Get the article from the database
        Article article2 = Database.db.getAllArticles().get(0);
        Database.close();

        // Check that the articles are equal
        assertEquals(article, article2);
    }


    @Test
    public void testGetArticleByTitle() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Add a new article to the database
        Article article = new Article("Beginner", "group1", "Test Title", "Test Abstract",
                "Test Body", "test,keywords", "test references", "group1,group2");
        Database.addArticle(article);

        // Get the article by title
        Article retrievedArticle = Database.db.getArticleByTitle("Test Title");
        Database.close();

        // Check that the retrieved article matches the original
        assertEquals(article.getTitle(), retrievedArticle.getTitle());
        assertEquals(article.getLevel(), retrievedArticle.getLevel());
        assertEquals(article.getGroupId(), retrievedArticle.getGroupId());
    }

    @Test
    public void testGetArticlesByLevel() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Add multiple articles with different levels
        Article beginnerArticle = new Article("Beginner", "group1", "Beginner Title",
                "Abstract", "Body", "keywords", "refs", "groups");
        Article intermediateArticle = new Article("Intermediate", "group1", "Intermediate Title",
                "Abstract", "Body", "keywords", "refs", "groups");

        Database.addArticle(beginnerArticle);
        Database.addArticle(intermediateArticle);

        // Get articles by level
        ArrayList<Article> beginnerArticles = Database.db.getArticlesByLevel("Beginner");
        Database.close();

        // Check that we got the correct number of articles and the right level
        assertEquals(1, beginnerArticles.size());
        assertEquals("Beginner", beginnerArticles.get(0).getLevel());
        assertEquals("Beginner Title", beginnerArticles.get(0).getTitle());
    }

    @Test
    public void testDeleteArticle() throws SQLException {
        Database.init();
        Database.clearDatabase();

        // Add an article then delete it
        Article article = new Article("Expert", "group1", "Delete Test",
                "Abstract", "Body", "keywords", "refs", "groups");
        Database.addArticle(article);

        // Verify article was added
        assertEquals(1, Database.db.getAllArticles().size());

        // Delete the article
        Database.db.deleteArticle("Delete Test");

        // Verify article was deleted
        assertEquals(0, Database.db.getAllArticles().size());
        Database.close();
    }
}

