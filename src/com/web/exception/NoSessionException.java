package com.web.exception;

public class NoSessionException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSessionException(){
	
	}
	
	public NoSessionException( String message )	{
		super(message);
	}
}
