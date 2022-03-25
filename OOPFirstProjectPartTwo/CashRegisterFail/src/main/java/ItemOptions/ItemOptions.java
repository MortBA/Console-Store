package ItemOptions;
import CashRegister.SystemOutput;
import CashRegister.UserInput;
import ReviewOptions.Reviews;
import TransacHistory.TransacHistory;

import java.util.ArrayList;

public class ItemOptions {

    // Constructor dependant variables
    SystemOutput sysOut;
    UserInput readIn;
    private final ArrayList<Item> items;
    TransacHistory saveTransaction;
    final boolean FACADE;

    // Constants
    final int MAX_MENU_VALUE = 7;
    final int ZERO = 0;
    final int CHANGE_NAME = 1;
    final int CHANGE_PRICE = 2;
    final int NULL_POINTER = -1;
    final String NO_ITEMS_REGISTERED = "No items registered yet.";
    final String INVALID_DATA = "Invalid data for item.";
    final String INVALID_MENU_OPTION = "Invalid menu option. Please type another option";
    final String MENU_INPUT = "Enter an option: ";
    final String ASK_ITEM_ID = "Enter items ID: ";
    final String ASK_ITEM_NAME = "Enter items name: ";
    final String ASK_ITEM_PRICE = "Enter items price: ";
    final String ASK_ITEM_QUANTITY = "Enter quantity of item: ";
    final String NAME_NULL = null;
    final String ID_NULL = null;
    final double PRICE_NULL = 0.00;


    /*
     ***********************
     *     CONSTRUCTOR     *
     ***********************
     */

    public ItemOptions(TransacHistory transactionHistory, boolean test) {
        sysOut = new SystemOutput();
        readIn = new UserInput();
        items = new ArrayList<>();
        this.saveTransaction = transactionHistory;
        this.FACADE = test;
    }



    /*
     ***********************
     *     MAIN METHOD     *
     ***********************
     */

    public void runProgram() {
        int menuChoice;
        do {
            do {
                sysOut.itemMenu();
                menuChoice = readIn.getUserOption(MAX_MENU_VALUE, MENU_INPUT, INVALID_MENU_OPTION);
            } while(menuChoice < ZERO || menuChoice > MAX_MENU_VALUE);
            switch(menuChoice)  {
                case 0: // returns to main menu
                    break;
                case 1:
                    addItem(ID_NULL, NAME_NULL, PRICE_NULL);
                    break;
                case 2:
                    delItem(ID_NULL);
                    break;
                case 3:
                    printAllItems();
                    break;
                case 4:
                    buyItem(ID_NULL, ZERO);
                    break;
                case 5:
                    newItemName(ID_NULL, NAME_NULL);
                    break;
                case 6:
                    newItemPrice(ID_NULL, PRICE_NULL);
                    break;
                case 7:
                    printItem(ID_NULL);
                    break;
                default:
                    //CashRegister.callError();
            }
        } while(menuChoice != ZERO);


    }

    /*
     ***********************
     *    EPIC FEATURES    *
     ***********************
     */

    public String addItem(String itemID, String itemName, double unitPrice) {
        double price;
        String name, success, id;

        // Assign values depending on if it's a FACADE test or user run.
        id = (FACADE ? itemID : lookUpItem());
        name = (FACADE ? itemName : readIn.readString(ASK_ITEM_NAME, INVALID_DATA));
        price = (FACADE ? unitPrice : sysOut.roundDecimal(readIn.readDouble(ASK_ITEM_PRICE, INVALID_DATA)));

        if(!itemTest(id, name, price)) {
            System.out.println(INVALID_DATA);
            return INVALID_DATA;
        }

        // Adds item to our ArrayList
        items.add(new Item(id, name, price)); // Object is stored in a list, so we don't need a reference.

        // Print and return success message.
        success = "Item " + id + " was registered successfully.";
        System.out.println(success);
        saveTransaction.hasRegistered();
        return success;
    }

    public String delItem(String itemID) {
        int index;
        String error, success, id;

        // If not a facade then ask user for ID else use the ID from the facade.
        id = (FACADE ? itemID : readIn.readID(ASK_ITEM_ID, INVALID_DATA));

        // Lookup if ID exists and return index in ArrayList
        index = findItem(id);

        //If ID does not exist then we print error.
        if (index == -1) {
            error = "Item " + id + " could not be removed.";
            System.out.println(error);
            return error;
        }

        // ID Exists
        items.remove(index);
        success = "Item " + id + " was successfully removed.";
        System.out.println(success);
        return success;
    }

    public String printAllItems() {
        String itemInfo;
        String headline;
        StringBuilder allItems;
        headline = "All registered items:";
        allItems = new StringBuilder(headline + System.lineSeparator()); // allItems is our final String to RETURN

        if(items.isEmpty()) { // If no items registered then print and return
            System.out.println(NO_ITEMS_REGISTERED);
            return NO_ITEMS_REGISTERED;
        }

        System.out.println(headline); // Print the headline

        for (Item item : items) { // Loop through all items and print the info
            itemInfo = item.toString();
            System.out.println(itemInfo);
            allItems.append(itemInfo).append(System.lineSeparator());
        }

        return allItems.toString();
    }

