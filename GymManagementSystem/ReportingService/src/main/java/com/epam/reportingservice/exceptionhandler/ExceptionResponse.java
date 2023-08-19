package com.epam.reportingservice.exceptionhandler;

public class ExceptionResponse {
	private String timeStamp;
	private String status;
	private String error;
	private String path;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timeStamp=" + timeStamp + ", status=" + status + ", error=" + error + ", path="
				+ path + "]";
	}

	public ExceptionResponse(String timeStamp, String status, String error, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	public ExceptionResponse() {

	}

}
