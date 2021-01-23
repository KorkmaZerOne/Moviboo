/**
 * 
 */
package com.omerkorkmaz.moviboo.exception;


public class MovibooException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public MovibooException()
	{
		super();
	}

	public MovibooException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MovibooException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MovibooException(String message)
	{
		super(message);
	}

	public MovibooException(Throwable cause)
	{
		super(cause);
	}
	
}
