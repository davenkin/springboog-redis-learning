package davenkin.springboot.web.service;

import davenkin.springboot.web.model.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static davenkin.springboot.web.cache.CacheConstants.EMPLOYEE_CACHE;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final Map<String, Employee> departments = new HashMap<>();
    private boolean retrievedFromDB = false;

    @Caching(evict = {@CacheEvict(value = EMPLOYEE_CACHE, key = "#employee.id")})
    public void save(Employee employee) {
        this.departments.put(employee.getId(), employee);
    }

    @Cacheable(value = EMPLOYEE_CACHE, key = "#id")
    public Employee getDepartment(String id) {
        this.retrievedFromDB = true;
        return this.departments.get(id);
    }

    public void resetRetrievedFromDB() {
        this.retrievedFromDB = false;
    }
}
