package com.lambdaschool.filtersampleemps.exceptions;


import com.lambdaschool.filtersampleemps.handlers.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class CustomErrorDetails
		extends DefaultErrorAttributes {

	private final HelperFunctions helper;

	@Autowired
	public CustomErrorDetails(HelperFunctions helper) {
		this.helper = helper;
	}

	@Override
	public Map<String, Object> getErrorAttributes(
			WebRequest webRequest,
			ErrorAttributeOptions options
	) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
		Map<String, Object> errorDetails = new LinkedHashMap<>();
		errorDetails.put("title",
				errorAttributes.get("error"));
		errorDetails.put("status",
				errorAttributes.get("status"));
		errorDetails.put("detail",
				errorAttributes.get("message"));
		errorDetails.put("timestamp",
				errorAttributes.get("timestamp"));
		errorDetails.put("developerMessage",
				"path: " + errorAttributes.get("path"));

		errorDetails.put("errors",
				helper.getConstraintViolation(this.getError(webRequest)));
		return errorDetails;
	}

}
