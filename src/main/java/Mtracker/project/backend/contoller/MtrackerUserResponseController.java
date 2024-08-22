package Mtracker.project.backend.contoller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.service.ResponseService;

@RestController
@RequestMapping
public class MtrackerUserResponseController {
	
	@Autowired
	private ResponseService responseService;
	
	@GetMapping("details")
	public ResponseEntity<?> getDetails(){
        var detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        AuthModel userDetails = responseService.userResponseDetails(detail.getUsername());
        return new ResponseEntity<>(userDetails,HttpStatus.OK);
    }
}
