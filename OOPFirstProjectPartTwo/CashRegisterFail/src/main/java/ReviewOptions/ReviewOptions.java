package ReviewOptions;

import CashRegister.UserInput;
import CashRegister.SystemOutput;
import ItemOptions.ItemOptions;
import ItemOptions.Item;

import java.util.ArrayList;
import java.util.List;


public class ReviewOptions {
    String ln = System.lineSeparator();
    SystemOutput systemOut;
    UserInput takeIn;
    ItemOptions itemRegistry;
    ArrayList<Item> items;


    public ReviewOptions(ItemOptions itemID)  {
        systemOut = new SystemOutput();
        takeIn = new UserInput();
        itemRegistry = itemID;
        items = itemRegistry.copyItems();
    }

    public void runReviews(){
        String ID;
        int choice;
        do{
            do{
                systemOut.reviewMenu();
                choice=this.takeIn.getUserOption(10,"Enter an option: ","Wrong option, try again a number between 0-10!");
            }while(choice>10||choice<(0));

            switch (choice){
                case 0: //Return to main menu
                    break;
                case 1: //Create review
                    ID = takeIn.readID("Please enter an item ID: ", "Please input a valid ID.");
                    int reviewGrade = takeIn.readInt("Enter an item grade:", "Grade values must be between 1 and 5");
                    String reviewComment = takeIn.readComment("Enter an item comment (Optional): ");
                    if (!reviewComment.isEmpty()) {
                        System.out.println(reviewItem(ID, reviewComment, reviewGrade));
                        } else {
                        System.out.println(reviewItem(ID, reviewGrade));
                    }
                    break;
                case 2: //Print specific review
                    ID = takeIn.readID("Please enter an item ID: ", "Error. Please enter a valid item ID.");
                    int reviewNumber = takeIn.readInt("Enter review number: ","Please enter an integer.");
                    System.out.println(printSpecificReview(ID, reviewNumber));
                    break;
                case 3: //Print all reviews of an item
                    ID = takeIn.readID("Please enter an item ID: ", "Error. Please enter a valid item ID.");
                    System.out.println(getPrintedItemReviews(ID));
                    break;
                case 4: //Print mean grade of an item
                    ID = takeIn.readID("Please enter an item ID: ", "Error. Please enter a valid item ID.");
                    System.out.println(printMeanGradeItem(ID));
                    break;
                case 5: //Print all comments of an item
                    ID = takeIn.readID("Please enter an item ID: ", "Error. Please enter a valid item ID.");
                    System.out.println(getItemCommentsPrinted(ID));
                    break;
                case 6: //Print all registered reviews
                    System.out.println(getPrintedReviews());
                    break;
                case 7: //Print item(s) w the most reviews
                    System.out.println(printMostRevs());
                    break;
                case 8: //Print item(s) w the least reviews
                    System.out.println(printLeastRevs());
                    break;
                case 9: //Print item(s) w best mean grade
                    System.out.println(printBestReviewedItems());
                    break;
                case 10: //Print item(s) w worst mean grade
                    System.out.println(printWorstReviewedItems());
                    break;
            }
        } while(choice!=0);
    }




    // 1. CREATES A REVIEW WITH A COMMENT AND ADDS IT TO THE REVIEWS LIST
    public String reviewItem(String ID, String reviewComment, int reviewGrade) {
        if (itemRegistry.existenceChecker(ID)) {
            if (reviewGrade < 1 || reviewGrade > 5) {
                return "Grade values must be between 1 and 5.";
            }
            Item item = itemRegistry.findItemObject(ID);
            item.reviewsList.add(new Reviews(reviewGrade, reviewComment));
            return "Your item review was registered successfully.";
        } else {
            return "Item " + ID + " was not registered yet.";
        }
    }
    // CREATES A REVIEW WITHOUT A COMMENT AND ADDS IT TO THE REVIEWS LIST
    public String reviewItem(String ID, int reviewGrade) {
        if (itemRegistry.existenceChecker(ID)) {
            if (reviewGrade < 1 || reviewGrade > 5) {
                return "Grade values must be between 1 and 5.";
            }
            Item item = itemRegistry.findItemObject(ID);
            item.reviewsList.add(new Reviews(reviewGrade, ""));
            return "Your item review was registered successfully.";
        } else {
            return "Item " + ID + " was not registered yet.";
        }
    }



