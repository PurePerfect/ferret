package com.pureperfect.ferret;

import com.pureperfect.ferret.vfs.PathElement;

/**
 * Implement this interface to scan.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface ScanFilter
{
	/**
	 * Define search criteria for scanning. If this method returns true, the
	 * visited resource will be added to the result set.
	 * 
	 * @param resource
	 *            The current resource being visited.
	 * 
	 * @return whether or not the resource should be included in the scan
	 *         results.
	 */
	public boolean accept(PathElement resource);
}
