package com.pureperfect.ferret;

import java.io.File;
import java.net.URL;

/**
 * A pre-filter determines whether or not a given resource should be allowed to
 * be added to the scan path. See {@link Scanner#setPrefilter(Prefilter)}.
 * 
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 * @see Scanner
 */
public interface Prefilter
{
	/**
	 * Determine whether or not the given file should be allowed.
	 * 
	 * @param f
	 *            the file to check
	 * @return whether or not the given file should be allowed.
	 */
	public boolean allow(File f);

	/**
	 * Determine whether or not the given url should be allowed.
	 * 
	 * @param url
	 *            the url to check
	 * @return whether or not the given url should be allowed.
	 */
	public boolean allow(URL url);
}