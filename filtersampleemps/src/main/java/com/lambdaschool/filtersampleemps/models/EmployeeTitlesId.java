package com.lambdaschool.filtersampleemps.models;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class EmployeeTitlesId implements Serializable {
	private long emp;
	private long jobname;
	public EmployeeTitlesId() {}

	public long getEmp() {
		return emp;
	}

	public void setEmp(long emp) {
		this.emp = emp;
	}

	public long getJobname() {
		return jobname;
	}

	public void setJobname(long jobname) {
		this.jobname = jobname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		EmployeeTitlesId that = (EmployeeTitlesId) o;
		return getEmp() == that.getEmp() && getJobname() == that.getJobname();
	}

	@Override
	public int hashCode() {
		return 34;
	}

}
