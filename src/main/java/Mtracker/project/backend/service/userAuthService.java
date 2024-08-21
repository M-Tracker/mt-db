package Mtracker.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Mtracker.project.backend.models.authModel;
import Mtracker.project.backend.repo.userAuthRepo;

@Service
public class userAuthService {
	@Autowired
	private userAuthRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public authModel registerUser(String username, String email, String password, String firstName, String lastName) {
        // Check if email or username already exists
        if (userRepo.findByEmail(email) != null) {
            throw new IllegalStateException("Email already in use");
        }
        if (userRepo.findByUsername(username) != null) {
            throw new IllegalStateException("Username already taken");
        }

        authModel newUser = new authModel();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(passwordEncoder.encode(password)); // Encrypt the password

        return userRepo.save(newUser);
    }
	
	public boolean loginUserByEmail(String email, String password) {
		authModel founduser = userRepo.findByEmail(email);
		
		if (founduser != null & passwordEncoder.encode(password)==founduser.getPassword()){
			return true;
		}
		
		return false;
	}
	
	public boolean loginUserByUserName(String username, String password) {
		authModel founduser = userRepo.findByUsername(username);
		
		if (founduser != null & passwordEncoder.encode(password)==founduser.getPassword()){
			return true;
		}
		
		return false;
	}
	
}
