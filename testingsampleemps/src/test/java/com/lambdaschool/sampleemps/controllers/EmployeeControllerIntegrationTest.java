package com.lambdaschool.sampleemps.controllers;

import com.lambdaschool.sampleemps.SampleempsApplicationTests;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = SampleempsApplicationTests.class)
@AutoConfigureMockMvc
@WithUserDetails(value = "testcinnamon")
public class EmployeeControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void whenMeasuredResponseTime() throws
                                           Exception
    {
        long time = System.currentTimeMillis();
        mockMvc.perform(get("/employees/employees"))
            .andDo(print());
        long responseTime = (System.currentTimeMillis() - time);

        assertTrue("timestamp",
            (responseTime < 5000L));
    }

    @Test
    public void listAllEmployees() throws Exception
    {
        mockMvc.perform(get("/employees/employees"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("CINABUN")));
    }

    @Test
    public void getEmployeeById() throws Exception
    {
        mockMvc.perform(get("/employees/employee/3"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("CINABUN")));
    }

    @Test
    public void getEmployeeByIdNotFound() throws Exception
    {
        mockMvc.perform(get("/employees/employee/100"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("ResourceNotFoundException")));
    }

    @Test
    public void listEmployeesWithName() throws Exception
    {
        mockMvc.perform(get("/employees/employeename/cin"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("CINABUN")));
    }

    @Test
    public void listEmployeesWithEmail() throws Exception
    {
        mockMvc.perform(get("/employees/employeeemail/local.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("hops@local.com")));
    }

    @Test
    public void getEmpJobCounts() throws Exception
    {
        mockMvc.perform(get("/employees/job/counts"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("count_job_titles")));
    }

    @Test
    public void addNewEmployee() throws Exception
    {
        mockMvc.perform(post("/employees/employee")
            .content("{ " +
                "    \"name\": \"monk\", " +
                "    \"salary\": 120000.0, " +
                "    \"jobnames\": [ " +
                "        { " +
                "            \"jobname\": { " +
                "                \"jobtitleid\": 1, " +
                "                \"title\": \"Big Boss\" " +
                "            }, " +
                "            \"manager\": \"Stumps\" " +
                "        } " +
                "    ], " +
                "    \"emails\": [ " +
                "        { " +
                "            \"email\": \"mojo@local.com\" " +
                "        } " +
                "    ] " +
                "}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.header()
                .exists("location"));
    }

    @Test
    public void updateFullEmployee() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/employee/6")
            .content("{ " +
                "    \"name\": \"mojo\", " +
                "    \"salary\": 120000.0, " +
                "    \"jobnames\": [ " +
                "        { " +
                "            \"jobname\": { " +
                "                \"jobtitleid\": 1, " +
                "                \"title\": \"Big Boss\" " +
                "            }, " +
                "            \"manager\": \"Stumps\" " +
                "        } " +
                "    ], " +
                "    \"emails\": [ " +
                "        { " +
                "            \"email\": \"mojo@local.com\" " +
                "        } " +
                "    ] " +
                "}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void updateFullEmployeeNotFound() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/employee/7777")
            .content("{ " +
                "    \"name\": \"mojo\", " +
                "    \"salary\": 120000.0, " +
                "    \"jobnames\": [ " +
                "        { " +
                "            \"jobname\": { " +
                "                \"jobtitleid\": 1, " +
                "                \"title\": \"Big Boss\" " +
                "            }, " +
                "            \"manager\": \"Stumps\" " +
                "        } " +
                "    ], " +
                "    \"emails\": [ " +
                "        { " +
                "            \"email\": \"mojo@local.com\" " +
                "        } " +
                "    ] " +
                "}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateEmployee() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.patch("/employees/employee/6")
            .content("{ " +
                "    \"name\": \"mojo\", " +
                "    \"salary\": 120000.0, " +
                "    \"jobnames\": [ " +
                "        { " +
                "            \"jobname\": { " +
                "                \"jobtitleid\": 1, " +
                "                \"title\": \"Big Boss\" " +
                "            }, " +
                "            \"manager\": \"Stumps\" " +
                "        } " +
                "    ], " +
                "    \"emails\": [ " +
                "        { " +
                "            \"email\": \"mojo@local.com\" " +
                "        } " +
                "    ] " +
                "}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void updateEmployeeNotFound() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.patch("/employees/employee/777")
            .content("{ " +
                "    \"name\": \"mojo\", " +
                "    \"salary\": 120000.0, " +
                "    \"jobnames\": [ " +
                "        { " +
                "            \"jobname\": { " +
                "                \"jobtitleid\": 1, " +
                "                \"title\": \"Big Boss\" " +
                "            }, " +
                "            \"manager\": \"Stumps\" " +
                "        } " +
                "    ], " +
                "    \"emails\": [ " +
                "        { " +
                "            \"email\": \"mojo@local.com\" " +
                "        } " +
                "    ] " +
                "}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteEmployeeById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/employee/8"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteEmployeeByIdNotFound() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/employee/100"))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }
}