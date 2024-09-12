package Mtracker.project.backend.dto;

public class AddProblemResponseDto {
    private Long id;
    private String message;
    private String httpStatus;

    public AddProblemResponseDto(Long id, String message, String httpStatus) {
        this.id = id;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
}
