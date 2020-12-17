package com.lambdaschool.filtersampleemps.config;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Spring uses filters to manage web traffic. Here we manually add a CORS (Cross-Origin Resource Sharing) filter to the chain.
 * Using the Order annotation, we tell Spring this is the most important filter. If this filter blocks a request,
 * don't do anything else. Just block the request.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter
		implements Filter {

	@Override
	public void doFilter(
			ServletRequest servletRequest,
			ServletResponse servletResponse,
			FilterChain filterChain
	)
	throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest  request  = (HttpServletRequest) servletRequest;

		response.setHeader("Access-Control-Allow-Origin", "*");

		response.setHeader("Access-Control-Allow-Methods", "*");

		response.setHeader("Access-Control-Allow-Headers", "*");


		response.setHeader("Access-Control-Max-Age", "3600");

		if (HttpMethod.OPTIONS.name()
				.equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
