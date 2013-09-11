package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * An implemention of an archive on the virtual file system.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplArchive extends AbstractPathElement implements Archive
{
	private URL archive;

	/**
	 * Create a new archive on the file system.
	 * 
	 * @param parent
	 *            the parent directory
	 * @param archive
	 *            the underlying archive
	 */
	ImplArchive(Container parent, URL archive)
	{
		super(parent);
		this.archive = archive;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URL getArchive()
	{
		return this.archive;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PathElement> getChildren() throws IOException
	{
		FileSystem fs = FileSystem.getInstance();
		
		JarInputStream in = new JarInputStream(this.archive.openStream());

		List<PathElement> entries = new LinkedList<PathElement>();

		for (ZipEntry entry = in.getNextJarEntry(); entry != null; entry = in
				.getNextJarEntry())
		{
			entries.add(fs.getVirtualPath(this, entry));
		}

		in.close();

		return entries;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return this.archive.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullPath()
	{
		return this.archive.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream openStream() throws IOException
	{
		return this.archive.openStream();
	}
}