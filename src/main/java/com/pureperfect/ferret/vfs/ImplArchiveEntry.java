package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * Represents an entry in an archive of the virtual file system.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplArchiveEntry extends AbstractPathElement implements ArchiveEntry
{
	private ZipEntry entry;

	/**
	 * Create a new archive entry on the file system.
	 * 
	 * @param parent
	 *            the parent archive
	 * @param entry
	 *            the underlying entry
	 */
	public ImplArchiveEntry(Archive parent, ZipEntry entry)
	{
		super(parent);
		this.entry = entry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ZipEntry getEntry()
	{
		return this.entry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream openStream() throws IOException
	{
		Archive ancestor = (Archive) this.getParent();

		return FileUtils.getInputStream(ancestor.getArchive().openStream(),
				entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return this.entry.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullPath()
	{
		String myName = this.getName();

		Archive archive = (Archive) this.getParent();

		String parentName = archive.getArchive().toString();

		StringBuilder builder = new StringBuilder(parentName.length()
				+ myName.length() + 2);

		builder.append(parentName);
		builder.append("!/");
		builder.append(myName);

		return builder.toString();
	}
}