package edu.tongji.webgis.security;

import edu.tongji.webgis.model.User;
import edu.tongji.webgis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailsService implements UserDetailsService {

	@Autowired
	UserService as;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User a = (User) as.findUserByUsername(username);
		if (a == null)
			throw new UsernameNotFoundException("The user could not be found.");
		return new AccountUserDetails(a);
	}

}
