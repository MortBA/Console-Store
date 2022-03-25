package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Epic4AlternativeTests {

    private Facade facade;

    private String expectedNoItemRegistered = "No items registered yet.";
    private String expectedNoTransactionsRegistered = "No items were bought yet.";

    @BeforeAll
    public static void setupSystem(){
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void setupFacade(){
        facade = new Facade();
    }

    @Test
    public void shouldBeZeroForEmptyTransactions(){
        assertEquals(0.0, facade.getTotalTransactions());
        assertEquals(0.0, facade.getTotalProfit());
        assertEquals(0, facade.getTotalUnitsSold());

        //Should still be zero after adding and not buying an item.
        facade.createItem("ID1", "Black T-shirt", 150.99);
        assertEquals(0.0, facade.getTotalTransactions());
        assertEquals(0.0, facade.getTotalProfit());
        assertEquals(0, facade.getTotalUnitsSold());
    }

    @Test
    public void shouldBeZeroForNonExistentItems(){
        String itemID = "ID1";
        assertEquals(0.0, facade.getProfit(itemID));
        assertEquals(0.0, facade.getUnitsSolds(itemID));

        //Should still be zero after adding and not buying an item.
        facade.createItem(itemID, "Black T-shirt", 150.99);
        assertEquals(0.0, facade.getProfit(itemID));
        assertEquals(0.0, facade.getUnitsSolds(itemID));
    }

    @Test
    public void shouldPrintErrorForNoTransactions(){
        String itemID = "ID1";
        String expectedNoItemMsg = "Item ID1 was not registered yet.";
        assertEquals(expectedNoItemMsg, facade.printItemTransactions(itemID));

        facade.createItem(itemID, "Black T-shirt", 150.99);
        String expectedNoTransactionMsg = "Transactions for item: ID1: Black T-shirt. 150.99 SEK" + TestResources.EOL +
                "No transactions have been registered for item ID1 yet.";
        assertEquals(expectedNoTransactionMsg, facade.printItemTransactions(itemID));
    }

    @Test
    public void shouldPrintEmptyHistory(){
        String expectedPrint = "All purchases made: "  + TestResources.EOL +
                "Total profit: 0.00 SEK"               + TestResources.EOL +
                "Total items sold: 0 units"            + TestResources.EOL +
                "Total purchases made: 0 transactions" + TestResources.EOL +
                "------------------------------------" + TestResources.EOL +
                "------------------------------------" + TestResources.EOL;

        String actualPrint = facade.printAllTransactions();

        assertEquals(expectedPrint, actualPrint);
    }
}