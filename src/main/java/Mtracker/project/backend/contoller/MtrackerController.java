package Mtracker.project.backend.contoller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Mtracker.project.backend.models.authModel;
import Mtracker.project.backend.service.userAuthService;

@RestController
@RequestMapping("/api/auth")
public class MtrackerController {
	
	@Autowired
	private userAuthService userService;
	
	@CrossOrigin
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody authModel authRequest) {
        try {
            authModel newUser = userService.registerUser(
                authRequest.getUsername(),
                authRequest.getEmail(),
                authRequest.getPassword(),
                authRequest.getFirstName(),
                authRequest.getLastName()
            );
            
            System.out.println("Did something that is expected");
            return ResponseEntity.ok("User registered successfully");
            
            
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	@GetMapping("/test")
	public void testing() {
		System.out.println("Hello, World!");
	}
}
