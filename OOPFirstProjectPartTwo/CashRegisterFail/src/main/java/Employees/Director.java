package Employees;

public class Director extends Manager{
    private String dept;

    Director(String employeeID, String employeeName, double grossSalary, String degree, String dept) throws Exception {
        super(employeeID, employeeName, grossSalary, degree);
        if (!dept.equals("Business") && !dept.equals("Human Resources") && !dept.equals("Technical")){
            throw new Exception("Department must be one of the options: Business, Human Resources or Technical.");
        }
        this.dept = dept; //Example: BSc, MSc, pHD
    }

    public String toString(){return (super.toString()+" Dept: "+dept);}

    //CALCULATES GROSS SALARY DEPENDING ON DEGREE AND RECEIVES BONUS
    public double calculateGrossSalary(){
        if(!super.getAlreadyExcected()){
            setBonus(5000);
            return super.calculateGrossSalary();
        }else {
            return super.getGrossSalary();
        }
    }

    //CALCULATES TAX AND NET SALARY ACCORDING TO GROSS_SALARY
    public double calculateSalary() {

        if(getGrossSalary()<30000){
            setTaxPercentage(0.1);
            return super.calculateSalary();
        }else if(30000<=getGrossSalary()&&getGrossSalary()<50000){
            setTaxPercentage(0.2);
            return super.calculateSalary();
        }else{
            setGrossSalary(getGrossSalary()-30000);
            setTaxPercentage(0.4);
            setNetSalary(super.calculateSalary()+24000);
            return getNetSalary();
        }

    }

    public String getDept(){return dept;}

    public void setDept(String dept){this.dept =dept;}

}
