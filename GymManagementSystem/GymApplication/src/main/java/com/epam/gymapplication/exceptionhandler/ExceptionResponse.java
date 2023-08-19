package com.epam.gymapplication.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
	private String timeStamp;
	private String status;
	private String error;
	private String path;
}
