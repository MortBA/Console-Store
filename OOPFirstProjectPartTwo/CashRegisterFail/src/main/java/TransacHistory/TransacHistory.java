package TransacHistory;
import CashRegister.UserInput;
import CashRegister.SystemOutput;
import ItemOptions.ItemOptions;
import ItemOptions.Item;

import java.util.ArrayList;
import java.util.List;


public class TransacHistory {
    SystemOutput printMenu;
    UserInput readIn;
    List<Transaction> historyList;
    public ItemOptions itemsData;
    String ln= System.lineSeparator();
    ArrayList<Item> items;
    public static boolean hasRegistered = false;


    public TransacHistory(){
        printMenu = new SystemOutput();
            readIn= new UserInput();
        historyList = new ArrayList<>();
    }

    public void runHistory(){
        String ID;
        int choice;
        do{
            do{
                printMenu.transacMenu();
                choice=this.readIn.getUserOption(8,"Enter an option! ","Wrong option, try again a number between 0-8!"+ln);
            }while(choice>8||choice<(0));

            switch (choice){
                case 0:

                    break;
                case 1:
                    System.out.println(allHistoryProfit());
                    break;
                case 2:
                    System.out.println(allHistoryUnitsSold());
                    break;
                case 3:
                    System.out.println(allHistoryTrans());
                    break;

                case 4:
                    System.out.println(printAllTrans());
                    break;
                case 5:
                    ID= readIn.readID("Please give valid ID", "You have given an invalid ID");
                    System.out.println(itemHistoryProfit(ID));
                    break;
                case 6:
                    ID= readIn.readID("Please give valid ID", "You have given an invalid ID");
                    System.out.println(itemHistoryUnitsSold(ID));
                    break;

                case 7:
                    ID= readIn.readID("Please give valid ID", "You have given an invalid ID");
                    System.out.println(printAllItemTrans(ID));
                    break;
                case 8:
                    System.out.println(mostProfit());
                    break;
            }
        } while(choice!=0);
    }


    public void purchaseSave(String id, int quantity, double totalPrice){
       historyList.add(new Transaction(id, quantity, totalPrice));
    }

    //GIVES TOTAL PROFIT EVER    1
    public double allHistoryProfit(){return printMenu.roundDecimal(allHistoryContent()[0]);}

    //GIVES NUMBER OF UNITS SOLD EVER   2
    public int allHistoryUnitsSold(){
        return (int) allHistoryContent()[1];
    }

    //GIVES NUMBER OF TRANSACTIONS EVER  3
    public int allHistoryTrans(){
        return historyList.size();
    }

    //HANDLES ALL CALCULATION FOR ALL ITEMS
    public double[] allHistoryContent(){
        double[] allHistory = new double[3];
        double totPrice = 0;
        double totTrans = 0;
        double unitsSold = 0;

        for(int i =0; i<historyList.size();i++){
            totPrice += historyList.get(i).getTotalPrice();
            unitsSold += historyList.get(i).getQuantity();
            totTrans++;
        }
        allHistory[0] = totPrice;
        allHistory[1] = unitsSold;
        allHistory[2] = totTrans;

        return allHistory;
    }

    //GIVES TOTAL PROFIT FROM A SINGLE ITEM  5
    public double itemHistoryProfit(String id){return printMenu.roundDecimal(itemHistoryContent(id)[0]);}

    //GIVES TOTAL UNITS SOLD OF A SINGLE ITEM  6
    public int itemHistoryUnitsSold(String id){return (int)itemHistoryContent(id)[1];}

    //GIVES TOTAL TRANSACTIONS A ITEM MADE IN TOTAL
    public int itemHistoryTotalTrans(String id){return (int) itemHistoryContent(id)[2];}

    //OPERATES AND CALCULATES A ITEM`S HISTORY
    public double[] itemHistoryContent(String id){

        double[] itemHistory = new double[3];
        double totPrice = 0;
        double totTrans= 0;
        double unitsSold=0;

        for(int i = 0; i<historyList.size(); i++){
            if(id.equals(historyList.get(i).getID())){
                totPrice+=historyList.get(i).getTotalPrice();
                totTrans++;
                unitsSold+=historyList.get(i).getQuantity();
            }
        }
        itemHistory[0]=totPrice;
        itemHistory[1]=unitsSold;
        itemHistory[2]=totTrans;
        return itemHistory;
    }

