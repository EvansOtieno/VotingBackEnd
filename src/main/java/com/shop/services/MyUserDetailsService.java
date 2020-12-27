package com.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.models.Users;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserService us;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return us.findByEmail(username);
	}

}
