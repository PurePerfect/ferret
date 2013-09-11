package com.pureperfect.ferret.vfs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;

import com.pureperfect.ferret.ScanException;

/**
 * An implementation of a lightweight Virtual File System (VFS) appropriate for
 * scanning. The VFS is made up of {@link PathElement PathElements} representing
 * the resources on the underlying file system. The actual mapping from the real
 * resource to the VFS {@link PathElement} can be seen in the table below.
 * 
 * <style> .mytable { border: 1px solid black; border-collapse: collapse;
 * text-align: center; } </style>
 * 
 * <table class="mytable" style="width: 50%;">
 * <tr>
 * <th class="mytable">Actual Resource</th>
 * <th class="mytable">VFS Type</th>
 * </tr>
 * <tr>
 * <td class="mytable">A directory on the local file system</td>
 * <td class="mytable">{@link Directory}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A file on the local file system</td>
 * <td class="mytable">{@link File}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A ZIP file on the local file system</td>
 * <td class="mytable">{@link Archive}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A ZIP file on the network</td>
 * <td class="mytable">{@link Archive}</td>
 * </tr>
 * <td class="mytable">A JAR file on the local file system</td>
 * <td class="mytable">{@link Archive}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A JAR file on the network</td>
 * <td class="mytable">{@link Archive}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A generic file inside an archive</td>
 * <td class="mytable">{@link ArchiveEntry}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A Java&trade; class in a directory</td>
 * <td class="mytable">{@link ClassFile}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A Java&trade; class in an archive</td>
 * <td class="mytable">{@link ClassFile}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A Java&trade; class on the network</td>
 * <td class="mytable">{@link ClassFile}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A generic file on the network</td>
 * <td class="mytable">{@link File}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A generic file inside a web application (WAR)</td>
 * <td class="mytable">{@link WebFile}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A directory inside a web application (WAR)</td>
 * <td class="mytable">{@link WebDirectory}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A ZIP file inside a web application (WAR)</td>
 * <td class="mytable">{@link Archive}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A JAR file inside a web application (WAR)</td>
 * <td class="mytable">{@link Archive}</td>
 * </tr>
 * <tr>
 * <td class="mytable">A class file inside a web application (WAR)</td>
 * <td class="mytable">{@link ClassFile}</td>
 * </tr>
 * </table>
 * 
 * TODO finish documentation. TODO delay file creation?
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 * @see WebFileSystem
 */
public class FileSystem
{
	private static FileSystem singleton = new FileSystem();

	private FileSystem()
	{
		// singleton
	}

	/**
	 * Determine whether or not a URL is a directory on the local file system.
	 * 
	 * @return whether or not a URL is a directory on the local file system.
	 */
	private static boolean isDirectory(final URL url)
	{
		return "file".equals(url.getProtocol())
				&& new File(url.getFile()).isDirectory();
	}

	/**
	 * Determine whether or not the given file is an archive.
	 * 
	 * @param file
	 *            the file to test
	 * @return whether or not it is an archive
	 */
	private static boolean isArchive(File file)
	{
		return isArchive(file.getName());
	}

	/**
	 * Determine whether or not the given url is an archive.
	 * 
	 * @param url
	 *            the url to test
	 * @return whether or not the given url is an archive
	 */
	private static boolean isArchive(URL url)
	{
		return isArchive(url.toString());
	}

	/**
	 * Determine whether or not the given name is an archive.
	 * 
	 * @param s
	 *            the name to test
	 * @return whether or not it is an archive
	 */
	private static boolean isArchive(String s)
	{
		return s.endsWith(".jar") || s.endsWith(".zip") || s.endsWith(".war")
				|| s.endsWith(".ear") || s.endsWith(".sar");
	}

	/**
	 * Get an instance of the file system.
	 * 
	 * @return an instance of the file system
	 */
	public static FileSystem getInstance()
	{
		return singleton;
	}

	/**
	 * Get the appropriate type of resource for the file.
	 * 
	 * @param parent
	 *            the parent directory if applicable. This can be null
	 * @param file
	 *            the file
	 * @return the virtual resource
	 */
	public PathElement getVirtualPath(Directory parent, File file)
	{
		if (file.isDirectory())
		{
			return new ImplDirectory(parent, file);
		}
		else if (isArchive(file))
		{
			try
			{
				return new ImplArchive(parent, file.toURI().toURL());
			}
			catch (MalformedURLException e)
			{
				throw new ScanException(e);
			}
		}
		else if (file.getName().endsWith(".class"))
		{
			return new ImplFileClassFile(parent, file);
		}

		return new ImplFileFile(parent, file);
	}

	/**
	 * Get the appropriate type of resource for the url.
	 * 
	 * @param parent
	 *            The parent directory of the given url. This can be null
	 * @param url
	 *            the url to get the virtual resource for
	 * @return the virtual resource
	 */
	public PathElement getVirtualPath(Directory parent, URL url)
	{
		if (isDirectory(url))
		{
			return new ImplDirectory(parent, new File(url.getFile()));
		}
		else if (isArchive(url))
		{
			return new ImplArchive(parent, url);
		}
		else if (url.toString().endsWith(".class"))
		{
			return new ImplURLClassFile(parent, url);
		}

		return new ImplFileFile(parent, new File(url.getFile()));
	}

	/**
	 * Get the appropriate type of resource for the archive entry.
	 * 
	 * @param parent
	 *            The archive containing the entry
	 * @param entry
	 *            the entry
	 * @return a virtual representation of the entry
	 */
	public PathElement getVirtualPath(Archive parent, ZipEntry entry)
	{
		if (entry.getName().endsWith(".class"))
		{
			return new ImplArchiveEntryClassFile(parent, entry);
		}

		else
		{
			return new ImplArchiveEntry(parent, entry);
		}
	}
}