    // 2. PRINTS A SPECIFIC REVIEW FOR AN ITEM BY TAKING ITEM ID AND THE NUMBER OF THE REVIEW AS INPUT
    public String printSpecificReview(String ID, int reviewNumber) {
        if (itemRegistry.existenceChecker(ID)) {
            Item item = itemRegistry.findItemObject(ID);
            if (item.reviewsList.isEmpty()) {
                return "Item " + item.getName() + " has not been reviewed yet.";
            } else if (reviewNumber < 1 || reviewNumber > item.reviewsList.size()) {
                return "Invalid review number. Choose between 1 and " + getNumberOfReviews(ID) + ".";
            } else {
                reviewNumber = reviewNumber - 1;
                return "Grade: " + item.reviewsList.get(reviewNumber).getReviewGrade() + "." + item.reviewsList.get(reviewNumber).getReviewComment();
            }
        } else {
            return "Item " + ID + " was not registered yet.";
        }

    }

    // 3. PRINTS ALL REVIEWS FOR A SINGLE ITEM
    public String getPrintedItemReviews(String ID) {
        if (itemRegistry.existenceChecker(ID)) {
        Item item = itemRegistry.findItemObject(ID);
        String printReviews = "Review(s) for " + item.toString() + ln;
        String noReviewsError = "The item " + item.getName() + " has not been reviewed yet.";

        if (item.getReviewList().isEmpty()) {
            return printReviews + noReviewsError;
        }

        for (Reviews reviews : item.getReviewList()) {
            printReviews += "Grade: " + reviews.toString() + ln;
        }
        return printReviews;
        } else {
            return "Item " + ID + " was not registered yet.";
        }
    }

    // 4. PRINTS THE MEAN GRADE OF AN ITEM
    public double printMeanGradeItem(String ID){
        if (itemRegistry.existenceChecker(ID)) {
            Item item = itemRegistry.findItemObject(ID);
            return item.getItemMeanGrade();
        } else {
            System.out.println("Item " + ID + " was not registered yet.");
            return 0;
        }
    }
    // 5. GETS ALL COMMENTS FOR AN ITEM AND PRINTS THEM.
    public String getItemCommentsPrinted(String ID) {
          StringBuilder result = new StringBuilder();
         for (String comments : getItemComments(ID)) {
           result.append(comments).append(ln);
         }
         return result.toString();
    }

    // 6. PRINTS ALL REGISTERED REVIEWS FOR ALL REGISTERED ITEMS
    public String getPrintedReviews() {
        items = itemRegistry.copyItems();
        List<List<Reviews>> reviews = new ArrayList<>();
        String allReviews = "All registered reviews:" +ln + "------------------------------------" +ln;
        String reviewSeparation = "------------------------------------" + ln;
        if (items.isEmpty()){
            return "No items registered yet.";
        } else {
            for (int j = 0; j < items.size(); j++) {
                if (!items.get(j).getReviewList().isEmpty()) {
                    reviews.add(items.get(j).getReviewList());
                }
            }
            if (reviews.isEmpty()) {
                return "No items were reviewed yet.";
            }
            for (Item item : items) {
                if (item.reviewsList.isEmpty()) {
                    allReviews += "";
                } else {
                    allReviews += "Review(s) for " + item.toString() + ln;

                    for (int i = 0; i < item.getReviewList().size(); i++) {
                        allReviews += "Grade: " + item.reviewsList.get(i).getReviewGrade() + "." + item.reviewsList.get(i).getReviewComment() + ln;
                    }
                    allReviews += reviewSeparation;
                }
            }
        }
            return allReviews;
        }

    // 7. PRINTS ITEMS WITH THE MOST REVIEWS
    public String printMostRevs() {
        items = itemRegistry.copyItems();
        int numberOfReviews = 0;
        String mostReviews = "Most reviews: " + numberOfReviews + "review(s) each." + ln;

        if (items.isEmpty()) {
            return "No items registered yet.";
        }
        for (Item item : items) {
            if (item.reviewsList.isEmpty()) {
                mostReviews = "No items were reviewed yet.";
            } else {
                mostReviews = "Most reviews: " + numberOfReviews + " review(s) each." + ln;
                for (int i = 0; i < getMostReviewedItems().size(); i++) {
                    numberOfReviews = getNumberOfReviews(getMostReviewedItems().get(i));
                    mostReviews += itemRegistry.findItemObject(getMostReviewedItems().get(i)) +ln;
                }
            }
        }
        return mostReviews;
    }

