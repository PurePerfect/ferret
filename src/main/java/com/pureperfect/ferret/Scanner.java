package com.pureperfect.ferret;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pureperfect.ferret.vfs.Container;
import com.pureperfect.ferret.vfs.Directory;
import com.pureperfect.ferret.vfs.FileSystem;
import com.pureperfect.ferret.vfs.PathElement;

/**
 * A scanner is used to scan the class path. This is a three step process:
 * 
 * <ol>
 * <li>Create a new instance of a scanner.</li>
 * <li>Add the paths that you want to scan.</li>
 * <li>Call the scan method and pass it your custom {@link ScanFilter filter}
 * that will determine which results are important to you and which are not.</li>
 * </ol>
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class Scanner
{
	private Prefilter prefilter;

	private final FileSystem fs;

	/**
	 * The set of paths that will be scanned.
	 */
	private final Set<PathElement> scanPath = new HashSet<PathElement>();

	/**
	 * Create a new scanner using the default {@link Prefilter}. The default
	 * implementation ignores files and folders that are added, but do not
	 * actually exist. This is necessary as the JVM could return paths to JAR
	 * files and folders that do not exist. The default behavior should be
	 * appropriate for most users since it does not make sense for most people
	 * to try to scan directories and JAR files that do not exist.
	 */
	public Scanner()
	{
		this(new DefaultPrefilter());
	}

	/**
	 * Create a new scanner using the specified Prefilter.
	 * 
	 * @param prefilter
	 *            The URLFilter that will determine whether or not a URL should
	 *            be allowed to be added to the scan path.
	 */
	public Scanner(final Prefilter prefilter)
	{
		this.setPrefilter(prefilter);
		this.fs = FileSystem.getInstance();
	}

	/**
	 * Add all available resources for the specified
	 * <code>java.lang.ClassLoaders.</code>
	 * 
	 * @param loaders
	 *            The <code>java.lang.ClassLoaders</code> to add resources for.
	 * @throws ScanException
	 *             If an error occurs while attempting to add resources to the
	 *             classpath.
	 */
	public void add(final ClassLoader... loaders) throws ScanException
	{
		for (final ClassLoader loader : loaders)
		{
			try
			{
				final Enumeration<URL> urls = loader.getResources("");

				while (urls.hasMoreElements())
				{
					this.add(urls.nextElement());
				}
			}
			catch (final Throwable t)
			{
				throw new ScanException(t);
			}
		}
	}

	/**
	 * Add the specified path.
	 * 
	 * @param path
	 *            The path to add.
	 */
	public void add(final PathElement path)
	{
		this.scanPath.add(path);
	}

	/**
	 * Add file paths to JARs or directories containing <code>.class</code>
	 * files.
	 * 
	 * @param paths
	 *            The paths to add.
	 * @throws ScanException
	 *             If the file name is invalid.
	 */
	public void add(final String... paths) throws ScanException
	{
		for (final String path : paths)
		{
			final File file = new File(path);

			if (this.prefilter.allow(file))
			{
				this.add(fs.getVirtualPath(null, file));
			}
		}
	}

	/**
	 * Add one or more URLs to the scan path.
	 * 
	 * @param urls
	 *            The URLs to be scanned.
	 */
	public void add(final URL... urls)
	{
		for (final URL url : urls)
		{
			if (this.prefilter.allow(url))
			{
				this.scanPath.add(fs.getVirtualPath(null, url));
			}
		}
	}

	/**
	 * Add one or more directories containing jar files. All of the JARs in the
	 * directory will be added. Files that are not JAR files will not be added.
	 * 
	 * @param jarDirs
	 *            The paths to directories containing jar files.
	 * 
	 * @throws ScanException
	 *             if a bad directory is specified.
	 */
	public void addJarDir(final String... jarDirs) throws ScanException
	{
		/*
		 * Scan each dir for JAR files to add.
		 */
		for (final String jarDir : jarDirs)
		{
			final File file = new File(jarDir);

			if (file.isDirectory())
			{
				Directory parent = (Directory) this.fs.getVirtualPath(null,
						file);

				final File[] jars = file.listFiles(JARFileFilter
						.defaultInstance());

				for (final File jar : jars)
				{
					this.scanPath.add(fs.getVirtualPath(parent, jar));
				}
			}
		}
	}

	/**
	 * Get the URLs on the scan path.
	 * 
	 * @return The scanPath on the scan path.
	 */
	public Set<PathElement> getScanPath()
	{
		return this.scanPath;
	}

	/**
	 * Attempts to add the boot classpath for the JVM. In most cases, this
	 * should be specified by the <code>sun.boot.class.path</code> property. If
	 * this property does not exist for your JVM, no files will be added.
	 * 
	 * @throws ScanException
	 *             If an error occurs whilst attempting to scan the boot class
	 *             path.
	 */
	public void addBootPath() throws ScanException
	{
		final String boot = System.getProperty("sun.boot.class.path");

		if (boot != null)
		{
			this.add(boot.split(File.pathSeparator));
		}
	}

	/**
	 * Attempts to add the known application classpath by reading the
	 * <code>java.class.path</code> property. If this property does not exist no
	 * files will be added.
	 * 
	 * @throws ScanException
	 *             If an error occurs whilst attempting to add the paths.
	 */
	public void addClassPath() throws ScanException
	{
		final String java = System.getProperty("java.class.path");

		if (java != null)
		{
			this.add(java.split(File.pathSeparator));
		}
	}

	/**
	 * Adds all of the JAR files in <code>java.endorsed.dirs</code>.
	 * 
	 * @throws ScanException
	 *             If an error occurs while attempting to scan the endorsed
	 *             directories.
	 */
	public void addEndorsedDirs() throws ScanException
	{
		final String endorsed = System.getProperty("java.endorsed.dirs");

		if (endorsed != null)
		{
			this.addJarDir(endorsed.split(File.pathSeparator));
		}
	}

	/**
	 * Adds all of the JAR files in <code>java.ext.dirs</code>.
	 * 
	 * @throws ScanException
	 *             If an error occurs while attempting to scan the ext
	 *             directories.
	 */
	public void addExtDirs() throws ScanException
	{
		final String ext = System.getProperty("java.ext.dirs");

		if (ext != null)
		{
			this.addJarDir(ext.split(File.pathSeparator));
		}
	}

	/**
	 * Attempts to add java library path by reading the
	 * <code>java.library.path</code> property. These directories should contain
	 * native code and not .jar or .class files.
	 * 
	 * @throws ScanException
	 *             If an error occurs while attempting to add the paths.
	 */
	public void addLibPath() throws ScanException
	{
		final String lib = System.getProperty("java.library.path");

		if (lib != null)
		{
			this.add(lib.split(File.pathSeparator));
		}
	}

	/**
	 * Scan all of the JAR files and all of the directories on the current scan
	 * path using the specified {@link ScanFilter}.
	 * 
	 * @param filter
	 *            The {@link ScanFilter} to use for the scan.
	 * @return The list of resources that matches the filter.
	 * @throws ScanException
	 *             If an error occurs while scanning the class path.
	 */
	@SuppressWarnings("unchecked")
	public <PE extends PathElement> Set<PE> scan(final ScanFilter filter)
			throws ScanException
	{
		try
		{
			final Set<PE> results = new HashSet<PE>();

			for (final PathElement url : this.scanPath)
			{
				this.recursiveScan((PE) url, filter, results);
			}

			return results;
		}
		catch (Throwable t)
		{
			throw new ScanException(t);
		}
	}

	@SuppressWarnings("unchecked")
	private <PE extends PathElement> void recursiveScan(PE start,
			ScanFilter filter, Set<PE> results) throws IOException
	{
		if (filter.accept(start))
		{
			results.add(start);
		}

		if (start instanceof Container)
		{
			Container container = (Container) start;

			List<PathElement> children = container.getChildren();

			for (PathElement child : children)
			{
				this.recursiveScan((PE) child, filter, results);
			}
		}
	}

	/**
	 * Get the current prefilter.
	 * 
	 * @return the current prefilter
	 */
	public Prefilter getPrefilter()
	{
		return prefilter;
	}

	/**
	 * Set the prefilter. The default implementation ignores files and folders
	 * that are added, but do not actually exist. This is necessary as the JVM
	 * could return paths to JAR files and folders that do not exist. The
	 * default behavior should be appropriate for most users since it does not
	 * make sense for most people to try to scan directories and JAR files that
	 * do not exist.
	 * 
	 * @param prefilter
	 *            the prefilter to use
	 */
	public void setPrefilter(Prefilter prefilter)
	{
		this.prefilter = prefilter;
	}

	/**
	 * A string representation of the scan path.
	 */
	@Override
	public String toString()
	{
		return this.scanPath.toString();
	}
}