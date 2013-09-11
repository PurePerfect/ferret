package com.pureperfect.ferret;

/**
 * Indicates there was a problem with the scanner.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class ScanException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * The underlying cause.
	 * 
	 * @param cause
	 *            The underlying cause
	 */
	public ScanException(final Throwable cause)
	{
		super(cause);
	}
}
