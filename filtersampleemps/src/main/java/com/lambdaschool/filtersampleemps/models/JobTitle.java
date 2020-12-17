package com.lambdaschool.filtersampleemps.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="jobtitles")
public class JobTitle extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobtitleid;

	@Column
	private String title;

	@OneToMany(mappedBy="jobname", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value="jobname", allowSetters = true)
	private Set<EmployeeTitles> empnames = new HashSet<>();

	public JobTitle() {}

	public JobTitle(String title) {
		this.title = title;
	}

	public long getJobtitleid() {
		return jobtitleid;
	}

	public void setJobtitleid(long jobtitleid) {
		this.jobtitleid = jobtitleid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<EmployeeTitles> getEmpnames() {
		return empnames;
	}

	public void setEmpnames(Set<EmployeeTitles> empnames) {
		this.empnames = empnames;
	}

	@Override
	public String toString() {
		return "JobTitle{" + "jobtitleid=" + jobtitleid + ", title='" + title + '\'' + ", empnames=" + empnames + '}';
	}

}
