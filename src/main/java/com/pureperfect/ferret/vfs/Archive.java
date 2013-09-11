package com.pureperfect.ferret.vfs;

import java.net.URL;

/**
 * Represents an archive on the virtual file system; usually a JAR or ZIP file.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface Archive extends Container
{
	/**
	 * Get the URL for the underlying resource.
	 * 
	 * @return the underlying archive
	 */
	public abstract URL getArchive();
}