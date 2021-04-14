package com.shohidulhaque.my_people.common_model;

import com.shohidulhaque.my_people.discovery_service.data_transfer_object.Employee;
import com.shohidulhaque.my_people.discovery_service.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

  private final EmployeeRepository repository;

  @Autowired
  public DatabaseLoader(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... strings) throws Exception {
    this.repository.save(new Employee("Frodo", "Baggins", "ring bearer"));
    this.repository.save(new Employee("John", "Hooper", "fire bearer"));
    this.repository.save(new Employee("Abdul", "Carl", "water bearer"));
    this.repository.save(new Employee("Cane", "Kate", "earth bearer"));

  }

}
