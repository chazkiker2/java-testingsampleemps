package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.models.Employee;
import com.lambdaschool.filtersampleemps.views.EmpNameCountJobs;

import java.util.List;


public interface EmployeeService
{
	List<Employee> findAllEmployees();

	Employee findEmployeeById(long employeeid);

	List<Employee> findEmployeeNameContaining(String subname);

	List<Employee> findEmployeeEmailContaining(String subemail);

	Employee save(Employee employee);

	List<EmpNameCountJobs> getEmpNameCountJobs();

	Employee update(
			Employee employee,
			long employeeid);

	void delete(long employeeid);
}

