package com.omerkorkmaz.moviboo.security;

import com.omerkorkmaz.moviboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class PostAuthorizationFilter extends OncePerRequestFilter
{
	@Autowired
    UserService userService;
	
	protected String[] IGNORE_URIS = {
			"/assets/"
	};
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException
	{
		String uri = request.getRequestURI();
		if(!isIgnorableURI(uri)){
			String menu = MenuConfiguration.getMatchingMenu(uri);
			request.setAttribute("CURRENT_MENU", menu);
		}
		chain.doFilter(request, response);
	}
	
	private boolean isIgnorableURI(String uri)
	{
		for (String u : IGNORE_URIS) 
		{
			if(uri.startsWith(u))
			{
				return true;
			}
		}
		return false;
	}
}
