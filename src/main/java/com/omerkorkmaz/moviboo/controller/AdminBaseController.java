package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.security.AuthenticatedCustomer;
import com.omerkorkmaz.moviboo.service.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;


public abstract class AdminBaseController
{
	protected final CLogger logger = CLogger.getLogger(getClass());
	
	@Autowired protected MessageSource messageSource;
	
	protected abstract String getHeaderTitle();
	
	public String getMessage(String code)
	{
		return messageSource.getMessage(code, null, null);
	}
	
	public String getMessage(String code, String defaultMsg)
	{
		return messageSource.getMessage(code, null, defaultMsg, null);
	}
	
	@ModelAttribute("authenticatedUser")
    public AuthenticatedCustomer authenticatedUser(@AuthenticationPrincipal AuthenticatedCustomer authenticatedCustomer)
    {
        return authenticatedCustomer;
    }
	
	public static AuthenticatedCustomer getCurrentUser() {

	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof AuthenticatedCustomer) {
	    	return ((AuthenticatedCustomer) principal);
	    }
	    // principal object is either null or represents anonymous user -
	    // neither of which our domain User object can represent - so return null
	    return null;
	}

	public static boolean isLoggedIn() {
	    return getCurrentUser() != null;
	}
}
