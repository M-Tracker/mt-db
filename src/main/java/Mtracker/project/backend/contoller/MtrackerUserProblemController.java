//This controller handles the data of the user
//All encpoints are accessible only to authenticated users
//This controller is responsible for endpoints that help in:
/*
 * -fetching user details
 * -fetching mistake/problem details
 * -adding a new mistake
 * -deleting a mistake*/

package Mtracker.project.backend.contoller;

import Mtracker.project.backend.dto.AddProblemResponseDto;
import Mtracker.project.backend.dto.GetProblemDto;
import Mtracker.project.backend.dto.ProblemDetailsDto;
import Mtracker.project.backend.models.UserProblemsDataModel;
import Mtracker.project.backend.service.UserProblemDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import Mtracker.project.backend.models.AuthModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MtrackerUserProblemController {

    @Autowired
    private UserProblemDataService userProblemDataService;

    @Autowired
    private ObjectMapper objectMapper;
	
	@GetMapping("details")
	public ResponseEntity<AuthModel> getDetails(){

        AuthModel userDetails = userProblemDataService.userResponseDetails();
        System.out.println("The details has been called");
        return new ResponseEntity<>(userDetails,HttpStatus.OK);
    }

    @PostMapping("addMistake")
    public ResponseEntity<AddProblemResponseDto> addProblem(@RequestParam("problemData") String problemDetails, @RequestParam("image") MultipartFile imageFile){


        try {

            ProblemDetailsDto problemDetailsDto = objectMapper.readValue(problemDetails, ProblemDetailsDto.class);

            UserProblemsDataModel userProblemsDataModel = userProblemDataService.addProblem(problemDetailsDto, imageFile);

            AddProblemResponseDto addProblemResponseDto = new AddProblemResponseDto(userProblemsDataModel.getId(), "Mistake successfully saved", "200");
            return new ResponseEntity<AddProblemResponseDto>(addProblemResponseDto, HttpStatus.OK);

        } catch (IOException e) {

            AddProblemResponseDto addProblemResponseDto = new AddProblemResponseDto(null, "Error saving the mistake", "500");
            return new ResponseEntity<AddProblemResponseDto>(addProblemResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("getMistakeInfo")
    public ResponseEntity<ProblemDetailsDto> getProblemInfo(@RequestBody GetProblemDto getProblemDto){

        System.out.println("Id provided in the request: " + getProblemDto.getId());

        try {
            Optional<UserProblemsDataModel> problemData =  userProblemDataService.getProblem(getProblemDto.getId());
            String key = problemData.get().getImgPath();

            ProblemDetailsDto problemDetailsDto = new ProblemDetailsDto();

            problemDetailsDto.setLesson(problemData.get().getLesson());
            problemDetailsDto.setTitle(problemData.get().getTitle());
            problemDetailsDto.setCount(problemData.get().getCount());
            problemDetailsDto.setDescription(problemData.get().getDescription());
            problemDetailsDto.setCategory(problemData.get().getCategory());
            problemDetailsDto.setImgExists("true");

            return new ResponseEntity<ProblemDetailsDto>(problemDetailsDto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("exception: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("getMistakeImage")
    public ResponseEntity<InputStreamResource> getProblemImage(@RequestBody GetProblemDto getProblemDto){

        try {
            Optional<UserProblemsDataModel> problemData =  userProblemDataService.getProblem(getProblemDto.getId());
            String key = problemData.get().getImgPath();

            InputStreamResource fileStream = userProblemDataService.getFileFromS3(key);

            String extension = userProblemDataService.getFileExtension(key);
            MediaType mediaType = userProblemDataService.getMediaTypeFromExtension(extension);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, String.valueOf(mediaType));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(mediaType)
                    .body(fileStream);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
