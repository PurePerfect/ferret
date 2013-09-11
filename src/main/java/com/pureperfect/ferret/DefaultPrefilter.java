package com.pureperfect.ferret;

import java.io.File;
import java.net.URL;

/**
 * A prefilter that only allows local files if they actually exist.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class DefaultPrefilter implements Prefilter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pureperfect.ferret.Prefilter#allow(java.io.File)
	 */
	@Override
	public boolean allow(File f)
	{
		return f.exists();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pureperfect.ferret.Prefilter#allow(java.net.URL)
	 */
	@Override
	public boolean allow(URL url)
	{
		if ("file".equals(url.getProtocol()))
		{
			return new File(url.getFile()).exists();
		}

		return true;
	}
}