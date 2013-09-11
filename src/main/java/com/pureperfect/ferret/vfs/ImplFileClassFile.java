package com.pureperfect.ferret.vfs;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An implementation of a Java class file on the file system.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplFileClassFile extends AbstractPathElement implements ClassFile
{
	private File realFile;

	/**
	 * Create a new class file.
	 * 
	 * @param parent
	 *            the parent directory
	 * @param underlying
	 *            .class file
	 */
	public ImplFileClassFile(Container parent, java.io.File underlying)
	{
		super(parent);
		this.realFile = underlying;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return this.realFile.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream openStream() throws IOException
	{
		return new FileInputStream(this.realFile);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullPath()
	{
		return this.realFile.getAbsolutePath();
	}
}