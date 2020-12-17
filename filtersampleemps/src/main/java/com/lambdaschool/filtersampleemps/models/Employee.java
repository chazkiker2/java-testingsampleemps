package com.lambdaschool.filtersampleemps.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "employees")
@JsonIgnoreProperties(value = "hasvalueforsalary")
public class Employee
		extends Auditable {

	@Transient
	@JsonIgnore
	public  boolean hasvalueforsalary = false;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long    employeeid;
	@Column(nullable = false,
	        unique = true)
	private String  name;
	@DecimalMax(value = "250000.0",
	            message = "Salary cannot exceed 250,000")
	@DecimalMin(value = "0.0",
	            message = "Salary must be a positive number")
	private double  salary;

	@OneToMany(mappedBy = "emp",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JsonIgnoreProperties(value = "emp",
	                      allowSetters = true)
	private Set<EmployeeTitles> jobnames = new HashSet<>();

	@OneToMany(mappedBy = "employee",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JsonIgnoreProperties(value = "employee")
	private List<Email> emails = new ArrayList<>();

	public Employee() {}

	public Employee(
			String name,
			@DecimalMax(value = "250000.0",
			            message = "Salary cannot exceed 250,000") @DecimalMin(value = "0.0",
			                                                                  message = "Salary must be a positive number") double salary
	) {
		setName(name);
		setSalary(salary);
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary       = salary;
		hasvalueforsalary = true;
	}

	public Set<EmployeeTitles> getJobnames() {
		return jobnames;
	}

	public void setJobnames(Set<EmployeeTitles> jobnames) {
		this.jobnames = jobnames;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	@Override
	public String toString() {
		return "Employee{" + "employeeid=" + employeeid + ", name='" + name + '\'' + ", salary=" + salary + ", jobnames=" +
		       jobnames + ", emails=" + emails + '}';
	}

}
