package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EmployeeTest {

    @Test
    void createEmployee_Success() throws InstantiationException {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;
<<<<<<< HEAD
        Employee employee = new Employee(firstName, lastName, description, jobYears);
=======
        Employee employee = new Employee(firstName, lastName, description, jobYears, "frodo.baggins@shire.com");
>>>>>>> email-field
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(String.valueOf(jobYears), employee.getJobYears());
    }

    @Test
    void createEmployee_InvalidFirstName(){
        String firstName = "";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_InvalidLastName(){
        String firstName = "Frodo";
        String lastName = "";
        String description = "ring bearer";
        int jobYears = 1;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_InvalidDescription(){
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "";
        int jobYears = 1;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_InvalidJobYears(){
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = -1;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_NullFirstName(){
        String firstName = null;
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 0;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_NullLastName(){
        String firstName = "Frodo";
        String lastName = null;
        String description = "ring bearer";
        int jobYears = 0;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_NullDescription(){
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = null;
        int jobYears = 0;
<<<<<<< HEAD
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears));
=======
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com"));
>>>>>>> email-field
    }

    @Test
    void createEmployee_ZeroJobYears() throws InstantiationException {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 0;
<<<<<<< HEAD
        Employee employee = new Employee(firstName, lastName, description, jobYears);
=======
        Employee employee = new Employee(firstName, lastName, description, jobYears,"frodo.baggins@shire.com");
>>>>>>> email-field
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(String.valueOf(jobYears), employee.getJobYears());
    }
<<<<<<< HEAD
=======

    @Test
    void createEmployee_EmtpyEmail(){
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 0;
        String email = "";
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,email));
    }

    @Test
    void createEmployee_NullEmail(){
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 0;
        String email = null;
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobYears,email));
    }
>>>>>>> email-field
}