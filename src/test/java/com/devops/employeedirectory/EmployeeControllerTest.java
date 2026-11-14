package com.devops.employeedirectory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / should return application info and status")
    void testRootEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.app", is("employee-directory")))
                .andExpect(jsonPath("$.host").exists());
    }

    @Test
    @DisplayName("GET /employees should return the employee list")
    void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("Alice Smith")));
    }

    @Test
    @DisplayName("GET /employees/{id} should return employee details when found")
    void testGetEmployeeByIdFound() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Alice Smith")))
                .andExpect(jsonPath("$.department", is("Engineering")))
                .andExpect(jsonPath("$.email", is("alice.smith@example.com")));
    }

    @Test
    @DisplayName("GET /employees/{id} should return 404 when employee does not exist")
    void testGetEmployeeByIdNotFound() throws Exception {
        mockMvc.perform(get("/employees/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")));
    }

    @Test
    @DisplayName("POST /employees should create a new employee with valid payload")
    void testCreateEmployeeSuccess() throws Exception {
        String payload = """
                {
                    "name": "Charlie Brown",
                    "department": "Security",
                    "email": "charlie.brown@example.com"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("Charlie Brown")))
                .andExpect(jsonPath("$.department", is("Security")))
                .andExpect(jsonPath("$.email", is("charlie.brown@example.com")));
    }

    @Test
    @DisplayName("POST /employees should return 400 Bad Request when mandatory fields are missing")
    void testCreateEmployeeValidationFailure() throws Exception {
        String payload = """
                {
                    "name": "",
                    "department": "",
                    "email": "invalid-email-address"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.department").exists())
                .andExpect(jsonPath("$.validationErrors.email").exists());
    }

    @Test
    @DisplayName("GET /actuator/health should return UP status")
    void testActuatorHealth() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")));
    }
}
