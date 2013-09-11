package com.pureperfect.ferret;

import java.io.File;
import java.io.FileFilter;

/**
 * ResourceFilter to select only jar files.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class JARFileFilter implements FileFilter
{
	private static final JARFileFilter singleton = new JARFileFilter();

	/**
	 * Get a singleton instance.
	 * 
	 * @return a singleton instance.
	 */
	public static final JARFileFilter defaultInstance()
	{
		return JARFileFilter.singleton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(final File pathname)
	{
		return pathname.getName().endsWith(".jar");
	}
}