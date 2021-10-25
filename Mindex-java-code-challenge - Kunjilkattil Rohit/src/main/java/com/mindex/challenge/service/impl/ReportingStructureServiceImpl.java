package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


/** ReportingStructure Service Method Implementation. **/

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String employeeId){

        LOG.debug("Reporting Structure for Employee with the ID [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);
        int totalDistReports = getNumberOfReports(employeeId);

        return new ReportingStructure(employee,totalDistReports);

    }

    /** Here we define the method getNumberOfReports to get the total number of reports for an employee by
     * taking in an employee id. The methods reads the id (say 'A') and gets the employee details. Next,
     * the reports variable stores the total number of direct reports for the employee with id 'A'. Then, we use
     * the variable totalReports to keep a track of the total number of reports, Then, we loop through all the employee
     * objects in the reports list and recursively apply the function to each employee id**/


    public int getNumberOfReports(String employeeId){
        Employee employee = employeeService.read(employeeId);

        List<Employee> reports = employee.getDirectReports();

        if(reports == null){
            throw new RuntimeException("No further direct reports for employee with ID : " + employeeId);
        }

        int totalReports = 0;


        for(Employee e : reports){
            try {
                totalReports += 1;
                totalReports += getNumberOfReports(e.getEmployeeId());
            }

            catch(RuntimeException ex){

            }
        }


        return totalReports;
    }




}