package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Epic5Regular {

    private Facade facade;

    @BeforeAll
    public static void setupSystem(){
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void setupFacade(){
        facade = new Facade();

        try{
            // 2 employees, 2 directors, 1 manager, 3 interns
            // G: Gross salary; N: Net salary
            facade.createEmployee("Emp1", "Elektra", 35000.50, "MSc", "Business");       // G: 47000.60; N: 37600.48
            facade.createEmployee("Emp2", "Blanca", 45000.00, "PhD", "Human Resources"); // G: 65750.00; N: 45450.00
            facade.createEmployee("Emp3", "Pray Tell", 25000.25, "BSc");                 // G: 27500.27; N: 24750.24
            facade.createEmployee("Emp4", "Lulu", 20000.00, 9);                          // G: 21000.00; N: 21000.00
            facade.createEmployee("Emp5", "Angel", 28500.10, 7);                         // G: 28500.10; N: 28500.10
            facade.createEmployee("Emp6", "Candy", 35000.50, 4);                         // G:     0.00; N:     0.00
            facade.createEmployee("Emp7", "Ricky", 23500.00);                            // G: 23500.00; N: 21150.00
            facade.createEmployee("Emp8", "Damon", 22100.00);                            // G: 22100.00; N: 19890.00
        }catch(Exception e){
            assertFalse(true); // Forces an error in the test. The creation should work without problems.
        }
    }

    @Test
    public void shouldCreateRegularEmployee() throws Exception {
        String employeeID = "IDa";
        String employeeName = "Katherine Johnson";
        double grossSalary = 50000.00;

        String expectedMessage = "Employee IDa was registered successfully.";
        String actualMessage = facade.createEmployee(employeeID, employeeName, grossSalary);
        assertEquals(expectedMessage, actualMessage);

        String expectedPrint = "Katherine Johnson's gross salary is 50000.00 SEK per month.";
        String actualPrint = facade.printEmployee(employeeID);
        assertEquals(expectedPrint, actualPrint);

        // 50000.00 - (0.1 * 50000.00) =  45000.0;
        double netSalary = facade.getNetSalary(employeeID);
        assertEquals(45000.00, netSalary);
    }

    @Test
    public void shouldCreateManagerEmployee() throws Exception {
        String employeeID = "IDb";
        String employeeName = "Mary Keller";
        String degree = "PhD";
        double grossSalary = 62000.009; // Should be truncated when created. See specification.

        String expectedMessage = "Employee IDb was registered successfully.";
        String actualMessage = facade.createEmployee(employeeID, employeeName, grossSalary, degree);
        assertEquals(expectedMessage, actualMessage);

        String expectedPrint = "PhD Mary Keller's gross salary is 83700.00 SEK per month.";
        String actualPrint = facade.printEmployee(employeeID);
        assertEquals(expectedPrint, actualPrint);

        // 62000.00 * 1.35 (PhD Bonus) = 83700.00 - (0.1 * 83700.00) =  75330.0;
        double netSalary = facade.getNetSalary(employeeID);
        assertEquals(75330.00, netSalary);
    }

    @Test
    public void shouldCreateDirectorEmployee() throws Exception {
        String employeeID = "IDc";
        String employeeName = "Alan Turing";
        String degree = "PhD";
        String dept = "Technical";
        double grossSalary = 62000.00; // Should be truncated

        String expectedMessage = "Employee IDc was registered successfully.";
        String actualMessage = facade.createEmployee(employeeID, employeeName, grossSalary, degree, dept);
        assertEquals(expectedMessage, actualMessage);

        String expectedPrint = "PhD Alan Turing's gross salary is 88700.00 SEK per month. Dept: Technical";
        String actualPrint = facade.printEmployee(employeeID);
        assertEquals(expectedPrint, actualPrint);
        assertEquals(59220.00, facade.getNetSalary(employeeID));

        // ---- A second director just for testing.
        String angelicaID = "IDd";
        String angelicaName = "Angelica Ross";
        String angelicaDept = "Business";
        String angelicaDegree = "BSc";
        double angelicaSalary = 20000.00;

        expectedMessage = "Employee IDd was registered successfully.";
        actualMessage = facade.createEmployee(angelicaID, angelicaName, angelicaSalary, angelicaDegree, angelicaDept);
        assertEquals(expectedMessage, actualMessage);

        expectedPrint = "BSc Angelica Ross's gross salary is 27000.00 SEK per month. Dept: Business";
        actualPrint = facade.printEmployee(angelicaID);
        assertEquals(expectedPrint, actualPrint);
        assertEquals(24300.00, facade.getNetSalary(angelicaID));

        // ---- A third director just for testing.
        String graceID = "IDe";
        String graceName = "Grace Hopper";
        String graceDept = "Human Resources";
        String graceDegree = "MSc";
        double graceSalary = 27000.00;

        expectedMessage = "Employee IDe was registered successfully.";
        actualMessage = facade.createEmployee(graceID, graceName, graceSalary, graceDegree, graceDept);
        assertEquals(expectedMessage, actualMessage);

        expectedPrint = "MSc Grace Hopper's gross salary is 37400.00 SEK per month. Dept: Human Resources";
        actualPrint = facade.printEmployee(graceID);
        assertEquals(expectedPrint, actualPrint);
        assertEquals(29920.00, facade.getNetSalary(graceID));
    }

    @Test
    public void shouldCreateInternEmployee() throws Exception {
        String angelaID = "IDf";
        String angelaName = "Angela Martin";
        double angelaSalary = 15000.00;
        int angelaGPA = 9;

        String expectedMessage = "Employee IDf was registered successfully.";
        String actualMessage = facade.createEmployee(angelaID, angelaName, angelaSalary, angelaGPA);
        assertEquals(expectedMessage, actualMessage);

        String expectedPrint = "Angela Martin's gross salary is 16000.00 SEK per month. GPA: 9";
        String actualPrint = facade.printEmployee(angelaID);
        assertEquals(expectedPrint, actualPrint);
        assertEquals(16000.00, facade.getNetSalary(angelaID));

        // ---- second intern with average grade
        String dwitghtID = "IDg";
        String dwitghtName = "Dwight Schrute";
        double dwitghtSalary = 15000.00;
        int dwightGPA = 7;

        expectedMessage = "Employee IDg was registered successfully.";
        actualMessage = facade.createEmployee(dwitghtID, dwitghtName, dwitghtSalary, dwightGPA);
        assertEquals(expectedMessage, actualMessage);

        expectedPrint = "Dwight Schrute's gross salary is 15000.00 SEK per month. GPA: 7";
        actualPrint = facade.printEmployee(dwitghtID);
        assertEquals(expectedPrint, actualPrint);
        assertEquals(15000.00, facade.getNetSalary(dwitghtID));

        // ---- third intern with average grade
        String michaelID = "IDh";
        String michaelName = "Michael Scott";
        double michaelSalary = 15000.00;
        int michaelGPA = 3;

        expectedMessage = "Employee IDh was registered successfully.";
        actualMessage = facade.createEmployee(michaelID, michaelName, michaelSalary, michaelGPA);
        assertEquals(expectedMessage, actualMessage);

        expectedPrint = "Michael Scott's gross salary is 0.00 SEK per month. GPA: 3";
        actualPrint = facade.printEmployee(michaelID);
        assertEquals(expectedPrint, actualPrint);
        assertEquals(0.00, facade.getNetSalary(michaelID));
    }

    @Test
    public void shouldRemoveEmployee() throws Exception {
        String empID = "Emp8";
        String expectedMessage = "Employee Emp8 was successfully removed.";
        String actualMessage = facade.removeEmployee(empID);
        assertEquals(expectedMessage, actualMessage);

        // Makes sure that the expected exception was thrown.
        Exception exception = assertThrows(Exception.class, () -> {
            facade.printEmployee(empID);
        });

        assertEquals("Employee Emp8 was not registered yet.", exception.getMessage());
    }

    @Test
    public void shouldPrintEmployee() throws Exception {
        String blancaMessage = "PhD Blanca's gross salary is 65750.00 SEK per month. Dept: Human Resources";
        String angelMessage  = "Angel's gross salary is 28500.10 SEK per month. GPA: 7";
        String prayMessage   = "BSc Pray Tell's gross salary is 27500.27 SEK per month.";
        String rickyMessage  = "Ricky's gross salary is 23500.00 SEK per month.";

        assertEquals(blancaMessage, facade.printEmployee("Emp2"));
        assertEquals(angelMessage , facade.printEmployee("Emp5"));
        assertEquals(prayMessage  , facade.printEmployee("Emp3"));
        assertEquals(rickyMessage , facade.printEmployee("Emp7"));
    }

    @Test
    public void shouldPrintAllEmployees() throws Exception {
        String expectedPrint = "All registered employees:" + TestResources.EOL +
                "MSc Elektra's gross salary is 47000.60 SEK per month. Dept: Business" + TestResources.EOL +
                "PhD Blanca's gross salary is 65750.00 SEK per month. Dept: Human Resources" + TestResources.EOL +
                "BSc Pray Tell's gross salary is 27500.27 SEK per month." + TestResources.EOL +
                "Lulu's gross salary is 21000.00 SEK per month. GPA: 9"   + TestResources.EOL +
                "Angel's gross salary is 28500.10 SEK per month. GPA: 7"  + TestResources.EOL +
                "Candy's gross salary is 0.00 SEK per month. GPA: 4"      + TestResources.EOL +
                "Ricky's gross salary is 23500.00 SEK per month."         + TestResources.EOL +
                "Damon's gross salary is 22100.00 SEK per month."         + TestResources.EOL;

        assertEquals(expectedPrint, facade.printAllEmployees());
    }

    @Test
    public void shouldPrintTotalNetSalary() throws Exception {
        double expectedTotal = 198340.82;
        assertEquals(expectedTotal, facade.getTotalNetSalary());
    }

    @Test
    public void shouldPrintSortedEmployees() throws Exception {
        String expectedPrint = "Employees sorted by gross salary (ascending order):" + TestResources.EOL +
                "Candy's gross salary is 0.00 SEK per month. GPA: 4"      + TestResources.EOL +
                "Lulu's gross salary is 21000.00 SEK per month. GPA: 9"   + TestResources.EOL +
                "Damon's gross salary is 22100.00 SEK per month."         + TestResources.EOL +
                "Ricky's gross salary is 23500.00 SEK per month."         + TestResources.EOL +
                "BSc Pray Tell's gross salary is 27500.27 SEK per month." + TestResources.EOL +
                "Angel's gross salary is 28500.10 SEK per month. GPA: 7"  + TestResources.EOL +
                "MSc Elektra's gross salary is 47000.60 SEK per month. Dept: Business" + TestResources.EOL +
                "PhD Blanca's gross salary is 65750.00 SEK per month. Dept: Human Resources" + TestResources.EOL;

        assertEquals(expectedPrint, facade.printSortedEmployees());
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        assertEquals("Employee Emp6 was updated successfully", facade.updateEmployeeName("Emp6", "Candy Ferocity"));
        assertEquals("Employee Emp6 was updated successfully", facade.updateInternGPA("Emp6", 10));
        assertEquals("Employee Emp4 was updated successfully", facade.updateInternGPA("Emp4", 2));
        assertEquals("Employee Emp1 was updated successfully", facade.updateManagerDegree("Emp1", "PhD"));
        assertEquals("Employee Emp1 was updated successfully", facade.updateDirectorDept("Emp1", "Technical"));
        assertEquals("Employee Emp8 was updated successfully", facade.updateGrossSalary("Emp8", 15000.00));
        assertEquals("Employee Emp3 was updated successfully", facade.updateGrossSalary("Emp3", 28000.99));
        assertEquals("Employee Emp3 was updated successfully", facade.updateManagerDegree("Emp3", "MSc"));

        String expectedElektra = "PhD Elektra's gross salary is 52250.67 SEK per month. Dept: Technical";
        String expectedDamon = "Damon's gross salary is 15000.00 SEK per month.";
        String expectedCandy = "Candy Ferocity's gross salary is 36000.50 SEK per month. GPA: 10";
        String expectedLulu = "Lulu's gross salary is 0.00 SEK per month. GPA: 2";
        String expectedPray = "MSc Pray Tell's gross salary is 33601.18 SEK per month.";


        assertEquals(expectedElektra,facade.printEmployee("Emp1"));
        assertEquals(expectedPray   ,facade.printEmployee("Emp3"));
        assertEquals(expectedLulu   ,facade.printEmployee("Emp4"));
        assertEquals(expectedCandy  ,facade.printEmployee("Emp6"));
        assertEquals(expectedDamon  ,facade.printEmployee("Emp8"));
    }

    @Test
    public void shouldMapPerDegree() throws Exception {
        Map<String, Integer> actualMap = facade.mapEachDegree();
        assertEquals(actualMap.get("PhD"), 1);
        assertEquals(actualMap.get("MSc"), 1);
        assertEquals(actualMap.get("BSc"), 1);

        facade.removeEmployee("Emp2"); // Remove Blanca with the PhD
        //Adds temporary employees with more 3 MSc and 1 BSc
        facade.createEmployee("Temp1", "John Doe", 25000.0, "MSc");
        facade.createEmployee("Temp2", "Jane Doe", 25000.0, "MSc");
        facade.createEmployee("Temp3", "Mary Doe", 25000.0, "MSc");
        facade.createEmployee("Temp4", "Mark Doe", 25000.0, "BSc");

        actualMap = facade.mapEachDegree();
        assertFalse(actualMap.containsKey("PhD")); // there should not be a PhD anymore.
        assertEquals(actualMap.get("MSc"), 4);
        assertEquals(actualMap.get("BSc"), 2);
    }

    @Test
    public void shouldPromoteToManager() throws Exception {
        String damonID = "Emp8";
        String expectedMessage = "Emp8 promoted successfully to Manager.";
        String expectedEmployee = "PhD Damon's gross salary is 29835.00 SEK per month.";
        assertEquals(expectedMessage , facade.promoteToManager(damonID, "PhD"));
        assertEquals(expectedEmployee, facade.printEmployee(damonID));

        String elektraID = "Emp1";
        // Note, when converting, it will get Elektra's raw gross salary, i.e.,
        // the gross salary before the bonuses: 35000.50
        expectedMessage = "Emp1 promoted successfully to Manager.";
        expectedEmployee = "BSc Elektra's gross salary is 38500.55 SEK per month.";
        assertEquals(expectedMessage , facade.promoteToManager(elektraID, "BSc"));
        assertEquals(expectedEmployee, facade.printEmployee(elektraID));

        String angelID = "Emp5";
        expectedMessage = "Emp5 promoted successfully to Manager.";
        expectedEmployee = "MSc Angel's gross salary is 34200.11 SEK per month.";
        assertEquals(expectedMessage , facade.promoteToManager(angelID, "MSc"));
        assertEquals(expectedEmployee, facade.printEmployee(angelID));
    }

    @Test
    public void promoteToDirector() throws Exception {
        String rickyID = "Emp7";
        String expectedMessage = "Emp7 promoted successfully to Director.";
        String expectedEmployee = "PhD Ricky's gross salary is 36725.00 SEK per month. Dept: Human Resources";
        assertEquals(expectedMessage , facade.promoteToDirector(rickyID, "PhD", "Human Resources"));
        assertEquals(expectedEmployee, facade.printEmployee(rickyID));

        String candyID = "Emp6";
        expectedMessage = "Emp6 promoted successfully to Director.";
        expectedEmployee = "BSc Candy's gross salary is 43500.55 SEK per month. Dept: Technical";
        assertEquals(expectedMessage , facade.promoteToDirector(candyID, "BSc", "Technical"));
        assertEquals(expectedEmployee, facade.printEmployee(candyID));

        String prayID = "Emp3";
        expectedMessage = "Emp3 promoted successfully to Director.";
        expectedEmployee = "BSc Pray Tell's gross salary is 32500.27 SEK per month. Dept: Business";
        assertEquals(expectedMessage , facade.promoteToDirector(prayID, "BSc", "Business"));
        assertEquals(expectedEmployee, facade.printEmployee(prayID));
    }

    @Test
    public void promoteToIntern() throws Exception {
        String blancaID = "Emp2";
        String expectedMessage = "Emp2 promoted successfully to Intern.";
        String expectedEmployee = "Blanca's gross salary is 46000.00 SEK per month. GPA: 10";
        assertEquals(expectedMessage , facade.promoteToIntern(blancaID, 10));
        assertEquals(expectedEmployee, facade.printEmployee(blancaID));

        String damonID = "Emp8";
        expectedMessage = "Emp8 promoted successfully to Intern.";
        expectedEmployee = "Damon's gross salary is 0.00 SEK per month. GPA: 1";
        assertEquals(expectedMessage , facade.promoteToIntern(damonID, 1));
        assertEquals(expectedEmployee, facade.printEmployee(damonID));

        String prayID = "Emp3";
        expectedMessage = "Emp3 promoted successfully to Intern.";
        expectedEmployee = "Pray Tell's gross salary is 25000.25 SEK per month. GPA: 6";
        assertEquals(expectedMessage , facade.promoteToIntern(prayID, 6));
        assertEquals(expectedEmployee, facade.printEmployee(prayID));
    }
}
