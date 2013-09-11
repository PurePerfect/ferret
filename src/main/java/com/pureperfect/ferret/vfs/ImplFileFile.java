package com.pureperfect.ferret.vfs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of a file on the VFS.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplFileFile extends AbstractPathElement implements File
{
	private java.io.File file;

	/**
	 * Create a new file on the VFS.
	 * 
	 * @param parent
	 *            the parent directory
	 * @param file
	 *            the underlying Java file
	 */
	ImplFileFile(Directory parent, java.io.File file)
	{
		super(parent);
		this.file = file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.io.File getFile()
	{
		return this.file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream openStream() throws IOException
	{
		return new FileInputStream(this.file);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return this.getFile().getName();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getFullPath()
	{
		return this.getFile().getAbsolutePath();
	}
}