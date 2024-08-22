package Mtracker.project.backend.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Mtracker.project.backend.dto.LoginDto;
import Mtracker.project.backend.dto.RegisterDto;
import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.service.userAuthService;

@RestController
@RequestMapping("/api/auth")
public class MtrackerAuthController {
	
	private userAuthService userService;
	

	@Autowired
	public MtrackerAuthController(userAuthService userService) {
		this.userService = userService;
	}

	@CrossOrigin
	@PostMapping("register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerdto) {
        try {
            AuthModel newUser = userService.registerUser(
                registerdto.getUsername(),
                registerdto.getEmail(),
                registerdto.getPassword(),
                registerdto.getFirstName(),
                registerdto.getLastName()
            );
            
            
            
            
        } catch (IllegalStateException e) {
            return new ResponseEntity<>((e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
        String jwt = userService.loginUserByUserName(registerdto.getUsername(), registerdto.getPassword());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
        //In next project set roles here for users and admins.
    }
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody LoginDto logindto) {
		
		String jwt = userService.loginUserByUserName(logindto.getUsername(), logindto.getPassword());
		
		if (jwt == null) {
			return new ResponseEntity<>("Authenication Failed", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		}
		
		
	}
	
	
	
}
