package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.exceptions.ResourceNotFoundException;
import com.lambdaschool.filtersampleemps.models.JobTitle;
import com.lambdaschool.filtersampleemps.repos.EmployeeRepo;
import com.lambdaschool.filtersampleemps.repos.JobTitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;


@Transactional
@Service(value = "jobtitleService") // needed to name this implementation as the service to use
public class JobTitleServiceImpl
		implements JobTitleService {

	private final EmployeeRepo employeeRepo;
	private final JobTitleRepo jobTitleRepo;

	@Autowired
	public JobTitleServiceImpl(
			EmployeeRepo employeeRepo,
			JobTitleRepo jobTitleRepo
	) {
		this.employeeRepo = employeeRepo;
		this.jobTitleRepo = jobTitleRepo;
	}

	@Transactional
	@Override
	public JobTitle update(
			long id,
			JobTitle jt
	) {
		if (jt.getTitle() == null) {
			throw new ResourceNotFoundException("No Title found to update!");
		}

		if (jt.getEmpnames()
				    .size() > 0) {
			throw new EntityExistsException("Employees are not updated through Job Titles");
		}

		jobTitleRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job Title id " + id + " not found!"));

		jobTitleRepo.updateJobTitle("SYSTEM", id, jt.getTitle());
		return jobTitleRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job Title id " + id + " not found!"));
	}

}
