package Mtracker.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.repo.userAuthRepo;

@Service
public class userAuthService {
	@Autowired
	private userAuthRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private customUserDetailService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthModel registerUser(String username, String email, String password, String firstName, String lastName) {
        // Check if email or username already exists
        if (userRepo.findByEmail(email) != null) {
            throw new IllegalStateException("Email already in use");
        }
        if (userRepo.findByUsername(username) != null) {
            throw new IllegalStateException("Username already taken");
        }
        
        System.out.println("username: " + username);
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);
        System.out.println("password: " + password);
        System.out.println("email: " + email);

        AuthModel newUser = new AuthModel();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(passwordEncoder.encode(password)); // Encrypt the password

        System.out.println("Did something that is expected");
        return userRepo.save(newUser);
    }
	
	
	
	public String loginUserByUserName(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			
			return null;
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		
		return jwt;
	}
	
}
