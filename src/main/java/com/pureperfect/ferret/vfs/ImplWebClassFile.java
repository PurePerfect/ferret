package com.pureperfect.ferret.vfs;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * Implementation of a class file in WEB-INF/classes.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplWebClassFile extends AbstractPathElement implements ClassFile
{
	private String path;

	private ServletContext context;

	public ImplWebClassFile(Container parent, ServletContext context,
			String path)
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
		return this.context.getResourceAsStream(this.path);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public javassist.bytecode.ClassFile getClassFile() throws IOException
	{
		return new javassist.bytecode.ClassFile(new DataInputStream(
				this.openStream()));
	}
}