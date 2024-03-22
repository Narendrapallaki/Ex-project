package com.example.ExceptionHandler;

public class CustomException  extends RuntimeException{
	
	private CustomException(String message)
	{
		super(message);
	}

}
