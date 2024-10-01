package davenkin.springboot.web;

import davenkin.springboot.web.model.Department;
import davenkin.springboot.web.model.Employee;
import davenkin.springboot.web.service.DepartmentService;
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

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void should_cache_non_final_class_using_jackson() {
        Employee employee = new Employee("123", "Mike");
        this.employeeService.save(employee);
        this.employeeService.getEmployee("123");
        assertTrue(this.employeeService.isRetrievedFromDB());

        employeeService.resetRetrievedFromDB();
        Employee dbEmployee = employeeService.getEmployee("123");
        assertFalse(employeeService.isRetrievedFromDB());
        assertEquals(employee.getName(), dbEmployee.getName());
    }

    @Test
    public void should_cache_final_class_using_jackson() {
        Department department = new Department("123", "Finance");
        this.departmentService.save(department);
        this.departmentService.getDepartment("123");
        assertTrue(this.departmentService.isRetrievedFromDB());

        departmentService.resetRetrievedFromDB();
        Department deDepartment = departmentService.getDepartment("123");
        assertFalse(departmentService.isRetrievedFromDB());
        assertEquals(department.getName(), deDepartment.getName());
    }
}
