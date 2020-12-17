package com.lambdaschool.filtersampleemps.services;


import com.lambdaschool.filtersampleemps.models.JobTitle;


public interface JobTitleService {

	JobTitle update(
			long id,
			JobTitle jt
	);

}
