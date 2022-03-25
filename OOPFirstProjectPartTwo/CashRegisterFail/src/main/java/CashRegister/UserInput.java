package CashRegister;
import ItemOptions.ItemOptions;
import ItemOptions.Item;

import java.util.ArrayList;
import java.util.Scanner;
public class UserInput {
    public Scanner readIn = new Scanner(System.in);
    String askUserFor; // This will be output before the user input.
    String informUserError; // This will be output if user did something wrong.
    public ItemOptions itemData;
    ArrayList<Item> items;



    // Constructor
    public UserInput()   {
        items = new ArrayList<>();
    }


    /*
    ***********************
    *       METHODS       *
    *       WILLIAM       *
    ***********************
    */

    // Returns a positive integer
    public int readInt(String input, String error)    {
        setStrings(input, error);
        return Integer.parseInt(getNumber());
    }


    // Returns a String
    public String readString(String askInput, String error)  {
        String userInput;
        setStrings(askInput, error);

        do {
            System.out.print(this.askUserFor);
            userInput = readIn.nextLine();
            if(userInput == null || userInput.isBlank() || userInput.isEmpty())
                System.out.println(this.informUserError);
        } while(userInput == null || userInput.isBlank() || userInput.isEmpty());
        return userInput;
    }

    // Asks user for a String to add an optional comment for reviews
    public String readComment(String askInput) {
        String userInput;
        setInputMsg(askInput);
        System.out.println(this.askUserFor);
        userInput = readIn.nextLine();
        return userInput;
    }

    // Returns a positive double
    public double readDouble(String input, String error) {
        setStrings(input, error);
        return Double.parseDouble(getNumber());
    }

    public String readID(String input, String error)    {
        String id;
        boolean validInput;
        setStrings(input, error);
        do {
            System.out.print(this.askUserFor);
            id = readIn.nextLine().trim(); // Handles if they enter "     " before a number to avoid "ID     1" for example
            id = (id.startsWith("ID") ? id : "ID" + id); // Removes ID from input.
            validInput = isNumber(id.substring(2));
            if(!validInput)
                System.out.println(this.informUserError);
        } while(!validInput);
        return id;
    }


    // Gets user input as a number
    private String getNumber()   {
        String input;
        boolean validNumber;
        do {
            System.out.print(this.askUserFor);
            input = readIn.nextLine().trim(); // .trim() handles "    " before a number which isn't caught by NumberFormatException
            validNumber = isNumber(input);
            if(!validNumber)
                System.out.println(this.informUserError);
        } while(!validNumber);
        return input;
    }

    // Checks if input is a positive number above 0.00
    public boolean isNumber(String number)   {
        if(number == null) {
            return false;
        }
        // skipping else since return would stop the method.
        try {
            double stringToDouble = Double.parseDouble(number);
            if(stringToDouble < 0)
                return false;
        }
        catch(NumberFormatException error)  {
            return false;
        }
        return true;
    }

    // Gets userInput for menu and checks if it's valid.
    public int getUserOption(int maxValue, String input, String error)  {
        int userOption;
        setStrings(input, error);
        do {
            userOption = readInt(input, error);
            if(userOption > maxValue)
                System.out.println(error);
        } while(userOption < 0 || userOption > maxValue);
        return userOption;
    }

    // Sets inputMessage and outputError
    public void setStrings(String input, String error)  {
        this.askUserFor = input;
        this.informUserError = error;
    }

    public void closeScanner()   {
        readIn.close();
    }


    /*
     **********************
     *       METHODS      *
     *    Burak / John    *
     **********************
     */

    public void setInputMsg(String input)  {
        this.askUserFor = input;
               }

    public String validID(){
        items = itemData.copyItems();
        boolean duplicate = true;
        String userIn;
        String valid="";
        do{
            userIn=readString("Please give ID of the item: ","You have given a wrong ID");

            for(int i =0;i<items.size();i++){
                if(userIn.equals(items.get(i).getId())){
                    valid=userIn;
                    duplicate=false;
                }
            }
            if(duplicate){
                System.out.println("Item " + userIn + " was not registered yet.");
            }
            if(duplicate){
                for(int i =0;i<items.size();i++){
                    if(!userIn.equals(items.get(i).getId())){
                        duplicate=true;
                    }
                }
            }

        }while(duplicate);
        return valid;
    }
}
