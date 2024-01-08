package com.employeeManagement.RegistrationService.service;

import com.employeeManagement.RegistrationService.model.MyUserDetails;
import com.employeeManagement.RegistrationService.model.User;
import com.employeeManagement.RegistrationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Service @Slf4j
public class JwtUserDetailsService implements UserDetailsService {
	

	private UserRepository userRepository;
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public MyUserDetails loadUserByUsername(String userName) {


		Optional<User> optionalUser = userRepository.findByEmail(userName);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		User user = optionalUser.get();
		log.info("User found");
		log.info("user successfully located");

		
		return new MyUserDetails(user);

	}

}