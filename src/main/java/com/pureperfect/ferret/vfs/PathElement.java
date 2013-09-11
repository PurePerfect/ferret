package com.pureperfect.ferret.vfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A resource on the Virtual File System (VFS). Underlying resources on the file
 * system will be mapped to the following virtual types:
 * 
 * <style>
 * 	.mytable {
 * 		border: 1px solid black;
 * 		border-collapse: collapse;
 * 		text-align: center;
 * 	}
 * </style>
 * 
 * <table class="mytable" style="width: 50%;">
 * 	<tr><th class="mytable">Actual Resource</th><th class="mytable">VFS Type</th></tr>
 * 	<tr><td class="mytable">A directory on the local file system</td><td class="mytable">{@link Directory}</td></tr>
 *  <tr><td class="mytable">A file on the local file system</td><td class="mytable">{@link File}</td></tr>
 *  <tr><td class="mytable">A zip, jar or other archive file on the local file system</td><td class="mytable">{@link Archive}</td></tr>
 *  <tr><td class="mytable">A zip, jar or other archive file on the network</td><td class="mytable">{@link Archive}</td></tr>
 *  <tr><td class="mytable">A generic file inside an archive</td><td class="mytable">{@link ArchiveEntry}</td></tr>
 *  <tr><td class="mytable">A Java&trade; class in a directory</td><td class="mytable">{@link ClassFile}</td></tr>
 *  <tr><td class="mytable">A Java&trade; class in an archive</td><td class="mytable">{@link ClassFile}</td></tr>
 * </table>
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface PathElement
{
	/**
	 * Get the parent container for this path.
	 * 
	 * @return the parent container for this path.
	 */
	public Container getParent();

	/**
	 * Get the name of this path, relative to its parent.
	 * 
	 * @return the name of this path
	 */
	public String getName();

	/**
	 * Get the full path, which should be unique on the file system.
	 * 
	 * @return the full path, which should be unique on the file system.
	 */
	public String getFullPath();

	/**
	 * Open a stream to this resource.
	 * 
	 * @return the input stream to this resource
	 * @throws IOException
	 *             if an error occurs opening the stream
	 * @throws UnsupportedOperationException
	 *             if the resource cannot be opened.
	 */
	public InputStream openStream() throws IOException,
			UnsupportedOperationException;
}