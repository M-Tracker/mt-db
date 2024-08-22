package Mtracker.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.repo.userAuthRepo;

@Service
public class ResponseService {
	
	@Autowired
	private userAuthRepo repo;
	
	
	public AuthModel userResponseDetails(String username) {
		AuthModel userDetails = repo.findByUsername(username);
		return userDetails;
	}
}
