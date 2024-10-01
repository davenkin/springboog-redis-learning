package davenkin.springboot.web.service;

import davenkin.springboot.web.model.Department;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static davenkin.springboot.web.cache.CacheConstants.DEPARTMENT_CACHE;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final Map<String, Department> departments = new HashMap<>();
    private boolean retrievedFromDB = false;

    @Caching(evict = {@CacheEvict(value = DEPARTMENT_CACHE, key = "#department.id")})
    public void save(Department department) {
        this.departments.put(department.getId(), department);
    }

    @Cacheable(value = DEPARTMENT_CACHE, key = "#id")
    public Department getDepartment(String id) {
        this.retrievedFromDB = true;
        return this.departments.get(id);
    }

    public void resetRetrievedFromDB() {
        this.retrievedFromDB = false;
    }
}
