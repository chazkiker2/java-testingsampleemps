package com.lambdaschool.filtersampleemps.controllers;


import com.lambdaschool.filtersampleemps.models.JobTitle;
import com.lambdaschool.filtersampleemps.services.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jobtitles")
public class JobTitleController
{

	private final JobTitleService jtService;

	@Autowired
	public JobTitleController(JobTitleService jtService) {
		this.jtService = jtService;
	}

	@PutMapping(value = "/jobtitle/{jobtitleid}",
	            consumes = {"application/json"})
	public ResponseEntity<?> putUpdateJobTitle(
			@PathVariable
					long jobtitleid,
			@Valid
			@RequestBody
					JobTitle newJT)
	{
		newJT = jtService.update(jobtitleid,
				newJT);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PatchMapping(value = "/jobtitle/{jobtitlesid}",
	              consumes = {"application/json"})
	public ResponseEntity<?> patchUpdateJobTitle(
			@PathVariable
					long jobtitlesid,
			@RequestBody
					JobTitle newJT)
	{
		newJT = jtService.update(jobtitlesid,
				newJT);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

