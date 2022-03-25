package facade;

import java.util.List;
import java.util.Map;

import Employees.EmployeeOptions;
import ItemOptions.ItemOptions;
import ReviewOptions.ReviewOptions;
import TransacHistory.TransacHistory;

public class Facade {
    TransacHistory trans;
    ItemOptions shop;
    ReviewOptions reviews;
    final boolean test;
    EmployeeOptions employee;

    // This class only has the skeleton of the methods used by the test.
    // You must fill in this class with your own code. You can (and should) create more classes
    // that implement the functionalities listed in the Facade and in the Test Cases.

    public Facade(){
        test = true;
        trans = new TransacHistory();
        shop = new ItemOptions(trans, test);
        trans.itemsData = shop;
        employee=new EmployeeOptions();
        reviews = new ReviewOptions(shop);
    }

    public String createItem(String itemID, String itemName, double unitPrice){
        return shop.addItem(itemID, itemName, unitPrice);
    }

    public String printItem(String itemID) {
        return shop.printItem(itemID);
    }

    public String removeItem(String itemID) {
        return shop.delItem(itemID);
    }

    public boolean containsItem(String itemID) {
        return false;
    }

    public double buyItem(String itemID, int amount) {
        return shop.buyItem(itemID, amount);
    }

    public String reviewItem(String itemID, String reviewComment, int reviewGrade) {return reviews.reviewItem(itemID, reviewComment, reviewGrade);}

    public String reviewItem(String itemID, int reviewGrade) {
        return reviews.reviewItem(itemID, reviewGrade);
    }

    public String getItemCommentsPrinted(String itemID) {
        return reviews.getItemCommentsPrinted(itemID);
    }

    public List<String> getItemComments(String itemID) {
        return reviews.getItemComments(itemID);
    }

    public double getItemMeanGrade(String itemID) {
        return shop.findItemObject(itemID).getItemMeanGrade();
    }

    public int getNumberOfReviews(String itemID) {
        return reviews.getNumberOfReviews(itemID);
    }

    public String getPrintedItemReview(String itemID, int reviewNumber) {return reviews.printSpecificReview(itemID, reviewNumber);}

    public String getPrintedReviews(String itemID) {
        return reviews.getPrintedItemReviews(itemID);
    }

    public String printMostReviewedItems() {
        return reviews.printMostRevs();
    }

    public List<String> getMostReviewedItems() {return reviews.getMostReviewedItems();}

    public List<String> getLeastReviewedItems() {
        return reviews.getLeastReviewedItems();
    }

    public String printLeastReviewedItems() {
        return reviews.printLeastRevs();
    }

    public double getTotalProfit() {
        return trans.allHistoryProfit() ;
        //trans.allHistoryProfit();
    }


    public String printItemTransactions(String itemID) {
        return trans.printAllItemTrans(itemID);       //trans.printHistory(itemID);
    }

    public int getTotalUnitsSold() {
        return trans.allHistoryUnitsSold(); //return trans.allHistoryUnitsSold();
    }

    public int getTotalTransactions() {
        return trans.allHistoryTrans();//trans.allHistoryRegTrans();
    }

    public double getProfit(String itemID) {
        return trans.itemHistoryProfit(itemID);//trans.itemHistProfits(itemID);
    }

    public int getUnitsSolds(String itemID) {
        return trans.itemHistoryUnitsSold(itemID);// trans.itemHistUnitsSold(itemID);
    }

    public String printAllTransactions() {
        return trans.printAllTrans();// trans.printAllTransac();
    }

    public String printWorseReviewedItems() {return reviews.printWorstReviewedItems();}

    public String printBestReviewedItems() {
        return reviews.printBestReviewedItems();
    }

    public List<String> getWorseReviewedItems() {
        return reviews.getWorstReviewedItems();
    }

    public List<String> getBestReviewedItems() {
        return reviews.getBestReviewedItems();
    }

    public String printAllReviews() {
        return reviews.getPrintedReviews();
    }

    public String updateItemName(String itemID, String newName) {
        return shop.newItemName(itemID, newName);
    }

    public String updateItemPrice(String itemID, double newPrice) {
        return shop.newItemPrice(itemID, newPrice);
    }

    public String printAllItems() {
        return shop.printAllItems();
    }

    public String printMostProfitableItems() {
        return trans.mostProfit();//trans.mostProfit();
    }


    public String createEmployee(String employeeID, String employeeName, double grossSalary) throws Exception {
        return employee.createEmployee(employeeID,employeeName,grossSalary);
    }

    public String printEmployee(String employeeID) throws Exception {
        return employee.printEmployee(employeeID);
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, String degree) throws Exception {
        return employee.createEmployee(employeeID,employeeName,grossSalary,degree);
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, int gpa) throws Exception {
        return employee.createEmployee(employeeID,employeeName,grossSalary,gpa);
    }

    public double getNetSalary(String employeeID) throws Exception {
        return employee.getNetSalary(employeeID);
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, String degree, String dept) throws Exception {
        return employee.createEmployee(employeeID,employeeName,grossSalary,degree,dept);
    }

    public String removeEmployee(String empID) throws Exception {
        return employee.removeEmployee(empID);
    }

    public String printAllEmployees() throws Exception {
        return employee.printAllEmployees();
    }

    public double getTotalNetSalary() throws Exception {
        return employee.getTotalNetSalary();
    }

    public String printSortedEmployees() throws Exception {
        return employee.printSortedEmployees();
    }

    public String updateEmployeeName(String empID, String newName) throws Exception {
        return employee.updateEmployeeName(empID, newName);
    }

    public String updateInternGPA(String empID, int newGPA) throws Exception {
        return employee.updateInternGPA(empID, newGPA);
    }

    public String updateManagerDegree(String empID, String newDegree) throws Exception {
        return employee.updateManagerDegree(empID, newDegree);
    }

    public String updateDirectorDept(String empID, String newDepartment) throws Exception {
        return employee.updateDirectorDept(empID, newDepartment);
    }

    public String updateGrossSalary(String empID, double newSalary) throws Exception {
        return employee.updateGrossSalary(empID, newSalary);
    }

    public Map<String, Integer> mapEachDegree() throws Exception {
        return employee.mapEachDegree();
    }

    public String promoteToManager(String empID, String degree) throws Exception {
        return employee.promoteToManager(empID, degree);

    }

    public String promoteToDirector(String empID, String degree, String department) throws Exception {
        return employee.promoteToDirector(empID, degree, department);
    }

    public String promoteToIntern(String empID, int gpa) throws Exception {
        return employee.promoteToIntern(empID, gpa);
    }
}
