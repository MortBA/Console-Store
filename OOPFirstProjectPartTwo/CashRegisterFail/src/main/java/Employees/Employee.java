package Employees;
import CashRegister.SystemOutput;

public class Employee {
    private final String employeeID;
    private String employeeName;
    private double grossSalary;
    private double netSalary;
    private double taxPercentage=0.1;
    private double rawSalary;
    SystemOutput sout;
    private boolean alreadyExcecuted = false;

    Employee(String employeeID, String employeeName, double grossSalary) throws Exception {
        if (employeeID.isBlank()) {
            throw new Exception("ID cannot be blank.");
        }
        if (employeeName.isBlank()){
            throw new Exception("Name cannot be blank.");
        }
        if (grossSalary <= 0){
            throw new Exception("Salary must be greater than zero.");
        }
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.grossSalary = (double)((long)(grossSalary * 100))/100;
        System.out.println("New reg employee Damon : " + grossSalary);
        this.rawSalary = this.grossSalary;
        sout=new SystemOutput();
    }



    protected String getEmployeeID() {
        return employeeID;
    }

    protected String getEmployeeName() {
        return employeeName;
    }

    protected void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    protected void setTaxPercentage(double taxPercentage){this.taxPercentage = taxPercentage;}

    protected double getTaxPercentage(){return taxPercentage;}

    protected double getGrossSalary() {
        return grossSalary;
    }

    protected double getRawSalary() { return rawSalary; }

    protected void setGrossSalary(double grossSalary) { this.grossSalary = grossSalary; }

    protected void setNetSalary(double netSalary){this.netSalary=netSalary;}

    protected double getNetSalary(){return netSalary;}

    protected boolean getAlreadyExcected(){return alreadyExcecuted;}

    protected void setAlreadyExcecuted(){alreadyExcecuted=true;}

    protected double calculateGrossSalary(){return grossSalary;}

    protected double calculateSalary(){return netSalary = grossSalary - (grossSalary * taxPercentage);}

    private double roundDecimal(double value)  {return ((double)((long)(value * 100)))/100;}

    public String toString(){
        String soutGrossSalary = sout.decimalFix(roundDecimal(grossSalary));
        if(grossSalary==0.0){
            soutGrossSalary="0.00";
        }
        return (employeeName+"'s gross salary is "+soutGrossSalary+" SEK per month.");
    }
}
