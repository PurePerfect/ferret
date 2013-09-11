package com.pureperfect.ferret;

import java.util.Set;

import javax.servlet.ServletContext;

import com.pureperfect.ferret.vfs.WebFileSystem;

/**
 * A Classpath scanner appropriate for scanning in a Java web application.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class WebScanner extends Scanner
{
	/**
	 * Create a new WebScanner.
	 */
	public WebScanner()
	{
		super();
	}

	/**
	 * Create a new WebScanner with the given pre-filter.
	 * 
	 * @param prefilter
	 *            the pre-filter to use
	 */
	public WebScanner(Prefilter prefilter)
	{
		super(prefilter);
	}

	/**
	 * Add private resources in WEB-INF to the scan path, but skip WEB-INF/lib
	 * and WEB-INF/classes.
	 * 
	 * @param context
	 *            the servlet context
	 */
	public void addPrivateResources(final ServletContext context)
	{
		Set<String> paths = context.getResourcePaths("/WEB-INF/");

		for (String path : paths)
		{
			// Skip /WEB-INF/lib and /WEB-INF/classes
			if (!("/WEB-INF/lib/".equals(path) || "/WEB-INF/classes/"
					.equals(path)))
			{
				this.add(context, path);
			}
		}
	}

	/**
	 * Add all files outside of WEB-INF/ to the scan path.
	 * 
	 * @param context
	 *            the servlet context
	 */
	public void addPublicResources(final ServletContext context)
	{
		Set<String> paths = context.getResourcePaths("/");

		for (String path : paths)
		{
			// Skip /WEB-INF/
			if (!("/WEB-INF/".equals(path)))
			{
				add(context, path);
			}
		}
	}

	/**
	 * Add a file in the WAR to the scan path.
	 * 
	 * @param context
	 *            The servlet context
	 * @param path
	 *            The path to the file
	 */
	public void add(ServletContext context, String path)
	{
		super.add(WebFileSystem.getInstance().getVirtualPath(null, context,
				path));
	}
}