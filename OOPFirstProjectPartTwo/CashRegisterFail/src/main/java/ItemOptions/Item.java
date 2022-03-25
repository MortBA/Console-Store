package ItemOptions;

import ReviewOptions.Reviews;
import java.util.ArrayList;
import java.util.List;

public class Item {
    // This will be the objects that stores the information about all the items in the shop.
     private final String id;
     private String name;
     private double price;
     public List<Reviews> reviewsList;

     Item(String id, String name, double price)   {
        this.id = id;
        this.name = name;
        this.price = price;
        this.reviewsList = new ArrayList<>();
    }

    public String getId() {return id;}

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    protected void setName(String name) {
         this.name = name;
    }

    protected void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return id + ": " + name + ". " + String.format("%.2f", price)  + " SEK";
    }

    //John Webb:

    public List<Reviews> getReviewList() {
        return reviewsList;
    }


    public double getItemMeanGrade() {
         double sumOfGrades = 0;
         for (Reviews reviews : getReviewList()) {
             sumOfGrades += reviews.getReviewGrade();
         }
         double value = sumOfGrades / getReviewList().size();
         double roundedNumber = (int) Math.pow(10, 1);
         int truncatedGrade = (int) (value * roundedNumber);
         return truncatedGrade / roundedNumber;
     }

}
