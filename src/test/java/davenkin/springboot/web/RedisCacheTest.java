package davenkin.springboot.web;

import davenkin.springboot.web.model.Employee;
import davenkin.springboot.web.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RedisCacheTest {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void should_cache_employee_using_jackson() {
        Employee employee = new Employee("123", "finance");
        this.employeeService.save(employee);
        this.employeeService.getDepartment("123");
        assertTrue(this.employeeService.isRetrievedFromDB());

        employeeService.resetRetrievedFromDB();
        Employee dbEmployee = employeeService.getDepartment("123");
        assertFalse(employeeService.isRetrievedFromDB());
        assertEquals(employee.getName(), dbEmployee.getName());
    }
}
