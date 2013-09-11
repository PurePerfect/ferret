package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * Random helper crap.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class FileUtils
{
	/**
	 * Ugghhh... this garbage is unfortunately necessary if we want to support
	 * network scanning since ZipFile and JARFile only have ImplFileFile
	 * constructors and not InputStream constructors. The whole thing is pretty
	 * asinine, since if you actually look at the code, they are just getting an
	 * input stream for the file anyway.
	 * 
	 * @param inStream
	 *            the input stream
	 * @param entry
	 *            the entry in the jar file
	 * @return the input stream for the entry
	 * @throws IOException
	 */
	public static InputStream getInputStream(InputStream inStream,
			ZipEntry entry) throws IOException
	{
		JarInputStream in = new JarInputStream(inStream);

		for (JarEntry current = in.getNextJarEntry(); current != null; current = in
				.getNextJarEntry())
		{
			if (current.getName().equals(entry.getName()))
			{
				return in;
			}
		}

		return in;
	}
}