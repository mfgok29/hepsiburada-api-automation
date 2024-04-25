package models.response;

import lombok.Data;

@Data
public class ErrorResponse{

	private int code;

	private String type;

	private String message;
}