    // 8. PRINTS ITEMS WITH THE LEAST REVIEWS
    public String printLeastRevs(){
        items = itemRegistry.copyItems();
        int numberOfReviews = 0;
        String leastReviews = "Least reviews: " + numberOfReviews + "review(s) each." + ln;

        if (items.isEmpty()) {
            return "No items registered yet.";
        }
        for (Item item : items) {
            if (item.reviewsList.isEmpty()) {
                leastReviews = "No items were reviewed yet.";
            } else {
                leastReviews = "Least reviews: " + numberOfReviews + " review(s) each." + ln;
                for (int i = 0; i < getLeastReviewedItems().size(); i++) {
                    numberOfReviews = getNumberOfReviews(getLeastReviewedItems().get(i));
                    leastReviews += itemRegistry.findItemObject(getLeastReviewedItems().get(i)) +ln;
                }
            }
        }
        return leastReviews;
    }

    // 9. PRINTS ITEMS WITH THE BEST REVIEWS
    public String printBestReviewedItems(){
        items = itemRegistry.copyItems();
        double meanGrade = 0.0;
        String bestReviews = "Items with best mean reviews:" +ln + "Grade: " + meanGrade;


        if (items.isEmpty()) {
            return "No items registered yet.";
        }
        for (Item item : items) {
            if (item.reviewsList.isEmpty()) {
                bestReviews = "No items were reviewed yet.";
            } else {
                bestReviews = "Items with best mean reviews:" +ln + "Grade: " + meanGrade + ln;
                for (int i = 0; i < getBestReviewedItems().size(); i++) {
                    meanGrade = itemRegistry.findItemObject(getBestReviewedItems().get(i)).getItemMeanGrade();
                    bestReviews += itemRegistry.findItemObject(getBestReviewedItems().get(i)) +ln;
                }
            }
        }
        return bestReviews;
    }

    // 10. PRINTS ITEMS WITH THE WORST REVIEWS
    public String printWorstReviewedItems(){
        items = itemRegistry.copyItems();
        double meanGrade = 0.0;
        String worstReviews = "Items with worst mean reviews:" +ln + "Grade: " + meanGrade;


        if (items.isEmpty()) {
            return "No items registered yet.";
        }
        for (Item item : items) {
            if (item.reviewsList.isEmpty()) {
                worstReviews = "No items were reviewed yet.";
            } else {
                worstReviews = "Items with worst mean reviews:" +ln + "Grade: " + meanGrade + ln;
                for (int i = 0; i < getWorstReviewedItems().size(); i++) {
                    meanGrade = itemRegistry.findItemObject(getWorstReviewedItems().get(i)).getItemMeanGrade();
                    worstReviews += itemRegistry.findItemObject(getWorstReviewedItems().get(i)) +ln;
                }
            }
        }
        return worstReviews;
    }

//Getter methods and ID methods

    // GETS ALL COMMENTS FOR A PARTICULAR ITEM AND ADDS THEM TO AN ARRAYLIST.
    public List<String> getItemComments(String ID) {
        List<String> comments = new ArrayList<>();
        if (itemRegistry.findItemObject(ID) == null) {
            return comments;
        } else {
            for (Reviews reviews : itemRegistry.findItemObject(ID).getReviewList()) {
                if (!reviews.getReviewComment().isEmpty()) {
                    comments.add(reviews.getReviewComment());
                }
            }
        }
        return comments;
    }

    // GETS THE NUMBER OF REVIEWS FOR A PARTICULAR ITEM.
public int getNumberOfReviews(String ID) {
    int numberOfReviews = 0;
    if (itemRegistry.findItemObject(ID) == null) {
        return numberOfReviews;
    }
    numberOfReviews = itemRegistry.findItemObject(ID).getReviewList().size();
    return numberOfReviews;
}

