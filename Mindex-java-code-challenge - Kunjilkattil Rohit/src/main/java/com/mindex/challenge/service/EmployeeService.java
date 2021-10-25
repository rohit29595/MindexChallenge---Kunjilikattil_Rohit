package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;

/** Interface for Employee type **/
public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);

}
