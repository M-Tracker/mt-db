package Mtracker.project.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import Mtracker.project.backend.models.authModel;
import Mtracker.project.backend.repo.userAuthRepo;

public class customUserDetailService implements UserDetailsService{

	private userAuthRepo repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		authModel user = repository.findByUsername(username);
		return new ;
	}

}
