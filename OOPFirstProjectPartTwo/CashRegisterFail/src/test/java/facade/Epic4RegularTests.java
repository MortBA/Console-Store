package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Epic4RegularTests {

    private Facade facade;

    @BeforeAll
    public static void setupSystem(){
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void setupFacade(){
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
        // ID1: 4 purchases, 17 items sold, 2340.34 = (452.97 + 709.65 + 1026.73 + 150.99)
        // ID2: 2 purchases,  4 items sold, 3998.00 = (999.50 + 2998.50)
        // ID3: 2 purchases,  2 items sold,  900.40 = (450.20 + 450.20)
        // ID4: 0 purchases,  0 items sold,    0.00
        // ID5: 1 purchases,  2 items sold, 2400.00 = (2400.00)
        // Total profit                   = 9638.74
        String[][] testPurchaseData = {
                {"ID1", "3"}, {"ID1", "5"}, {"ID1", "8"}, {"ID1", "1"},
                {"ID2", "1"}, {"ID2", "3"},
                {"ID3", "1"}, {"ID3", "1"},
                {"ID5", "2"}
        };
        for (int i = 0; i < testPurchaseData.length; i++) {
            String[] row = testPurchaseData[i];
            facade.buyItem(row[0], Integer.parseInt(row[1]));
        }
    }

    @Test
    public void shouldGetTotalProfit(){
        double expectedProfit = 9638.74;
        double actualProfit = facade.getTotalProfit();
        assertEquals(expectedProfit, actualProfit);
    }

    @Test
    public void shouldGetTotalUnitsSold(){
        int expectedUnits = 25;
        int actualUnits = facade.getTotalUnitsSold();
        assertEquals(expectedUnits, actualUnits);
    }

    @Test
    public void shouldGetTotalTransactions(){
        int expectedTransactions = 9;
        int actualTransactions = facade.getTotalTransactions();
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void shouldGetProfitFromAnItem(){
        String itemID1 = "ID1";
        double expectedProfitID1 = 2340.34;
        double actualProfitID1 = facade.getProfit(itemID1);
        assertEquals(expectedProfitID1, actualProfitID1);

        String itemID3 = "ID3";
        double expectedProfitID3 = 900.40;
        double actualProfitID3 = facade.getProfit(itemID3);
        assertEquals(expectedProfitID3, actualProfitID3);

        String itemID4 = "ID4";
        double expectedProfitID4 = 0;
        double actualProfitID4 = facade.getProfit(itemID4);
        assertEquals(expectedProfitID4, actualProfitID4);
    }

    @Test
    public void shouldGetSoldUnitsFromItem(){
        String itemID1 = "ID1";
        int expectedUnitsID1 = 17;
        int actualUnitsID1 = facade.getUnitsSolds(itemID1);
        assertEquals(expectedUnitsID1, actualUnitsID1);

        String itemID2 = "ID2";
        int expectedUnitsID2 = 4;
        int actualUnitsID2 = facade.getUnitsSolds(itemID2);
        assertEquals(expectedUnitsID2, actualUnitsID2);

        String itemID4 = "ID4";
        int expectedUnitsID4 = 0;
        int actualUnitsID4 = facade.getUnitsSolds(itemID4);
        assertEquals(expectedUnitsID4, actualUnitsID4);
    }

    @Test
    public void shouldPrintTransactionsFromItem(){
        String itemID1 = "ID1";
        // ID1: 4 purchases, 19 items sold, 2551.79 = (452.97 + 709.65 + 1238.11 + 150.99)
        String expectedPrintID1 = "Transactions for item: ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL +
                "ID1: 3 item(s). 452.97 SEK"   + TestResources.EOL +
                "ID1: 5 item(s). 709.65 SEK"   + TestResources.EOL +
                "ID1: 8 item(s). 1026.73 SEK" + TestResources.EOL +
                "ID1: 1 item(s). 150.99 SEK"   + TestResources.EOL;

        String actualPrintID1 = facade.printItemTransactions(itemID1);
        assertEquals(expectedPrintID1, actualPrintID1);

        String itemID4 = "ID4";
        String expectedPrintID4 = "Transactions for item: ID4: Sweatpants. 120.20 SEK" + TestResources.EOL +
                "No transactions have been registered for item ID4 yet.";
        String actualPrintID4 = facade.printItemTransactions(itemID4);
        assertEquals(expectedPrintID4, actualPrintID4);
    }

    @Test
    public void shouldPrintAllTransactions(){
        String expectedPrint = "All purchases made: "  + TestResources.EOL +
                "Total profit: 9638.74 SEK"            + TestResources.EOL +
                "Total items sold: 25 units"           + TestResources.EOL +
                "Total purchases made: 9 transactions" + TestResources.EOL +
                "------------------------------------" + TestResources.EOL +
                "ID1: 3 item(s). 452.97 SEK"           + TestResources.EOL +
                "ID1: 5 item(s). 709.65 SEK"           + TestResources.EOL +
                "ID1: 8 item(s). 1026.73 SEK"         + TestResources.EOL +
                "ID1: 1 item(s). 150.99 SEK"           + TestResources.EOL +
                "ID2: 1 item(s). 999.50 SEK"           + TestResources.EOL +
                "ID2: 3 item(s). 2998.50 SEK"          + TestResources.EOL +
                "ID3: 1 item(s). 450.20 SEK"           + TestResources.EOL +
                "ID3: 1 item(s). 450.20 SEK"           + TestResources.EOL +
                "ID5: 2 item(s). 2400.00 SEK"          + TestResources.EOL +
                "------------------------------------" + TestResources.EOL;

        String actualPrint = facade.printAllTransactions();

        assertEquals(expectedPrint, actualPrint);
    }

    @Test
    public void shouldPrintMostProfitableItems() {
        String expectedProfitableItems = "Most profitable items: " + TestResources.EOL +
                "Total profit: 3998.00 SEK" + TestResources.EOL +
                "ID2: Winter jacket. 999.50 SEK"  + TestResources.EOL;

        String actualProfitable = facade.printMostProfitableItems();

        assertEquals(expectedProfitableItems, actualProfitable);
    }
}