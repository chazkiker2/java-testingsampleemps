package com.lambdaschool.sampleemps.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.sampleemps.SampleempsApplication;
import com.lambdaschool.sampleemps.models.*;
import com.lambdaschool.sampleemps.services.EmployeeService;
import com.lambdaschool.sampleemps.views.EmpNameCountJobs;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = SampleempsApplication.class,
    properties = {
        "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})

public class EmployeeControllerUnitTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private List<Employee> empList = new ArrayList<>();

    private List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        // Set up my web application context
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();

        // working with employees
        JobTitle jt1 = new JobTitle();
        jt1.setTitle("Test Big Boss");
        jt1.setJobtitleid(1);

        JobTitle jt2 = new JobTitle();
        jt2.setTitle("Test Wizard");
        jt2.setJobtitleid(2);

        Employee emp1 = new Employee();
        emp1.setEmployeeid(11);
        emp1.setName("CINABUN");
        emp1.setSalary(80000.00);
        emp1.getEmails()
            .add(new Email("hops@local.com",
                emp1));
        emp1.getEmails()
            .get(0)
            .setEmailid(111);
        emp1.getEmails()
            .add(new Email("bunny@hoppin.local",
                emp1));
        emp1.getEmails()
            .get(1)
            .setEmailid(112);
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt1,
                "Stumps"));
        emp1.getJobnames()
            .add(new EmployeeTitles(emp1,
                jt2,
                "Stumps"));

        Employee emp2 = new Employee();
        emp2.setEmployeeid(12);
        emp2.setName("BARNY");
        emp2.setSalary(80000.00);
        emp2.getEmails()
            .add(new Email("barnbarn@local.com",
                emp2));
        emp2.getEmails()
            .get(0)
            .setEmailid(121);

        emp2.getJobnames()
            .add(new EmployeeTitles(emp2,
                jt1,
                "Stumps"));

        Employee emp3 = new Employee();
        emp3.setEmployeeid(13);
        emp3.setName("JOHN");
        emp3.setSalary(75000.00);

        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);

        // working with users
        // this is not actually needed for this unit but is put here for reference
        Role r1 = new Role("ADMIN");
        r1.setRoleid(1);
        Role r2 = new Role("USER");
        r2.setRoleid(2);

        User u1 = new User("testbarnbarn",
            "password");
        u1.setUserid(11);
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));

        User u2 = new User("testadmin",
            "password");
        u2.setUserid(12);
        u2.getRoles()
            .add(new UserRoles(u2,
                r1));

        // users
        User u3 = new User("testcinnamon",
            "ILuvM4th!");
        u3.setUserid(13);
        u3.getRoles()
            .add(new UserRoles(u3,
                r2));

        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

        // if you want to print the list
        // userList.forEach(u -> System.out.println(u));
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllEmployees() throws Exception
    {
        String apiUrl = "/employees/employees";

        Mockito.when(employeeService.findAllEmployees())
            .thenReturn(empList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(empList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getEmployeeById() throws Exception
    {
        String apiUrl = "/employees/employee/1";

        Mockito.when(employeeService.findEmployeeById(1))
            .thenReturn(empList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(empList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns Employee",
            er,
            tr);
    }

    @Test
    public void getEmployeeByIdNotFound() throws Exception
    {
        String apiUrl = "/employees/employee/1111";

        Mockito.when(employeeService.findEmployeeById(1111))
            .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns Employee Empty",
            er,
            tr);
    }

    @Test
    public void listEmployeesWithName() throws Exception
    {
        String apiUrl = "/employees/employeename/test";

        Mockito.when(employeeService.findEmployeeNameContaining(any(String.class)))
            .thenReturn(empList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(empList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void listEmployeesWithEmail() throws Exception
    {
        String apiUrl = "/employees/employeeemail/test";

        Mockito.when(employeeService.findEmployeeEmailContaining(any(String.class)))
            .thenReturn(empList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(empList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getEmpJobCounts() throws Exception
    {
        String apiUrl = "/employees/job/counts";
        List<EmpNameCountJobs> empNameCountJobs = new ArrayList<>();
        Mockito.when(employeeService.getEmpNameCountJobs())
            .thenReturn(empNameCountJobs);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(empNameCountJobs);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void addNewEmployee() throws Exception
    {
        String apiUrl = "/employees/employee";

        Mockito.when(employeeService.save(any(Employee.class)))
            .thenReturn(empList.get(0));

        String requestBody = "{ " +
            "    \"name\": \"anewuser\", " +
            "    \"salary\": 80000.0, " +
            "    \"jobnames\": [ " +
            "        { " +
            "            \"jobname\": { " +
            "                \"title\": \"Big Boss\" " +
            "            }, " +
            "            \"manager\": \"Stumps\" " +
            "        }, " +
            "        { " +
            "            \"jobname\": { " +
            "                \"title\": \"Wizard\" " +
            "            }, " +
            "            \"manager\": \"Stumps\" " +
            "        } " +
            "    ], " +
            "    \"emails\": [ " +
            "        { " +
            "            \"email\": \"iamnew@local.com\" " +
            "        }, " +
            "        { " +
            "            \"email\": \"iamnewy@hoppin.local\" " +
            "        } " +
            "    ] " +
            "}";
        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullEmployee() throws Exception
    {
        String apiUrl = "/employees/employee/3";

        Mockito.when(employeeService.save(any(Employee.class)))
            .thenReturn(empList.get(0));

        String requestBody = "{ " +
            "    \"employeeid\": 3, " +
            "    \"name\": \"anewuser\", " +
            "    \"salary\": 80000.0, " +
            "    \"jobnames\": [ " +
            "        { " +
            "            \"jobname\": { " +
            "                \"title\": \"Big Boss\" " +
            "            }, " +
            "            \"manager\": \"Stumps\" " +
            "        }, " +
            "        { " +
            "            \"jobname\": { " +
            "                \"title\": \"Wizard\" " +
            "            }, " +
            "            \"manager\": \"Stumps\" " +
            "        } " +
            "    ], " +
            "    \"emails\": [ " +
            "        { " +
            "            \"email\": \"iamnew@local.com\" " +
            "        }, " +
            "        { " +
            "            \"email\": \"iamnewy@hoppin.local\" " +
            "        } " +
            "    ] " +
            "}";
        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateEmployee() throws Exception
    {
        String apiUrl = "/employees/employee/3";

        Mockito.when(employeeService.update(any(Employee.class),
            any(Long.class)))
            .thenReturn(empList.get(0));

        String requestBody = "{ " +
            "    \"name\": \"anewuser\", " +
            "    \"salary\": 80000.0, " +
            "    \"emails\": [ " +
            "        { " +
            "            \"email\": \"iamnew@local.com\" " +
            "        }, " +
            "        { " +
            "            \"email\": \"iamnewy@hoppin.local\" " +
            "        } " +
            "    ] " +
            "}";
        RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestBody);

        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteEmployeeById() throws Exception
    {
        String apiUrl = "/employees/employee/3";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
            "3")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }
}