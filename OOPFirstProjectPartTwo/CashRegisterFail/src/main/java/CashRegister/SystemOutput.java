package CashRegister;

import java.text.DecimalFormat;

public class SystemOutput {
    String ln = System.lineSeparator();
    // Global
    DecimalFormat decimals = new DecimalFormat("#.00");

    public void mainMenu()  {

        System.out.println
                ("Main Menu: Please choose among the options below."+ ln+
                 ln+
                "0. Close system" + ln+
                "1. Open Item options." + ln+
                "2. Open Review options" + ln+
                "3. Open Transaction history"+ln+
                "4. Open Employee options."+ln

                +ln);
    }

    public void transacMenu(){
        System.out.println
                (ln+    "Item options menu:" + ln+
                        ln+
                "0. Return to Main Menu."+ ln+
                "1. Print total profit from all item purchases." + ln+
                "2. Print total units sold from all item purchases."+ ln+
                "3. Print all total number of transactions made."+ ln+
                "4. Print all transactions made."+ ln+
                "5. Print the total profit of a specific item."+ ln+
                "6. Print the number of units sold of a specific item."+ ln+
                "7. Print all transactions of a specific item."+ ln+
                "8. Print item with highest profit."+ln
                +ln);
    }

    public void reviewMenu(){
        System.out.println
                (ln+"Review options menu:"+ ln+
                        ln+
                "0. Return to Main Menu."+ ln+
                "1. Create a review for an Item."+ ln+
                "2. Print a specific review of an Item."+ ln+
                "3. Print all reviews of an Item."+ ln+
                "4. Print mean grade of an Item."+ ln+
                "5. Print all comments of an Item."+ ln+
                "6. Print all registered reviews."+ ln+
                "7. Print item(s) with most reviews."+ ln+
                "8. Print item(s) with least reviews."+ ln+
                "9. Print item(s) with best mean review grade."+ ln+
                "10. Print item(s) with worst mean review grade."+ ln
                +ln);

    }

    public void itemMenu(){
        System.out.println
                (ln+"Item Options menu:"+ ln+
                        ln+
                 "0. Return to Main Menu."+ln+
                 "1. Create an Item."+ln+
                 "2. Remove an Item."+ln+
                 "3. Print all registered Items."+ln+
                 "4. Buy an Item."+ln+
                 "5. Update an item’s name."+ln+
                 "6. Update an item’s price."+ln+
                 "7. Print a specific item"+ln
                +ln);
    }

    public void employeeMenu(){
        System.out.println
                (ln+"Item Options menu:"+ ln+
                        ln+
                        "0. Return to Main Menu."+ln+
                        "1. Create an employee (Regular Employee)."+ln+
                        "2. Create an employee (Manager)."+ln+
                        "3. Create an employee (Director)."+ln+
                        "4. Create an employee (Intern)."+ln+
                        "5. Remove an employee."+ln+
                        "6. Print specific employee."+ln+
                        "7. Print all registered employees."+ln+
                        "8. Print the total expense with net salary."+ln+
                        "9. Print all employees sorted by gross salary."+ln
                        +ln);
    }

    // returns a String with two decimals
    public String decimalFix(double value)   {
        return decimals.format(value);
    }

    // Removes any decimals over #.00. Doesn't work for #.00 so use decimalFormat to fix printing
    public double roundDecimal(double value)  {return (double)((long)(value * 100))/100;}


}
