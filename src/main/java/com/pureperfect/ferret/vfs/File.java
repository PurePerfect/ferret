package com.pureperfect.ferret.vfs;

/**
 * Represents a file on the virtual file system.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface File extends PathElement
{
	/**
	 * Get the underlying file.
	 * 
	 * @return the underlying file
	 */
	public abstract java.io.File getFile();
}