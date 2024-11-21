import database.Database;
import org.junit.jupiter.api.Test;
import util.Article;

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

}
