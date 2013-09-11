package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of a directory on the virtual file system.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplDirectory extends ImplFileFile implements Directory
{
	/**
	 * Create a new directory on the virtual file system.
	 * 
	 * @param parent
	 *            the parent directory
	 * @param file
	 *            the underlying Java file
	 */
	public ImplDirectory(Directory parent, java.io.File file)
	{
		super(parent, file);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PathElement> getChildren() throws IOException
	{
		FileSystem fs = FileSystem.getInstance();

		java.io.File[] subfiles = this.getFile().listFiles();

		if (subfiles == null)
			return new LinkedList<PathElement>();

		List<PathElement> results = new ArrayList<PathElement>(subfiles.length);

		for (java.io.File file : subfiles)
		{
			results.add(fs.getVirtualPath(this, file));
		}

		return results;
	}

	@Override
	public InputStream openStream() throws IOException
	{
		throw new UnsupportedOperationException(this.getFullPath()
				+ " is a directory.");
	}
}