    public double buyItem(String itemID, int amount) {
        int quantity, index, discounted, totalItems;
        String success, id, error;
        double totalPrice, itemPrice;

        if(items.size() == 0) {
            System.out.println(NO_ITEMS_REGISTERED);
            return NULL_POINTER;
        }

        id = (FACADE ? itemID : readIn.readID(ASK_ITEM_ID, INVALID_DATA));
        quantity = (FACADE ? amount : readIn.readInt(ASK_ITEM_QUANTITY, INVALID_DATA));

        index = findItem(id);
        error = "Item " + id + " was not registered yet.";

        if(index == -1) {
            System.out.println(error);
            return index;
        }

        itemPrice = items.get(index).getPrice();

        if (quantity > 4) {
            discounted = quantity - 4;
            quantity = 4;
        } else {
            discounted = 0;
        }

        totalItems = discounted + quantity;
        totalPrice = sysOut.roundDecimal((quantity * itemPrice) + (discounted * (itemPrice * (0.7))));

        success = "Successfully purchased " + totalItems +
                " x Item " + id + ": " +
                sysOut.decimalFix(totalPrice) + " SEK.";

        System.out.println(success);

        saveTransaction.purchaseSave(id, totalItems, totalPrice);
        return totalPrice;
    }

    public String newItemName(String itemID, String itemName) {
        return changeItem(itemID,itemName,PRICE_NULL,CHANGE_NAME);

    }

    public String newItemPrice(String itemID, double itemPrice) {
        return changeItem(itemID,NAME_NULL,itemPrice,CHANGE_PRICE);
    }

    // property: 1 == name, 2 == price
    // This method is only called from newItemName() or newItemPrice()
    private String changeItem(String itemID, String newName, double newPrice, int property)    {
        int index;
        double price;
        String name, error, success, id;

        id = (FACADE ? itemID : readIn.readID(ASK_ITEM_ID, INVALID_DATA));
        error = "Item " + id + " was not registered yet.";
        success = "Item " + id + " was updated successfully.";

        // Finds where the item is in our ArrayList
        index = findItem(id);
        if(index == -1)  { // If item is not added yet:
            System.out.println(error);
            return error;
        }

        switch (property) { // If Item is added:
            case 1: // Check if we are changing name:
                name = (FACADE ? newName : readIn.readString(ASK_ITEM_NAME, INVALID_DATA));

                if(name.isEmpty()) {
                    System.out.println(INVALID_DATA);
                    return INVALID_DATA;
                }

                items.get(index).setName(name);
                break;
            case 2: // Check if we are changing price:
                price = (FACADE ? newPrice : readIn.readDouble(ASK_ITEM_PRICE, INVALID_DATA));

                if(price <= 0) {
                    System.out.println(INVALID_DATA);
                    return INVALID_DATA;
                }

                items.get(index).setPrice(price);
                break;
            default: // If this method was somehow called with wrong PROPERTY
                System.exit(1);
        }
        System.out.println(success);
        return success;
    }

    public String printItem(String itemID) {
        String error, itemInfo, id;
        int index;

        id = (FACADE ? itemID : readIn.readID(ASK_ITEM_ID, INVALID_DATA));
        error = "Item " + id + " was not registered yet.";
        index = findItem(id);

        if (index == -1) {
            System.out.println(error);
            return error;
        }

        itemInfo = items.get(index).toString();

        System.out.println(itemInfo);
        return itemInfo;
    }

    /*
     ************************
     *    FACADE METHODS    *
     ************************
     */

    // Tests if all items pass the requirements.
    private boolean itemTest(String id, String name, double price)  {
        id = (id.startsWith("ID") ? id.substring(2) : id);
        return !FACADE || (readIn.isNumber(id) && !name.isEmpty() && !(price < 0));
    }


    /*
     ***********************
     *   GENERAL METHODS   *
     ***********************
     */

    // This method will check if id exists then return the index of item in our ArrayList.
    private int findItem(String searchQuery)  {
        int index;
        index = -1;

        if(items.size() == 0) // In case we forgot to check before calling method.
            return index;

        for(int i = 0; i < items.size(); i++)   {
            if(items.get(i).getId().equals(searchQuery)) {
                return i;
            }
        }

        return index;
    }

    // This method asks for and ID as input and checks if it's a duplicate
    private String lookUpItem()    {
        String id;
        boolean checkDuplicate;

        do {
            id = readIn.readID(ASK_ITEM_ID, INVALID_DATA);

            if(!items.isEmpty() && findItem(id) != -1) { // If list is not empty, then check if ID is duplicate:
                System.out.println(INVALID_DATA);
                checkDuplicate = true;
            }
            else    { // If list is empty or ID is not duplicate:
                checkDuplicate = false;
            }
        } while(checkDuplicate);
        return id;
    }


    // returns a deep copy of all the items in the shop.
    public ArrayList<Item> copyItems()  {
        String name, id;
        double price;
        ArrayList<Item> itemsCopy = new ArrayList<>();

        for(int i = 0; i < items.size(); i++)   {
            id = items.get(i).getId();
            name = items.get(i).getName();
            price = items.get(i).getPrice();
            itemsCopy.add(
                    new Item(id, name, price)
            ); // Create a new object with same values and add to new arraylist

            for (int j = 0; j < items.get(i).reviewsList.size(); j++) { //Also copies item reviews
                itemsCopy.get(i).reviewsList.add(
                        new Reviews(
                                items.get(i).getReviewList().get(j).getReviewGrade(),
                                items.get(i).getReviewList().get(j).getReviewComment()
                        )
                );
            }
        }
        return itemsCopy;
    }


    /*
     ***********************
     *      John Webb      *
     ***********************
     */


    // John Essential Methods:
    // Finds and returns an item object by its ID
    public Item findItemObject(String ID) {
        return items.get(findItem(ID));
    }


    public boolean existenceChecker (String id){//Checks if such item currently exists
        boolean existence = false;
        for(Item currentItem : items){
            if(id.equals(currentItem.getId())){
                existence = true;
            }
        }
        return existence;
    }
}