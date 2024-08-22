package Mtracker.project.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.repo.userAuthRepo;

@Service
public class customUserDetailService implements UserDetailsService{

	private userAuthRepo userRepository;
	
	@Autowired
	public customUserDetailService(userAuthRepo userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthModel user = userRepository.findByUsername(username);
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		//the last array that is being returned in this includes the granted authorities.
		//However, in this case we don't have the roles, so we just return an empty array list.
	}
	
	

}