    //PRINTS HISTORY OF ONE SINGLE ITEM   7
    public String printAllItemTrans(String id) {
        String result = "";
        boolean itemExists = itemsData.existenceChecker(id);
        if(itemExists){//checks if item id exists
            result = "Transactions for item: "+ id + ": "+ getName(id)+". "+printMenu.decimalFix(getPrice(id))+" SEK"+ln; //Preps first line DONT

            if(itemHistoryTotalTrans(id) > 0){//checks if any transaction for said item
                for(int i = 0; i<historyList.size(); i++){//loops entire history list
                    if(historyList.get(i).getID().equals(id)){
                        result += historyList.get(i).toString() + ln;//prints entire hstory list
                    }
                }
            }else {
                result+="No transactions have been registered for item "+id+" yet."; //no tranaction for item print
            }
        }else{// item id dosent exist so print so
            result+="Item "+id+" was not registered yet.";
        }

        return result;
    }



    //PRINTS ALL THE PURCHASES EVER MADE   4
    public String printAllTrans(){
        String allHistoryProfit=""+printMenu.decimalFix(allHistoryProfit());
        if(historyList.size()==0){
            allHistoryProfit="0.00";
        }
        String result = "All purchases made: "+ln
                +"Total profit: "+allHistoryProfit+" SEK"+ ln
                +"Total items sold: "+allHistoryUnitsSold()+" units"+ ln
                +"Total purchases made: "+allHistoryTrans()+" transactions"+ ln
                +"------------------------------------"+ln;

        if(allHistoryTrans()>0){
            for(int i = 0; i<historyList.size();i++){
                result+=historyList.get(i).toString()+ln;
            }
        }
        result+="------------------------------------"+ln;
        return result;
    }



    // MOST PROFIT ITEM(S)
    public String mostProfit(){
        String result = "";
        double mostProfit= 0;
        String mostProfitID = "";

        if(hasRegistered && historyList.size()>0){
            for(int i = 0; i<historyList.size();i++){
                if(itemHistoryProfit(historyList.get(i).getID())>mostProfit){
                    mostProfitID = historyList.get(i).getID();
                    mostProfit=itemHistoryProfit(historyList.get(i).getID());
                }
            }
            result+="Most profitable items: "+ln
                    +"Total profit: "+ printMenu.decimalFix(mostProfit)+ " SEK"+ln;

            result+=itemsData.copyItems().get(getIndex(mostProfitID)).getId()+": "+getName(mostProfitID)+". "+printMenu.decimalFix(getPrice(mostProfitID))+" SEK"+ln;


            for(int i = 0; i<historyList.size();i++){
                if(itemHistoryProfit(historyList.get(i).getID())==mostProfit&&historyList.get(i).getID()!=mostProfitID){
                    String idHist = historyList.get(i).getID();
                    result+=itemsData.copyItems().get(getIndex(idHist)).getId()+": "+getName(idHist)+". "+getPrice(idHist)+" SEK"+ln;
                }
            }

        }else if(!hasRegistered){
            result+="No items registered yet";
        }else{
            result+="No items were bought yet";
        }
        return result;
    }

    // CHECKS IF ATLEAST ONE ITEM HAS EVER BEEN CREATED
    public void hasRegistered() {hasRegistered=true;}


    //GETS INDEX OF ITEM IN ITEMSCOPYLIST
    public int getIndex(String id){
        items = itemsData.copyItems();
        int index = 0;
        for(int i = 0; i<items.size();i++){
            if(id.equals(items.get(i).getId())){
                index=i;
            }
        }
        return index;
    }
    //FETCHES NAME OF ITEM DEPENDING ON ID
    public String getName(String id){
        items = itemsData.copyItems();
        String name ="";
        int index = getIndex(id);
        name =items.get(index).getName();
        return name;
    }

    //GETS PRICE OF A ITEM
    public double getPrice(String id){
        items = itemsData.copyItems();
        double price = 0;
        int index = getIndex(id);
        price = items.get(index).getPrice();
        return price;
    }

}
