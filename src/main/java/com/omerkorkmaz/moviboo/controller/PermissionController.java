/**
 * 
 */
package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.model.Consent;
import com.omerkorkmaz.moviboo.security.SecurityUtil;
import com.omerkorkmaz.moviboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends AdminBaseController
{
	private static final String viewPrefix = "permissions/";
	
	@Autowired protected UserService userService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Permissions";
	}
	
	@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public String listPermissions(Model model) {
		List<Consent> list = userService.getAllConsents();
		model.addAttribute("permissions",list);
		return viewPrefix+"permissions";
	}

}
