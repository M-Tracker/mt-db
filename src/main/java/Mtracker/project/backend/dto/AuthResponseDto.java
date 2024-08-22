package Mtracker.project.backend.dto;

public class AuthResponseDto {
	private String jwt;
	private String message;
	private String httpStatus;
	
	
	public AuthResponseDto(String jwt, String message, String httpStatus) {
		super();
		this.jwt = jwt;
		this.message = message;
		this.httpStatus = httpStatus;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
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
