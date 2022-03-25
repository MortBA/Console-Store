package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class Epic2AlternativeTests {

    private Facade facade;

    @BeforeAll
    public static void systemSetup(){
        // Forces the system config to use "." for decimal separators.
        // Otherwise, the tests with comparing strings for doubles will fail.
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void testSetup(){
        // Starts with an empty Facade before each test.
        facade = new Facade();
    }

    @Test
    public void shouldPrintErrorWhenInvalidItemData(){
        // Verifies the attempt to remove a registered item
        String expectedOperationResult = "Invalid data for item.";

        assertEquals(expectedOperationResult, facade.createItem("", "Some name", 50.00));
        assertEquals(expectedOperationResult, facade.createItem("ID1", "", 50.00));
        assertEquals(expectedOperationResult, facade.createItem("ID1", "Some name", -50.00));
    }

    @Test
    public void shouldNotCrashWhenRemovingInvalidItems(){
        // Verifies the attempt to remove a non-existing item
        String itemID = "ID1";
        String expectedResult = "Item ID1 could not be removed.";
        assertFalse(facade.containsItem(itemID));
        assertEquals(expectedResult, facade.removeItem(itemID));
    }

    @Test
    public void shouldPrintErrorWhenNoItemRegistered(){
        String expectedOutput = "Item ID1 was not registered yet.";
        String actualOutput = facade.printItem("ID1");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldNotBuyWhenNoItemRegistered(){
        double expectedRegularPrice = -1.0;
        double actualRegularPrice = facade.buyItem("ID1", 3);
        assertEquals(expectedRegularPrice, actualRegularPrice);
    }

    @Test
    public void shouldPrintErrorWhenInvalidUpdate(){
        String itemID = "ID1";
        String expectedNotRegistered = "Item " + itemID + " was not registered yet.";
        String expectedInvalidData = "Invalid data for item.";

        assertEquals(expectedNotRegistered, facade.updateItemPrice(itemID, -5.0));
        assertEquals(expectedNotRegistered, facade.updateItemName(itemID, "Some name"));
        assertEquals(expectedNotRegistered, facade.updateItemName(itemID, ""));

        facade.createItem(itemID, "Black T-shirt", 150.99);

        assertEquals(expectedInvalidData, facade.updateItemPrice(itemID, -5.0));
        assertEquals(expectedInvalidData, facade.updateItemPrice(itemID, 0.0));
        assertEquals(expectedInvalidData, facade.updateItemName(itemID, ""));
    }

    @Test
    public void shouldPrintErrorWhenPrintingEmptyItems(){
        String expectedErrorMessage  = "No items registered yet.";
        assertEquals(expectedErrorMessage, facade.printAllItems());
    }

}