package com.pureperfect.ferret.vfs;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A class file for a url.
 * 
 * @author J. Chris Folsom
 * @since 1.0
 * @version 1.0
 */
class ImplURLClassFile extends AbstractPathElement implements ClassFile
{
	private URL url;
	
	public ImplURLClassFile(Container parent, URL url)
	{
		super(parent);
		this.url = url;
	}

	@Override
	public String getName()
	{
		return url.getFile();
	}

	@Override
	public InputStream openStream() throws IOException,
			UnsupportedOperationException
	{
		return this.url.openStream();
	}

	@Override
	public javassist.bytecode.ClassFile getClassFile() throws IOException
	{
		return new javassist.bytecode.ClassFile(new DataInputStream(
				this.openStream()));
	}
}
