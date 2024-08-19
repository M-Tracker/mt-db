package Mtracker.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import Mtracker.project.backend.models.authModel;
import Mtracker.project.backend.repo.userAuthRepo;

public class userAuthService {
	@Autowired
	private userAuthRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder pwencoder;
	
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
        newUser.setPassword(pwencoder.encode(password)); // Encrypt the password

        return userRepo.save(newUser);
    }
	
	public boolean loginUserByEmail(String email, String password) {
		authModel founduser = userRepo.findByEmail(email);
		
		if (founduser != null & pwencoder.encode(password)==founduser.getPassword()){
			return true;
		}
		
		return false;
	}
	
	public boolean loginUserByUserName(String username, String password) {
		authModel founduser = userRepo.findByUsername(username);
		
		if (founduser != null & pwencoder.encode(password)==founduser.getPassword()){
			return true;
		}
		
		return false;
	}
	
}
