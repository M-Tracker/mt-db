package Mtracker.project.backend.service;

import Mtracker.project.backend.dto.ProblemDetailsDto;
import Mtracker.project.backend.models.AuthModel;
import Mtracker.project.backend.repo.userAuthRepo;
import Mtracker.project.backend.models.UserProblemsDataModel;
import Mtracker.project.backend.repo.UserProblemDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserProblemDataService {

    @Value("${project.image}")
    private String onPremesisInitialPath;

    @Value("${aws.image}")
    private String awsInitialPath;

    @Value("${project.migrated}")
    private boolean projectMigrated;

    @Value("${aws.s3.imagebucket}")
    private String bucketName;

    @Autowired
    private userAuthRepo authRepo;

    @Autowired
    private UserProblemDataRepo userProblemDataRepo;

    @Autowired
    private S3Service s3Service;

    public AuthModel userResponseDetails() {
        return getCurrentUser();
    }

    public UserProblemsDataModel addProblem(ProblemDetailsDto problemDetailsDto, MultipartFile imgFile) throws IOException {

        AuthModel currentUser = getCurrentUser();
        String userId = String.valueOf(currentUser.getId());

        String originalFileName = imgFile.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName;

        if(problemDetailsDto.isImgExists() == "true"){
            uniqueFileName = currentUser.getUsername() + "-" + problemDetailsDto.getCategory() + "-" + problemDetailsDto.getTitle() + "." + fileExtension;
            try {
                s3Service.updateFile(uniqueFileName, imgFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            uniqueFileName = "-";
        }

        UserProblemsDataModel userProblemsData = new UserProblemsDataModel(problemDetailsDto.getTitle(), problemDetailsDto.getCategory(), problemDetailsDto.getCount(), problemDetailsDto.getDescription(), problemDetailsDto.getLesson(), currentUser, uniqueFileName);
        userProblemDataRepo.save(userProblemsData);

        return userProblemsData;
    }

    public Optional<UserProblemsDataModel> getProblem(Long id){

        AuthModel currentUser = getCurrentUser();

        Integer user_id = currentUser.getId();

        Optional<UserProblemsDataModel> userProblem = userProblemDataRepo.findById(id);

        if (userProblem.isPresent()){
            if(Objects.equals(userProblem.get().getUser().getId(), user_id)){
                return userProblem;
            }
        }

        return Optional.empty();
    }

    public InputStreamResource getFileFromS3(String key){

        return s3Service.downloadFile(key, bucketName);
    }

    private AuthModel getCurrentUser() {


        System.out.println("the getcurrentUser has been called");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("If condition in getcurrentUser is satisfied and username: " + userDetails.getUsername());
            return authRepo.findByUsername(userDetails.getUsername());
        }else{
            return null;
        }

    }

    public String getFileExtension(String originalFileName){
        if ((originalFileName == null || originalFileName.lastIndexOf(".") == -1)) {
            return ""; // No extension found
        }else{
            return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }
    }

    public MediaType getMediaTypeFromExtension(String extension) {
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "webp":
                return MediaType.valueOf("image/webp");
            case "svg":
                return MediaType.valueOf("image/svg+xml");
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // Default for unknown types
        }
    }

    public String deleteProblem(Long id){

        Optional<UserProblemsDataModel> userProblem = userProblemDataRepo.findById(id);

        if (userProblem.isPresent()) {
            try {
                UserProblemsDataModel actualProblemData = userProblem.get();
                String key = actualProblemData.getImgPath();

                s3Service.deleteFile(key);
                userProblemDataRepo.delete(actualProblemData);

                return "Problem deleted successfully";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
