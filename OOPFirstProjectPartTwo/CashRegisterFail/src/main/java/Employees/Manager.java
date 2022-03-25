package Employees;

public class Manager extends Employee{
    private String degree;
    private int bonus=0;
    Manager(String employeeID, String employeeName, double grossSalary, String degree) throws Exception{
        super(employeeID, employeeName, grossSalary);
        if (!degree.equals("BSc") && !degree.equals("MSc") && !degree.equals("PhD")){
            throw new Exception("Degree must be one of the options: BSc, MSc or PhD.");
        }
        this.degree= degree;
    }

    public String toString(){return (degree+" "+super.toString());}

    //CALCULATES GROSS SALARY ACCORDING TO DEGREE
    public double calculateGrossSalary(){
        if(!super.getAlreadyExcected()){
            if(degree.equals("BSc")){
                setGrossSalary(getGrossSalary()*1.1+bonus);
            }else if(degree.equals("MSc")){
                setGrossSalary(getGrossSalary()*1.2+bonus);
            }else{
                setGrossSalary(getGrossSalary()*1.35+bonus);
            }
            setAlreadyExcecuted();
            return super.getGrossSalary();
        }else{

        }
        return super.getGrossSalary();
    }

    public double calculateSalary(){
        calculateGrossSalary();
        return super.calculateSalary();
    }

    public String getDegree(){return degree;}

    public void setDegree(String degree){this.degree=degree;}

    public void setBonus(int bonus){this.bonus=bonus;}


}
