package com.lambdaschool.sampleemps.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.sampleemps.SampleempsApplicationTests;
import com.lambdaschool.sampleemps.exceptions.ResourceNotFoundException;
import com.lambdaschool.sampleemps.models.Email;
import com.lambdaschool.sampleemps.models.Employee;
import com.lambdaschool.sampleemps.models.EmployeeTitles;
import com.lambdaschool.sampleemps.models.JobTitle;
import com.lambdaschool.sampleemps.views.EmpNameCountJobs;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleempsApplicationTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeServiceImplWithDatabaseTest
{
    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setUp() throws Exception
    {
        // mocks -> fake data
        // stubs -> fake methods
        // Java -> we just call everything a mock

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findAllEmployees()
    {
        assertEquals(3,
            employeeService.findAllEmployees()
                .size());
    }

    @Test
    public void b_findEmployeeById()
    {
        assertEquals("CINABUN",
            employeeService.findEmployeeById(3)
                .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ba_findEmployeeByIdNotFound()
    {
        assertEquals("CINABUN",
            employeeService.findEmployeeById(10L)
                .getName());
    }

    @Test
    public void c_findEmployeeNameContaining()
    {
        assertEquals(1,
            employeeService.findEmployeeNameContaining("CIN")
                .size());
    }

    @Test
    public void d_findEmployeeEmailContaining()
    {
        assertEquals(3,
            employeeService.findEmployeeEmailContaining("LOCAL")
                .size());
    }

    @Test
    public void da_getEmpNameCountJobs()
    {
        List<EmpNameCountJobs> myListTest;
        myListTest = employeeService.getEmpNameCountJobs();

        assertEquals(myListTest.size(),
            3);
    }

    @Test
    public void db_delete()
    {
        employeeService.delete(6);
        assertEquals(2,
            employeeService.findAllEmployees()
                .size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void dc_deletefailed()
    {
        employeeService.delete(777);
        assertEquals(3,
            employeeService.findAllEmployees()
                .size());
    }

    @Test
    public void e_save()
    {
        JobTitle jt1 = new JobTitle();
        jt1.setTitle("Turtle");
        jt1.setJobtitleid(1);

        Employee emp1 = new Employee();
        emp1.setName("Stumps");
        emp1.setSalary(80000.00);
        emp1.getEmails()
            .add(new Email("stumps@local.com",
                emp1));
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt1,
                "Stumps"));

        Employee addEmp = employeeService.save(emp1);
        assertNotNull(addEmp);
        assertEquals(emp1.getName(),
            addEmp.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ea_saveJTNotFound()
    {
        JobTitle jt1 = new JobTitle();
        jt1.setTitle("Turtle");
        jt1.setJobtitleid(10);

        Employee emp1 = new Employee();
        emp1.setName("BarnTheRabbit");
        emp1.setSalary(80000.00);
        emp1.getEmails()
            .add(new Email("barntherabbit@local.com",
                emp1));
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt1,
                "Stumps"));

        Employee addEmp = employeeService.save(emp1);
        assertNotNull(addEmp);
        assertEquals(emp1.getName(),
            addEmp.getName());
    }

    @Test
    public void eb_savePut() throws Exception
    {
        JobTitle jt1 = new JobTitle();
        jt1.setTitle("Turtle");
        jt1.setJobtitleid(1);

        Employee emp1 = new Employee();
        emp1.setName("CinTheRabbit");
        emp1.setEmployeeid(3);
        emp1.setSalary(80000.00);
        emp1.getEmails()
            .add(new Email("cintherabbit@local.com",
                emp1));
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt1,
                "Stumps"));

        Employee addEmp = employeeService.save(emp1);
        assertNotNull(addEmp);
        assertEquals(emp1.getName(),
            addEmp.getName());
    }

    @Test
    public void g_update() throws Exception
    {
        JobTitle jt1 = new JobTitle();
        jt1.setTitle("Turtle");
        jt1.setJobtitleid(1);

        Employee emp1 = new Employee();
        emp1.setName("StumpsAgain");
        emp1.setEmployeeid(8);
        emp1.setSalary(80000.00);
        emp1.getEmails()
            .add(new Email("stumpsagain@local.com",
                emp1));
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt1,
                "Stumps"));

        Employee addEmp = employeeService.update(emp1,
            8);
        assertNotNull(addEmp);
        assertEquals(emp1.getName(),
            addEmp.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ga_updateJTNotFound() throws Exception
    {
        JobTitle jt1 = new JobTitle();
        jt1.setTitle("Turtle");
        jt1.setJobtitleid(777);

        Employee emp1 = new Employee();
        emp1.setName("Stumps");
        emp1.setEmployeeid(3);
        emp1.setSalary(80000.00);
        emp1.getEmails()
            .add(new Email("stumps@local.com",
                emp1));
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt1,
                "Stumps"));

        Employee addEmp = employeeService.update(emp1,
            3);
        assertNotNull(addEmp);
        assertEquals(emp1.getName(),
            addEmp.getName());
    }
}