    // GETS ITEMS WITH THE MOST REVIEWS AND ADDS THEIR IDS TO AN ARRAYLIST
public List<String> getMostReviewedItems(){
        items = itemRegistry.copyItems();
        List<String> mostReviewedItems = new ArrayList<>();
        List<List<Reviews>> reviews = new ArrayList<>();
        int numberOfReviews = 0;

    if (items.isEmpty()) {
        return mostReviewedItems;
    }

        for (Item item : items) {
            for (int j = 0; j < items.size(); j++) {
                if (!items.get(j).getReviewList().isEmpty()) {
                    reviews.add(items.get(j).getReviewList());
                }
            }
            if (reviews.isEmpty()) {
                return mostReviewedItems;
            }
            if (item.reviewsList.size() > numberOfReviews) {
                mostReviewedItems.add(item.getId());
                numberOfReviews = item.reviewsList.size();
            } else if (item.reviewsList.size() == numberOfReviews) {
                mostReviewedItems.add(item.getId());
            }
        }
        return mostReviewedItems;
}
    // GETS ITEMS WITH THE LEAST REVIEWS AND ADDS THEIR IDS TO AN ARRAYLIST
public List<String> getLeastReviewedItems() {
    items = itemRegistry.copyItems();
    List<Item> reviewedItems = new ArrayList<>();
    List<String> leastReviewedItems = new ArrayList<>();
    int minReviews;
    List<List<Reviews>> reviews = new ArrayList<>();

    if (items.isEmpty()) {
        return leastReviewedItems;
    }
    for (Item item : items) {
        for (int j = 0; j < items.size(); j++) {
            if (!items.get(j).getReviewList().isEmpty()) {
                reviews.add(items.get(j).getReviewList());
            }
        }
        if (reviews.isEmpty()) {
            return leastReviewedItems;
        }
        else if (item.getReviewList().size() != 0) {
            reviewedItems.add(item);
        }
    }
    minReviews = reviewedItems.get(0).getReviewList().size();

    for (Item item : reviewedItems) {
        if (minReviews > item.getReviewList().size()) {
            minReviews = item.getReviewList().size();
            leastReviewedItems.clear();
            leastReviewedItems.add(item.getId());
        } else if (minReviews == item.getReviewList().size()) {
            leastReviewedItems.add(item.getId());
        }
    }
    return leastReviewedItems;
}
    // GETS ITEMS WITH THE BEST REVIEWS AND ADDS THEIR IDS TO AN ARRAYLIST
    public List<String> getBestReviewedItems() {
        items = itemRegistry.copyItems();
        List<String> bestReviewedItems = new ArrayList<>();
        List<List<Reviews>> reviews = new ArrayList<>();
        double minGrade = 0.0;

        if (items.isEmpty()) {
            return bestReviewedItems;
        }
        for (Item item : items) {
            for (int j = 0; j < items.size(); j++) {
                if (!items.get(j).getReviewList().isEmpty()) {
                    reviews.add(items.get(j).getReviewList());
                }
            }
            if (reviews.isEmpty()) {
                return bestReviewedItems;
            }
                if (item.getItemMeanGrade() > minGrade) {
                    bestReviewedItems.add(item.getId());
                    minGrade = item.getItemMeanGrade();
                } else if (item.getItemMeanGrade() == minGrade) {
                    bestReviewedItems.add(item.getId());
                }
            }
        return bestReviewedItems;
    }
    // GETS ITEMS WITH THE WORST REVIEWS AND ADDS THEIR IDS TO AN ARRAYLIST
    public List<String> getWorstReviewedItems() {
        items = itemRegistry.copyItems();
        List<Item> reviewedItems = new ArrayList<>();
        List<String> worstReviewedItems = new ArrayList<>();
        double minGrade;
        List<List<Reviews>> reviews = new ArrayList<>();

        if (items.isEmpty()) {
            return worstReviewedItems;
        }

        for (Item item : items) {
            for (int j = 0; j < items.size(); j++) {
                if (!items.get(j).getReviewList().isEmpty()) {
                    reviews.add(items.get(j).getReviewList());
                }
            }

            if (reviews.isEmpty()) {
                return worstReviewedItems;
            } else if (item.getItemMeanGrade() != 0.0) {
                reviewedItems.add(item);
            }
        }

            minGrade = reviewedItems.get(0).getItemMeanGrade();


        for (Item item : reviewedItems) {
            if (minGrade > item.getItemMeanGrade()) {
                minGrade = item.getItemMeanGrade();
                worstReviewedItems.clear();
                worstReviewedItems.add(item.getId());
            } else if (minGrade == item.getItemMeanGrade()) {
                worstReviewedItems.add(item.getId());
            }
        }
        return worstReviewedItems;
    }
}


