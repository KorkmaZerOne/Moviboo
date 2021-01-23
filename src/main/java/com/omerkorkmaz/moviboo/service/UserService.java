package com.omerkorkmaz.moviboo.service;

import com.omerkorkmaz.moviboo.data.ConsentRepository;
import com.omerkorkmaz.moviboo.data.RoleRepository;
import com.omerkorkmaz.moviboo.data.UserRepository;
import com.omerkorkmaz.moviboo.exception.MovibooException;
import com.omerkorkmaz.moviboo.model.Consent;
import com.omerkorkmaz.moviboo.model.Role;
import com.omerkorkmaz.moviboo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService
{
	@Autowired
	UserRepository userRepository;
	@Autowired
	ConsentRepository consentRepository;
	@Autowired
	RoleRepository roleRepository;
	
	public User findUserByEmail(String email)
	{
		return userRepository.findUserByMail(email);
	}
	
	public String resetPassword(String email)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new MovibooException("Invalid email address");
		}
		String uuid = UUID.randomUUID().toString();
		user.setPasswordResetToken(uuid);
		return uuid;
	}

	public void updatePassword(String email, String token, String password)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new MovibooException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
			throw new MovibooException("Invalid password reset token");
		}
		user.setPassword(password);
		user.setPasswordResetToken(null);
	}

	public boolean verifyPasswordResetToken(String email, String token)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new MovibooException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
			return false;
		}
		return true;
	}
	
	public List<Consent> getAllConsents() {
		return consentRepository.findAll();
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleByName(String roleName)
	{
		return roleRepository.findByName(roleName);
	}
	
	public Role createRole(Role role) {
		Role roleByName = getRoleByName(role.getName());
		if(roleByName != null){
			throw new MovibooException("Role "+role.getName()+" already exist");
		}
		List<Consent> persistedConsents = new ArrayList<>();
		List<Consent> consents = role.getConsents();
		if(consents != null){
			for (Consent consent : consents) {
				if(consent.getName() != null)
				{
					persistedConsents.add(consentRepository.getOne(consent.getConsentId()));
				}
			}
		}
		
		role.setConsents(persistedConsents);
		return roleRepository.save(role);
	}
	
	public Role updateRole(Role role)
	{
		Role persistedRole = getRoleById(role.getUserRoleId());
		if(persistedRole == null){
			throw new MovibooException("Role "+role.getUserRoleId()+" doesn't exist");
		}
		persistedRole.setDescription(role.getDescription());
		List<Consent> updatedConsents = new ArrayList<>();
		List<Consent> consents = role.getConsents();
		if(consents != null){
			for (Consent consent : consents) {
				if(consent.getName() != null)
				{
					updatedConsents.add(consentRepository.getOne(consent.getConsentId()));
				}
			}
		}
		persistedRole.setConsents(updatedConsents);
		return roleRepository.save(persistedRole);
	}
	
	public Role getRoleById(Integer id) {
		return roleRepository.getOne(id);
	}
	
	public User getUserById(Integer id)
	{
		return userRepository.getOne(id);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User createUser(User user)
	{
		User userByEmail = findUserByEmail(user.getEmail());
		if(userByEmail != null){
			throw new MovibooException("Email "+user.getEmail()+" already in use");
		}
		List<Role> persistedRoles = new ArrayList<>();
		List<Role> roles = user.getRoles();
		if(roles != null){
			for (Role role : roles) {
				if(role.getName() != null)
				{
					persistedRoles.add(roleRepository.getOne(role.getUserRoleId()));
				}
			}
		}
		user.setRoles(persistedRoles);
		
		return userRepository.save(user);
	}
	
	public User updateUser(User user)
	{
		User persistedUser = getUserById(user.getUserId());
		if(persistedUser == null){
			throw new MovibooException("User "+user.getUserId()+" doesn't exist");
		}
		
		List<Role> updatedRoles = new ArrayList<>();
		List<Role> roles = user.getRoles();
		if(roles != null){
			for (Role role : roles) {
				if(role.getName() != null)
				{
					updatedRoles.add(roleRepository.getOne(role.getUserRoleId()));
				}
			}
		}
		persistedUser.setRoles(updatedRoles);
		return userRepository.save(persistedUser);
	}

}
