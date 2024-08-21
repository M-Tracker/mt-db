package Mtracker.project.backend.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Mtracker.project.backend.dto.registerDto;
import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.service.userAuthService;

@RestController
@RequestMapping("/api/auth")
public class MtrackerController {
	
	private userAuthService userService;

	@Autowired
	public MtrackerController(userAuthService userService) {
		this.userService = userService;
	}

	@CrossOrigin
	@PostMapping("register")
	public ResponseEntity<?> registerUser(@RequestBody registerDto registerdto) {
        try {
            AuthModel newUser = userService.registerUser(
                registerdto.getUsername(),
                registerdto.getEmail(),
                registerdto.getPassword(),
                registerdto.getFirstName(),
                registerdto.getLastName()
            );
            
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
            //In next project set roles here for users and admins.
            
            
        } catch (IllegalStateException e) {
            return new ResponseEntity<>((e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
	
	@GetMapping("/test")
	public void testing() {
		System.out.println("Hello, World!");
	}
}
