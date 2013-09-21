package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;

/**
 * A resource on the Virtual File System (VFS). 
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface PathElement
{
	/**
	 * Get the parent container for this path.
	 * 
	 * @return the parent container for this path.
	 */
	public Container getParent();

	/**
	 * Get the name of this path, relative to its parent.
	 * 
	 * @return the name of this path
	 */
	public String getName();

	/**
	 * Get the full path, which should be unique on the file system.
	 * 
	 * @return the full path, which should be unique on the file system.
	 */
	public String getFullPath();

	/**
	 * Open a stream to this resource.
	 * 
	 * @return the input stream to this resource
	 * @throws IOException
	 *             if an error occurs opening the stream
	 * @throws UnsupportedOperationException
	 *             if the resource cannot be opened.
	 */
	public InputStream openStream() throws IOException,
			UnsupportedOperationException;
}