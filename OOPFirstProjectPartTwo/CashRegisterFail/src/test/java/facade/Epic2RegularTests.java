package facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class Epic2RegularTests {

    private Facade facade;

    @BeforeAll
    public static void systemSetup(){
        // Forces the system config to use "." for decimal separators.
        // Otherwise, the tests with comparing strings for doubles will fail.
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void testSetup(){
        facade = new Facade();

        String itemID = "ID1";
        String itemName = "Black T-shirt";
        double price = 150.99;

        facade.createItem(itemID, itemName, price);
    }

    @Test
    public void shouldCreateValidItem(){
        String itemID = "ID2";
        String itemName = "Blue T-shirt";
        double price = 100.00;

        // Verify if the operation worked.
        String expectedOperationResult = "Item ID2 was registered successfully.";
        String actualOperationResult = facade.createItem(itemID, itemName, price);
        assertEquals(expectedOperationResult, actualOperationResult);

        // Verify if the item is indeed stored in the system.
        String expectedOutput = "ID2: Blue T-shirt. 100.00 SEK";
        String actualOutput = facade.printItem(itemID);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldRemoveItem(){
        // Verifies the attempt to remove a registered item
        String expectedMessage = "Item ID1 was successfully removed.";
        String itemID = "ID1";
        assertEquals(expectedMessage, facade.removeItem(itemID));
        assertFalse(facade.containsItem(itemID));
    }

    @Test
    public void shouldPrintOneItem(){
        String expectedOutput = "ID1: Black T-shirt. 150.99 SEK";
        String actualOutput = facade.printItem("ID1");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldBuyOneItem(){
        double actualRegularPrice = facade.buyItem("ID1", 3);
        double expectedRegularPrice = 452.97;
        assertEquals(expectedRegularPrice, actualRegularPrice);

        double actualDiscountPrice = facade.buyItem("ID1", 10);
        double expectedDiscountPrice = 1238.11;
        assertEquals(expectedDiscountPrice, actualDiscountPrice);
    }

    @Test
    public void shouldUpdateItemName(){
        // Verifies the attempt to remove a registered item
        String itemID = "ID1";
        String oldName = "ID1: Black T-shirt. 150.99 SEK";

        assertEquals(oldName, facade.printItem(itemID));

        assertEquals("Item ID1 was updated successfully.", facade.updateItemName(itemID, "T-shirt"));

        String expectedUpdate = "ID1: T-shirt. 150.99 SEK";
        assertEquals(expectedUpdate, facade.printItem(itemID));
    }

    @Test
    public void shouldUpdateItemPrice(){
        // Verifies the attempt to remove a registered item
        String itemID = "ID1";
        String oldPrice = "ID1: Black T-shirt. 150.99 SEK";

        assertEquals(oldPrice, facade.printItem(itemID));

        assertEquals("Item ID1 was updated successfully.", facade.updateItemPrice(itemID, 50.00));

        String expectedUpdate = "ID1: Black T-shirt. 50.00 SEK";
        assertEquals(expectedUpdate, facade.printItem(itemID));
    }

    @Test
    public void shouldPrintAllItems(){

        String expectedOperationResult = "All registered items:" + TestResources.EOL +
                "ID1: Black T-shirt. 150.99 SEK"   + TestResources.EOL +
                "ID2: Blue T-shirt. 100.00 SEK"   + TestResources.EOL +
                "ID3: Yellow T-shirt. 120.00 SEK" + TestResources.EOL +
                "ID4: Red T-shirt. 105.00 SEK"    + TestResources.EOL;

        facade.createItem("ID2", "Blue T-shirt", 100.00);
        facade.createItem("ID3", "Yellow T-shirt", 120.00);
        facade.createItem("ID4", "Red T-shirt", 105.00);

        String actualOperationResult = facade.printAllItems();
        assertEquals(expectedOperationResult, actualOperationResult);
    }
}