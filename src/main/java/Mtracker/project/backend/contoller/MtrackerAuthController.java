package Mtracker.project.backend.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Mtracker.project.backend.dto.AuthResponseDto;
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
	public ResponseEntity<AuthResponseDto> registerUser(@RequestBody RegisterDto registerdto) {
		String jwt="";
		String message = "Eithor username or email already exists";
		String httpStatus = "400";
        try {
            AuthModel newUser = userService.registerUser(
                registerdto.getUsername(),
                registerdto.getEmail(),
                registerdto.getPassword(),
                registerdto.getFirstName(),
                registerdto.getLastName()
            );
            
        } catch (IllegalStateException e) {
        	AuthResponseDto authResponseDto = new AuthResponseDto(jwt, message,httpStatus);
            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.BAD_REQUEST);
        }
        
        jwt = userService.loginUserByUserName(registerdto.getUsername(), registerdto.getPassword());
        message = "User Successfully Created";
        httpStatus = "200";
        AuthResponseDto authResponseDto = new AuthResponseDto(jwt, message,httpStatus);
        return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);
        //In next project set roles here for users and admins.
    }
	
	@PostMapping("login")
	public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto logindto) {
		
		String jwt = userService.loginUserByUserName(logindto.getUsername(), logindto.getPassword());
		String message = "Username or password not correct";
		String httpStatus = "400";
		
		if (jwt == null) {
			AuthResponseDto authResponseDto = new AuthResponseDto(jwt, message,httpStatus);
			return new ResponseEntity<>(authResponseDto, HttpStatus.BAD_REQUEST);
		}else {
			message = "User logged in Successfully";
			httpStatus = "200";
			AuthResponseDto authResponseDto = new AuthResponseDto(jwt, message,httpStatus);
			return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
		}
			
	}
	
}
