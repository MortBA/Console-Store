package TransacHistory;

import CashRegister.SystemOutput;

public class Transaction {
    final private String ID;
    final private double totalPrice;
    final private int quantity;
    SystemOutput printMenu;


    public Transaction(String ID, int quantity, double totalPrice){
        this.ID = ID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        printMenu = new SystemOutput();


    }

    @Override
    public String toString() {return ID + ": "+quantity+ " item(s). "+printMenu.decimalFix(totalPrice)+ " SEK";}

    public String getID(){return this.ID;}

    public int getQuantity(){return this.quantity;}

    public double getTotalPrice(){return this.totalPrice;}




}

