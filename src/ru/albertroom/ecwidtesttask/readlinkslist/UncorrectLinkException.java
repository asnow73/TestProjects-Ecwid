package ru.albertroom.ecwidtesttask.readlinkslist;

public class UncorrectLinkException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UncorrectLinkException(String message)
	{
		super(message);
	}
}