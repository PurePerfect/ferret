package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * Implementation of a file in a web container.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplWebFile extends AbstractPathElement implements
		WebFile
{
	protected String path;

	protected ServletContext context;

	/**
	 * Create a new web file.
	 * 
	 * @param parent
	 *            the parent directory.
	 * @param context
	 *            the servlet context
	 * @param path
	 *            the path to this file
	 */
	public ImplWebFile(WebDirectory parent, ServletContext context, String path)
	{
		super(parent);
		this.context = context;
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream openStream() throws IOException
	{
		return context.getResourceAsStream(this.path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return this.path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullPath()
	{
		return this.path;
	}
}