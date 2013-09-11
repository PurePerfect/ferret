package com.pureperfect.ferret.vfs;

import java.util.zip.ZipEntry;

/**
 * Represents an entry in an archive on the virtual file system. This will be a
 * generic file since class files in archives are represented using
 * {@link ClassFile}.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface ArchiveEntry extends PathElement
{
	/**
	 * Get the underlying entry.
	 * 
	 * @return the underlying entry
	 */
	public abstract ZipEntry getEntry();
}