package com.lambdaschool.filtersampleemps.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "employeetitles")
@IdClass(EmployeeTitlesId.class)
public class EmployeeTitles
		extends Auditable
		implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "employeeid")
	@JsonIgnoreProperties(value = "jobnames",
	                      allowSetters = true)
	private Employee emp;

	@Id
	@ManyToOne
	@JoinColumn(name = "jobtitleid")
	@JsonIgnoreProperties(value = "empnames",
	                      allowSetters = true)
	private JobTitle jobname;

	@Column(nullable = false)
	private String manager;

	public EmployeeTitles() {}

	public EmployeeTitles(
			Employee emp,
			JobTitle jobname,
			String manager
	) {
		this.emp     = emp;
		this.jobname = jobname;
		this.manager = manager;
	}

	@Override
	public int hashCode() {
		return 34;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (!(o instanceof EmployeeTitles)) {
			return false;
		}
		EmployeeTitles that           = (EmployeeTitles) o;
		long           thisEmpId      = (this.getEmp() == null)
		                                ? 0
		                                : this.getEmp()
				                                .getEmployeeid();
		long           thatEmpId      = (that.getEmp() == null)
		                                ? 0
		                                : that.getEmp()
				                                .getEmployeeid();
		long           thisJobtitleId = (this.getJobname() == null)
		                                ? 0
		                                : this.getJobname()
				                                .getJobtitleid();
		long           thatJobtitleId = (that.getJobname() == null)
		                                ? 0
		                                : that.getJobname()
				                                .getJobtitleid();
		return (thisEmpId == thatEmpId) && (thisJobtitleId == thatJobtitleId);
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public JobTitle getJobname() {
		return jobname;
	}

	public void setJobname(JobTitle jobname) {
		this.jobname = jobname;
	}

}
