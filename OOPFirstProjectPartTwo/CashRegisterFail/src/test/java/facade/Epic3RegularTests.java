package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class Epic3RegularTests {

    private Facade facade;

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

        String[][] testDataItems = {
                {"ID1", "Black T-shirt" , "150.99"},
                {"ID2", "Winter jacket" , "999.50"},
                {"ID3", "High heels"    , "450.20"},
                {"ID4", "Sweatpants"    , "120.20"},
                {"ID5", "Leather jacket", "1200.00"},
        };
        for (String[] row : testDataItems) {
            facade.createItem(row[0], row[1], Double.parseDouble(row[2]));
        }

        String[][] testDataReviews = {
                {"ID1", "Good material for the price.", "4"},
                {"ID1", "", "5"},
                {"ID1", "Great item but could be better.", "4"},
                {"ID3", "They are less comfortable than I thought.", "3"},
                {"ID5", "Not worth the price.", "1"},
                {"ID5", "The delivery took too long.", "2"},
                {"ID5", "", "2"}
        };
        for (String[] row : testDataReviews) {
            facade.reviewItem(row[0], row[1], Integer.parseInt(row[2]));
        }
    }

    @Test
    public void shouldSuccessfullyCreateReview(){
        String itemID = "ID4";
        String firstComment = "Very comfy and comfortable..";
        int firstGrade = 5;

        String secondComment = "Low quality materials.";
        int secondGrade = 3;
        int thirdGrade  = 2;

        String successfulReview = "Your item review was registered successfully.";
        assertEquals(successfulReview, facade.reviewItem(itemID, firstComment, firstGrade));
        assertEquals(successfulReview, facade.reviewItem(itemID, secondComment, secondGrade));
        assertEquals(successfulReview, facade.reviewItem(itemID, thirdGrade));

        double expectedMean = 3.3;

        List<String> actualComments = facade.getItemComments(itemID);
        double actualMean = facade.getItemMeanGrade(itemID);

        assertEquals(firstComment, actualComments.get(0));
        assertEquals(secondComment, actualComments.get(1));
        assertEquals(2, actualComments.size());

        assertEquals(expectedMean, actualMean);
    }

    @Test
    public void shouldGetReviewScoresFromAllItems(){
        String itemID = "ID1";
        double expectedMean = 4.3;
        int expectedNumReviews = 3;

        assertEquals(expectedNumReviews, facade.getNumberOfReviews(itemID));
        assertEquals(expectedMean, facade.getItemMeanGrade(itemID));
    }

    @Test
    public void shouldPrintAllReviews(){
        String expectedResult = "All registered reviews:"            + TestResources.EOL +
                "------------------------------------"               + TestResources.EOL +
                "Review(s) for ID1: Black T-shirt. 150.99 SEK"       + TestResources.EOL +
                "Grade: 4.Good material for the price."              + TestResources.EOL +
                "Grade: 5."                                          + TestResources.EOL +
                "Grade: 4.Great item but could be better."           + TestResources.EOL +
                "------------------------------------"               + TestResources.EOL +
                "Review(s) for ID3: High heels. 450.20 SEK"          + TestResources.EOL +
                "Grade: 3.They are less comfortable than I thought." + TestResources.EOL +
                "------------------------------------"               + TestResources.EOL +
                "Review(s) for ID5: Leather jacket. 1200.00 SEK"     + TestResources.EOL +
                "Grade: 1.Not worth the price."                      + TestResources.EOL +
                "Grade: 2.The delivery took too long."               + TestResources.EOL +
                "Grade: 2."                                          + TestResources.EOL +
                "------------------------------------"               + TestResources.EOL ;
        assertEquals(expectedResult, facade.printAllReviews());
    }

    @Test
    public void shouldGetCommentsFromItem(){
        String itemID = "ID1";
        List<String> expectedComments = new ArrayList<>();
        expectedComments.add("Good material for the price.");
        expectedComments.add("Great item but could be better.");

        // Item ID1 has only two comments written by reviews.
        // Reviews with empty comments are not added when retrieving the
        // However, ID1 has a total of 3 reviews made.
        List<String> actualComments = facade.getItemComments(itemID);

        assertEquals(expectedComments.size(), actualComments.size());
        for(int i = 0; i < expectedComments.size(); i++){
            String expectedComment = expectedComments.get(i);
            String actualComment = actualComments.get(i);
            assertEquals(expectedComment, actualComment);
        }
    }

    @Test
    public void shouldPrintReviewsFromItem(){
        String itemID1 = "ID1";
        String expectedPrintedReviews = "Review(s) for ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL +
                        "Grade: 4.Good material for the price." + TestResources.EOL +
                        "Grade: 5." + TestResources.EOL +
                        "Grade: 4.Great item but could be better." + TestResources.EOL;
        String actualPrintedReviews = facade.getPrintedReviews(itemID1);

        assertEquals(expectedPrintedReviews, actualPrintedReviews);
    }

    @Test
    public void shouldGetSpecificItemReviews(){
        String itemID = "ID5";
        String expectedReview1 = "Grade: 1.Not worth the price.";
        String expectedReview2 = "Grade: 2.";

        assertEquals(expectedReview1, facade.getPrintedItemReview(itemID, 1));
        assertEquals(expectedReview2, facade.getPrintedItemReview(itemID, 3));
    }

    @Test
    public void shouldGetItemsWithMostReviews(){
        List<String> expectedMost = new ArrayList<>();
        expectedMost.add("ID1");
        expectedMost.add("ID5");

        List<String> actualMost = facade.getMostReviewedItems();
        assertEquals(expectedMost, actualMost);

        facade.reviewItem("ID1", 3);
        expectedMost.remove("ID5");
        actualMost = facade.getMostReviewedItems();

        assertEquals(expectedMost, actualMost);
    }

    @Test
    public void shouldPrintItemsWithMostReviews(){
        String expectedMost1 = "Most reviews: 3 review(s) each."  + TestResources.EOL +
                "ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL +
                "ID5: Leather jacket. 1200.00 SEK" + TestResources.EOL;

        String actualMost1 = facade.printMostReviewedItems();
        assertEquals(expectedMost1, actualMost1);

        facade.reviewItem("ID1", 3);
        String expectedMost2 = "Most reviews: 4 review(s) each." + TestResources.EOL +
                "ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL;

        String actualMost2 = facade.printMostReviewedItems();
        assertEquals(expectedMost2, actualMost2);
    }


    @Test
    public void shouldGetItemsWithLeastReviews(){
        // At this stage ID2 and ID4 have no reviews.
        List<String> expectedLeast = new ArrayList<>();
        expectedLeast.add("ID3");

        List<String> actualLeastReviewed = facade.getLeastReviewedItems();

        assertEquals(expectedLeast, actualLeastReviewed);

        // Add more reviews to test when there should be only one item with the least reviews.
        facade.reviewItem("ID2", "Great item."     , 5);
        facade.reviewItem("ID2", "Had better."     , 4);
        facade.reviewItem("ID3", "Not good at all.", 1);

        expectedLeast = new ArrayList<>();
        expectedLeast.add("ID2");
        expectedLeast.add("ID3");

        actualLeastReviewed = facade.getLeastReviewedItems();
        assertEquals(expectedLeast, actualLeastReviewed);
    }

    @Test
    public void shouldPrintItemsWithLeastReviews(){
        // At this stage ID2 and ID4 have no reviews.
        String expectedLeast1 = "Least reviews: 1 review(s) each." + TestResources.EOL +
                "ID3: High heels. 450.20 SEK" + TestResources.EOL;
        String actualLeast1 = facade.printLeastReviewedItems();
        assertEquals(expectedLeast1, actualLeast1);

        // Add more reviews to test when there should be only one item with the least reviews.
        facade.reviewItem("ID2", "Great item."     , 5);
        facade.reviewItem("ID2", "Had better."     , 4);
        facade.reviewItem("ID3", "Not good at all.", 1);

        String expectedLeast2 = "Least reviews: 2 review(s) each."  + TestResources.EOL +
                "ID2: Winter jacket. 999.50 SEK" + TestResources.EOL +
                "ID3: High heels. 450.20 SEK" + TestResources.EOL;
        String actualLeast2 = facade.printLeastReviewedItems();
        assertEquals(expectedLeast2, actualLeast2);
    }

    @Test
    public void shouldPrintItemsWithBestReviews(){
        String expectedBest1 = "Items with best mean reviews:" + TestResources.EOL +
                "Grade: 4.3" + TestResources.EOL +
                "ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL;

        String actualBest1 = facade.printBestReviewedItems();
        assertEquals(expectedBest1, actualBest1);

        facade.reviewItem("ID2", "Great item."     , 5);
        facade.reviewItem("ID2", "Had better."     , 4);
        facade.reviewItem("ID2",4);

        String expectedBest2 = "Items with best mean reviews:" + TestResources.EOL +
                "Grade: 4.3" + TestResources.EOL +
                "ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL +
                "ID2: Winter jacket. 999.50 SEK" + TestResources.EOL ;
        String actualBest2 = facade.printBestReviewedItems();
        assertEquals(expectedBest2, actualBest2);
    }

    @Test
    public void shouldPrintItemsWithWorseReviews(){
        String expectedWorse = "Items with worst mean reviews:" + TestResources.EOL +
                "Grade: 1.6" + TestResources.EOL +
                "ID5: Leather jacket. 1200.00 SEK" + TestResources.EOL;

        String actualWorse = facade.printWorseReviewedItems();
        assertEquals(expectedWorse, actualWorse);

        String itemID3 = "ID3";
        facade.reviewItem(itemID3,1);
        facade.reviewItem(itemID3,1);

        expectedWorse = "Items with worst mean reviews:" + TestResources.EOL +
                "Grade: 1.6" + TestResources.EOL +
                "ID3: High heels. 450.20 SEK" + TestResources.EOL +
                "ID5: Leather jacket. 1200.00 SEK" + TestResources.EOL;
        actualWorse = facade.printWorseReviewedItems();
        assertEquals(expectedWorse, actualWorse);
    }

    @Test
    public void shouldGetItemsWithWorseReviews(){
        List<String> expectedWorseReviews = new ArrayList<>();
        expectedWorseReviews.add("ID5");

        List<String> actualWorseReviews = facade.getWorseReviewedItems();
        assertEquals(expectedWorseReviews, actualWorseReviews);

        // Add a few more bad reviews for an existing item.
        facade.reviewItem("ID3",1);
        facade.reviewItem("ID3",1);

        expectedWorseReviews = new ArrayList<>();
        expectedWorseReviews.add("ID3");
        expectedWorseReviews.add("ID5");
        actualWorseReviews = facade.getWorseReviewedItems();
        assertEquals(expectedWorseReviews, actualWorseReviews);
    }

    @Test
    public void shouldGetItemsWithBestReviews(){
        List<String> expectedBestReviews = new ArrayList<>();
        expectedBestReviews.add("ID1");

        List<String> actualBestReviews = facade.getBestReviewedItems();
        assertEquals(expectedBestReviews, actualBestReviews);

        // Add a few more bad reviews for an existing item.
        facade.reviewItem("ID2", "Great item."     , 5);
        facade.reviewItem("ID2", "Had better."     , 4);
        facade.reviewItem("ID2",4);

        expectedBestReviews = new ArrayList<>();
        expectedBestReviews.add("ID1");
        expectedBestReviews.add("ID2");
        actualBestReviews = facade.getBestReviewedItems();
        assertEquals(expectedBestReviews, actualBestReviews);
    }
}
