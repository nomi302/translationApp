package com.translation.assessment.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
	private final String apiKey;
	private final Environment env;

	public ApiKeyAuthFilter(String apiKey,Environment env) {
		this.apiKey = apiKey;
		 this.env = env;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Skip authentication in test environment
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            filterChain.doFilter(request, response);
            return;
        }

		// Skip API key check for Swagger/OpenAPI paths
		String path = request.getRequestURI();
		if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
			filterChain.doFilter(request, response);
			return;
		}

		String requestApiKey = request.getHeader("X-API-Key");

		if (apiKey.equals(requestApiKey)) {
			// Create authenticated token
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("api-user", null,
					AuthorityUtils.createAuthorityList("ROLE_API"));

			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid API Key");
		}
	}
}
