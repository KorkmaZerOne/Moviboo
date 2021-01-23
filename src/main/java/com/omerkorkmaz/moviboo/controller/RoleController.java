package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.model.Consent;
import com.omerkorkmaz.moviboo.model.Role;
import com.omerkorkmaz.moviboo.security.SecurityUtil;
import com.omerkorkmaz.moviboo.service.UserService;
import com.omerkorkmaz.moviboo.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Secured(SecurityUtil.MANAGE_ROLES)
public class RoleController extends AdminBaseController
{
	private static final String viewPrefix = "roles/";
	
	@Autowired protected UserService userService;
	@Autowired private RoleValidator roleValidator;

	@Override
	protected String getHeaderTitle()
	{
		return "Manage Roles";
	}
	
	@ModelAttribute("permissionsList")
	public List<Consent> consentsList(){
		return userService.getAllConsents();
	}
	
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public String listRoles(Model model) {
		List<Role> list = userService.getAllRoles();
		model.addAttribute("roles",list);
		return viewPrefix+"roles";
	}
	
	@RequestMapping(value="/roles/new", method=RequestMethod.GET)
	public String createRoleForm(Model model) {
		Role role = new Role();
		model.addAttribute("role",role);
		//model.addAttribute("permissionsList",securityService.getAllPermissions());		
		
		return viewPrefix+"create_role";
	}

	@RequestMapping(value="/roles", method=RequestMethod.POST)
	public String createRole(@Valid @ModelAttribute("role") Role role, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		roleValidator.validate(role, result);
		if(result.hasErrors()){
			return viewPrefix+"create_role";
		}
		Role persistedRole = userService.createRole(role);
		logger.debug("Created new role with id : {} and name : {}", persistedRole.getUserRoleId(), persistedRole.getName());
		redirectAttributes.addFlashAttribute("info", "Role created successfully");
		return "redirect:/roles";
	}
	
	
	@RequestMapping(value="/roles/{id}", method=RequestMethod.GET)
	public String editRoleForm(@PathVariable Integer id, Model model) {
		Role role = userService.getRoleById(id);
		Map<Integer, Consent> assignedPermissionMap = new HashMap<>();
		List<Consent> consents = role.getConsents();
		for (Consent consent : consents)
		{
			assignedPermissionMap.put(consent.getConsentId(), consent);
		}
		List<Consent> roleConsents = new ArrayList<>();
		List<Consent> allConsents = userService.getAllConsents();
		for (Consent consent : allConsents)
		{
			if(assignedPermissionMap.containsKey(consent.getConsentId())){
				roleConsents.add(consent);
			} else {
				roleConsents.add(null);
			}
		}
		role.setConsents(roleConsents);
		model.addAttribute("role",role);
		//model.addAttribute("permissionsList",allPermissions);		
		return viewPrefix+"edit_role";
	}
	
	@RequestMapping(value="/roles/{id}", method=RequestMethod.POST)
	public String updateRole(@ModelAttribute("role") Role role, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {		
		Role persistedRole = userService.updateRole(role);
		logger.debug("Updated role with id : {} and name : {}", persistedRole.getUserRoleId(), persistedRole.getName());
		redirectAttributes.addFlashAttribute("info", "Role updated successfully");
		return "redirect:/roles";
	}
	
}
