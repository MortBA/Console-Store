package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class Epic3AlternateTests {

    private Facade facade;

    private String expectedNoItemRegistered = "No items registered yet.";
    private String expectedNoReviewsRegistered = "No items were reviewed yet.";

    @BeforeAll
    public static void systemSetup(){
        // Forces the system config to use "." for decimal separators.
        // Otherwise, the tests with comparing strings for doubles will fail.
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void testSetup(){
        // Restarts the facade before each test.
        facade = new Facade();
    }

    @Test
    public void shouldPrintErrorForInvalidReview(){
        String itemID = "ID1";
        String expectedErrorMsg = "Item ID1 was not registered yet.";
        String expectedGradeError = "Grade values must be between 1 and 5.";

        // Checking messsages for non-existing item.
        assertEquals(expectedErrorMsg, facade.reviewItem(itemID, 4));
        assertEquals(expectedErrorMsg, facade.reviewItem(itemID, "Should be cheaper.", 3));

        // Checking messages for invalid grade range.
        facade.createItem(itemID, "Black T-shirt", 150.99);
        assertEquals(expectedGradeError, facade.reviewItem(itemID, "Should be cheaper.", -5));
        assertEquals(expectedGradeError, facade.reviewItem(itemID, -1));

        assertEquals(expectedGradeError, facade.reviewItem(itemID, "Should be cheaper.", 100));
        assertEquals(expectedGradeError, facade.reviewItem(itemID, 7));
    }

    @Test
    public void shouldGetEmptyItemComments(){
        String itemID = "ID1";
        facade.createItem(itemID, "Black T-shirt", 150.99);

        // Item ID1 has no comments or reviews.
        List<String> actualComments = facade.getItemComments(itemID);

        assertTrue(actualComments.isEmpty());
        assertEquals(0, facade.getNumberOfReviews(itemID));

        double actualMean = facade.getItemMeanGrade(itemID);
        assertEquals(0.0, actualMean);
    }

    @Test
    public void shouldPrintErrorForEmptyReviews(){
        String itemID2 = "ID2";
        String expectedNoItem = "Item ID2 was not registered yet.";
        String actualOperation = facade.getPrintedReviews(itemID2);
        assertEquals(expectedNoItem, actualOperation);

        facade.createItem(itemID2, "Winter jacket", 999.50);
        String expectedNonReviewed = "Review(s) for ID2: Winter jacket. 999.50 SEK" + TestResources.EOL +
                "The item Winter jacket has not been reviewed yet.";
        String actualNonReviewed = facade.getPrintedReviews(itemID2);
        assertEquals(expectedNonReviewed, actualNonReviewed);
    }

    @Test
    public void shouldPrintErrorWhenInvalidReviewIndex(){
        String itemID = "ID5";
        facade.createItem(itemID, "Leather jacket", 1200.00);

        String expectedReview1 = "Item Leather jacket has not been reviewed yet.";
        assertEquals(expectedReview1, facade.getPrintedItemReview(itemID, 0));
        assertEquals(expectedReview1, facade.getPrintedItemReview(itemID, 1));

        facade.reviewItem(itemID, 5);
        String expectedInvalidIndex = "Invalid review number. Choose between 1 and 1.";
        assertEquals(expectedInvalidIndex, facade.getPrintedItemReview(itemID, 0));
        assertEquals(expectedInvalidIndex, facade.getPrintedItemReview(itemID, 10));

        facade.reviewItem(itemID, 3);
        expectedInvalidIndex = "Invalid review number. Choose between 1 and 2.";
        assertEquals(expectedInvalidIndex, facade.getPrintedItemReview(itemID, -2));
        assertEquals(expectedInvalidIndex, facade.getPrintedItemReview(itemID, 3));
    }

    @Test
    public void shouldPrintErrorWhenNoReviewCreated(){
        // Assessing error message for retrieving reviews when no item was registered.
        assertEquals(expectedNoItemRegistered, facade.printMostReviewedItems());
        assertEquals(expectedNoItemRegistered, facade.printLeastReviewedItems());
        assertEquals(expectedNoItemRegistered, facade.printBestReviewedItems());
        assertEquals(expectedNoItemRegistered, facade.printWorseReviewedItems());
        assertEquals(expectedNoItemRegistered, facade.printAllReviews());

        // Register with one item, but remain with no reviews.
        String itemID = "ID2";
        facade.createItem(itemID, "Winter jacket", 999.50);
        assertEquals(expectedNoReviewsRegistered, facade.printMostReviewedItems());
        assertEquals(expectedNoReviewsRegistered, facade.printLeastReviewedItems());
        assertEquals(expectedNoReviewsRegistered, facade.printBestReviewedItems());
        assertEquals(expectedNoReviewsRegistered, facade.printWorseReviewedItems());
        assertEquals(expectedNoReviewsRegistered, facade.printAllReviews());
    }

    @Test
    public void shouldGetEmptyWhenNoReviewCreated(){
        // Should be empty when no item is registered
        assertTrue(facade.getBestReviewedItems().isEmpty() );
        assertTrue(facade.getWorseReviewedItems().isEmpty());
        assertTrue(facade.getMostReviewedItems().isEmpty() );
        assertTrue(facade.getLeastReviewedItems().isEmpty());

        // Should still be empty when no item has received a review yet.
        String itemID = "ID2";
        facade.createItem(itemID, "Winter jacket", 999.50);

        assertTrue(facade.getBestReviewedItems().isEmpty() );
        assertTrue(facade.getWorseReviewedItems().isEmpty());
        assertTrue(facade.getMostReviewedItems().isEmpty() );
        assertTrue(facade.getLeastReviewedItems().isEmpty());
    }
}

