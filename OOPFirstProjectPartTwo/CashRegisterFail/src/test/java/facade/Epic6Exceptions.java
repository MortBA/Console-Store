package facade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class Epic6Exceptions {

    private Facade facade;

    @BeforeAll
    public static void setupSystem(){
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    public void setupFacade(){
        facade = new Facade();
    }

    @Test
    public void shouldThrowExceptionForNoEmployees(){
        String expectedMessage = "No employees registered yet.";

        Exception exceptionPrintAll = assertThrows(Exception.class, () -> {
            facade.printAllEmployees();
        });

        Exception exceptionNetSalaries = assertThrows(Exception.class, () -> {
            facade.getTotalNetSalary();
        });

        Exception exceptionAllSorted = assertThrows(Exception.class, () -> {
            facade.printSortedEmployees();
        });

        Exception exceptionDegreeMap = assertThrows(Exception.class, () -> {
            facade.mapEachDegree();
        });

        assertEquals(expectedMessage, exceptionPrintAll.getMessage());
        assertEquals(expectedMessage, exceptionNetSalaries.getMessage());
        assertEquals(expectedMessage, exceptionAllSorted.getMessage());
        assertEquals(expectedMessage, exceptionDegreeMap.getMessage());
    }

    @Test
    public void shouldThrowExceptionForMissingEmployee() throws Exception {
        String empID = "IDx";
        String expectedMessage = "Employee IDx was not registered yet.";

        Exception exceptionPrint = assertThrows(Exception.class, () -> {
            facade.printEmployee(empID);
        });
        Exception exceptionRemove = assertThrows(Exception.class, () -> {
            facade.removeEmployee(empID);
        });
        Exception exceptionSalary = assertThrows(Exception.class, () -> {
            facade.getNetSalary(empID);
        });
        Exception exceptionPromDirector = assertThrows(Exception.class, () -> {
            facade.promoteToDirector(empID, "PhD", "Business");
        });
        Exception exceptionPromManager = assertThrows(Exception.class, () -> {
            facade.promoteToManager(empID, "PhD");
        });
        Exception exceptionPromIntern = assertThrows(Exception.class, () -> {
            facade.promoteToIntern(empID, 2);
        });
        Exception exceptionUpdSalary = assertThrows(Exception.class, () -> {
            facade.updateGrossSalary(empID, 10000.00);
        });
        Exception exceptionUpdName = assertThrows(Exception.class, () -> {
            facade.updateEmployeeName(empID, "Joanne");
        });
        Exception exceptionUpdGPA = assertThrows(Exception.class, () -> {
            facade.updateInternGPA(empID, 10);
        });
        Exception exceptionUpdDegree = assertThrows(Exception.class, () -> {
            facade.updateManagerDegree(empID, "PhD");
        });
        Exception exceptionUpdDepartment = assertThrows(Exception.class, () -> {
            facade.updateDirectorDept(empID, "Technical");
        });

        assertEquals(expectedMessage, exceptionPrint.getMessage());
        assertEquals(expectedMessage, exceptionRemove.getMessage());
        assertEquals(expectedMessage, exceptionSalary.getMessage());
        assertEquals(expectedMessage, exceptionPromIntern.getMessage());
        assertEquals(expectedMessage, exceptionPromDirector.getMessage());
        assertEquals(expectedMessage, exceptionPromManager.getMessage());
        assertEquals(expectedMessage, exceptionUpdDegree.getMessage());
        assertEquals(expectedMessage, exceptionUpdSalary.getMessage());
        assertEquals(expectedMessage, exceptionUpdName.getMessage());
        assertEquals(expectedMessage, exceptionUpdGPA.getMessage());
        assertEquals(expectedMessage, exceptionUpdDepartment.getMessage());
    }

    @Test
    public void shouldNotCreateInvalidEmployee() throws Exception {
        Exception exceptionInvalidID = assertThrows(Exception.class, () -> {
            facade.createEmployee("", "Joanne", 20000.50);
        });
        Exception exceptionInvalidName = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "    ", 20000.50);
        });
        Exception exceptionNegativeSalary = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", -5000.23);
        });

        // Create valid and then try to update to invalid.
        facade.createEmployee("ID1", "Joanne", 20000.50);
        Exception exceptionUpdName = assertThrows(Exception.class, () -> {
            facade.updateEmployeeName("ID1", "");
        });
        Exception exceptionUpdSal = assertThrows(Exception.class, () -> {
            facade.updateGrossSalary("ID1", 0.00);
        });
        assertEquals("ID cannot be blank.", exceptionInvalidID.getMessage());
        assertEquals("Name cannot be blank.", exceptionInvalidName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionNegativeSalary.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());

        assertEquals("Name cannot be blank.", exceptionUpdName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
    }

    @Test
    public void shouldNotCreateInvalidManager() throws Exception {
        Exception exceptionInvalidID = assertThrows(Exception.class, () -> {
            facade.createEmployee("", "Joanne", 20000.50, "BSc");
        });
        Exception exceptionInvalidName = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", " ", 20000.50, "BSc");
        });
        Exception exceptionNegativeSalary = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", -5000.23, "BSc");
        });
        Exception exceptionInvalidDegree = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", 5000.23, "BCS");
        });

        // Create valid and then try to update to invalid.
        facade.createEmployee("ID1", "Joanne", 20000.50, "BSc");
        Exception exceptionUpdName = assertThrows(Exception.class, () -> {
            facade.updateEmployeeName("ID1", "");
        });
        Exception exceptionUpdSal = assertThrows(Exception.class, () -> {
            facade.updateGrossSalary("ID1", 0.00);
        });
        Exception exceptionUpdDegree = assertThrows(Exception.class, () -> {
            facade.updateManagerDegree("ID1", "DhP");
        });

        assertEquals("ID cannot be blank.", exceptionInvalidID.getMessage());
        assertEquals("Name cannot be blank.", exceptionInvalidName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionNegativeSalary.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
        assertEquals("Degree must be one of the options: BSc, MSc or PhD.", exceptionInvalidDegree.getMessage());

        assertEquals("Name cannot be blank.", exceptionUpdName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
        assertEquals("Degree must be one of the options: BSc, MSc or PhD.", exceptionUpdDegree.getMessage());
    }

    @Test
    public void shouldNotCreateInvalidDirector() throws Exception {
        Exception exceptionInvalidID = assertThrows(Exception.class, () -> {
            facade.createEmployee("", "Joanne", 20000.50, "BSc");
        });
        Exception exceptionInvalidName = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", " ", 20000.50, "BSc");
        });
        Exception exceptionNegativeSalary = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", -5000.23, "BSc");
        });
        Exception exceptionInvalidDegree = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", 5000.23, "BCS");
        });
        Exception exceptionInvalidDept = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", 5000.23, "BSc", " ");
        });

        // Create valid and then try to update to invalid.
        facade.createEmployee("ID1", "Joanne", 20000.50, "BSc", "Business");
        Exception exceptionUpdName = assertThrows(Exception.class, () -> {
            facade.updateEmployeeName("ID1", "");
        });
        Exception exceptionUpdSal = assertThrows(Exception.class, () -> {
            facade.updateGrossSalary("ID1", 0.00);
        });
        Exception exceptionUpdDegree = assertThrows(Exception.class, () -> {
            facade.updateManagerDegree("ID1", "DhP");
        });
        Exception exceptionUpdDept = assertThrows(Exception.class, () -> {
            facade.updateDirectorDept("ID1", "Physics");
        });

        assertEquals("ID cannot be blank.", exceptionInvalidID.getMessage());
        assertEquals("Name cannot be blank.", exceptionInvalidName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionNegativeSalary.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
        assertEquals("Degree must be one of the options: BSc, MSc or PhD.", exceptionInvalidDegree.getMessage());
        assertEquals("Department must be one of the options: Business, Human Resources or Technical.", exceptionInvalidDept.getMessage());

        assertEquals("Name cannot be blank.", exceptionUpdName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
        assertEquals("Degree must be one of the options: BSc, MSc or PhD.", exceptionUpdDegree.getMessage());
        assertEquals("Department must be one of the options: Business, Human Resources or Technical.", exceptionUpdDept.getMessage());
    }

    @Test
    public void shouldNotCreateInvalidIntern() throws Exception {
        Exception exceptionInvalidID = assertThrows(Exception.class, () -> {
            facade.createEmployee("", "Joanne", 20000.50, 5);
        });
        Exception exceptionInvalidName = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", " ", 20000.50, 5);
        });
        Exception exceptionNegativeSalary = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", -5000.23, 5);
        });
        Exception exceptionInvalidGPA = assertThrows(Exception.class, () -> {
            facade.createEmployee("ID1", "Joanne", 5000.23, -5);
        });

        // Create valid and then try to update to invalid.
        facade.createEmployee("ID1", "Joanne", 20000.50, 5);
        Exception exceptionUpdName = assertThrows(Exception.class, () -> {
            facade.updateEmployeeName("ID1", "");
        });
        Exception exceptionUpdSal = assertThrows(Exception.class, () -> {
            facade.updateGrossSalary("ID1", 0.00);
        });
        Exception exceptionUpdateGPA = assertThrows(Exception.class, () -> {
            facade.updateInternGPA("ID1", 15);
        });

        assertEquals("ID cannot be blank.", exceptionInvalidID.getMessage());
        assertEquals("Name cannot be blank.", exceptionInvalidName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionNegativeSalary.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
        assertEquals("-5 outside range. Must be between 0-10.", exceptionInvalidGPA.getMessage());

        assertEquals("Name cannot be blank.", exceptionUpdName.getMessage());
        assertEquals("Salary must be greater than zero.", exceptionUpdSal.getMessage());
        assertEquals("15 outside range. Must be between 0-10.", exceptionUpdateGPA.getMessage());
    }
}