package com.shohidulhaque.my_people.discovery_service.persistence;

import com.shohidulhaque.my_people.discovery_service.data_transfer_object.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}