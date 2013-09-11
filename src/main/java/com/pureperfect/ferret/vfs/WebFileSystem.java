package com.pureperfect.ferret.vfs;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import com.pureperfect.ferret.ScanException;

/**
 * An implementation of a lightweight Virtual File System (VFS) appropriate for
 * scanning in a WAR file / web application.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 * @see FileSystem
 */
public class WebFileSystem
{
	private static final WebFileSystem singleton = new WebFileSystem();

	private WebFileSystem()
	{
		// singleton
	}

	/**
	 * Get the appropriate virtual web resource.
	 * 
	 * @param parent
	 *            The parent directory
	 * 
	 * @param context
	 *            the servlet context for the application
	 * @param path
	 *            the path to the folder or file
	 * @return the appropriate virtual resource
	 */
	public PathElement getVirtualPath(WebDirectory parent,
			ServletContext context, String path)
	{
		// Directories
		if (isWebDirectory(context, path))
		{
			return new ImplWebDirectory(parent, context, path);
		}
		// Handle Archives as URLs
		else if (path.endsWith(".jar"))
		{
			try
			{
				final URL jar = context.getResource(path);

				return new ImplArchive(parent, jar);
			}
			catch (Throwable t)
			{
				throw new ScanException(t);
			}
		}
		// Class files
		else if (path.endsWith(".class"))
		{
			return new ImplWebClassFile(parent, context, path);
		}
		// Plain files
		else
		{
			return new ImplWebFile(parent, context, path);
		}
	}

	private static boolean isWebDirectory(ServletContext context, String path)
	{
		/*
		 * If it ends with a '/' its definitely a directory. However not ending
		 * with a slash is equally valid (e.g. /WEB-INF/lib). The only way to
		 * resolve this is to see if the resource actually exists, but does not
		 * have a stream since directories cannot be streamed. This is a rather
		 * resource intensive approach, but I don't have a quicker solution at
		 * the moment for reliably testing whether or not a give path is a
		 * directory.
		 */
		try
		{
			return path.endsWith("/")
					|| (context.getResource(path) != null && context
							.getResourceAsStream(path) == null);
		}
		catch (MalformedURLException e)
		{
			throw new ScanException(e);
		}
	}

	/**
	 * Get an instance of the file system.
	 * 
	 * @return an instance of the file system
	 */
	public static WebFileSystem getInstance()
	{
		return singleton;
	}
}