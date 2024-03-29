package com.omerkorkmaz.moviboo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteErrorController extends SiteBaseController
{
	private static final String viewPrefix = "error/";
	
	@Override
	protected String getHeaderTitle()
	{
		return "Error";
	}
	
	@RequestMapping("/403")
	public String accessDenied()
	{
		return viewPrefix+"accessDenied";
	}

}
