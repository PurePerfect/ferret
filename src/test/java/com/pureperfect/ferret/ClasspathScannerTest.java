package com.pureperfect.ferret;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.pureperfect.ferret.vfs.PathElement;

/**
 * Test core functionality.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class ClasspathScannerTest extends TestCase
{
	public void testAddBootPath() throws IOException
	{
		final Scanner ferret = new Scanner();

		final String bootPath = System.getProperty("sun.boot.class.path");

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.addBootPath();

		final String[] expected = bootPath.split(File.pathSeparator);

		final Set<String> expectedSet = new HashSet<String>();

		expectedSet.addAll(Arrays.asList(expected));

		/*
		 * Sometimes the property contains paths that don't exist. We need to
		 * remove them because if the path does not exist then it will not be
		 * added to the scanner, and the numbers will not match up because we
		 * will get a false positive.
		 */
		final Set<String> missing = new HashSet<String>();

		for (final String doesItExist : expectedSet)
		{
			if (!new File(doesItExist).exists())
			{
				missing.add(doesItExist);
			}
		}

		expectedSet.removeAll(missing);

		// Generate set of expected urls
		final Set<URL> expectedURLS = new HashSet<URL>();

		for (final String expect : expectedSet)
		{
			final URL url = new File(expect).toURI().toURL();

			expectedURLS.add(url);
		}
	}

	public void testAddClassLoaderResources() throws Exception
	{
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();

		final Enumeration<URL> urls = cl.getResources("");

		final Set<URL> expected = new HashSet<URL>();

		while (urls.hasMoreElements())
		{
			final URL url = urls.nextElement();

			if (url.toString().startsWith("file:"))
			{
				final File f = new File(url.getFile());

				if (f.isDirectory() || f.getName().endsWith(".jar"))
				{
					expected.add(url);
				}
			}
		}

		final Scanner ferret = new Scanner();

		ferret.add(cl);

		Assert.assertEquals(expected.size(), ferret.getScanPath().size());
	}

	public void testAddDirectories() throws MalformedURLException
	{
		final File srcDir = new File("src");

		final Scanner ferret = new Scanner();

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.add(srcDir.toURI().toURL());

		Assert.assertEquals(1, ferret.getScanPath().size());

		// Duplicate add should do nothing
		ferret.add(srcDir.toURI().toURL());

		Assert.assertEquals(1, ferret.getScanPath().size());
	}

	public void testAddEndorsedDirs() throws MalformedURLException
	{
		final Scanner ferret = new Scanner();

		final String endorsed = System.getProperty("java.endorsed.dirs");

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.addEndorsedDirs();

		final List<File> expected = new LinkedList<File>();

		final String[] endorsedDirs = endorsed.split(File.pathSeparator);

		for (final String dir : endorsedDirs)
		{
			final File[] jars = new File(dir).listFiles(JARFileFilter
					.defaultInstance());

			if (jars != null)
			{
				for (final File jar : jars)
				{
					expected.add(jar);
				}
			}
		}

		Assert.assertEquals(expected.size(), ferret.getScanPath().size());
	}

	public void testAddExtDirs() throws MalformedURLException
	{
		final Scanner ferret = new Scanner();

		final String ext = System.getProperty("java.ext.dirs");

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.addExtDirs();

		final List<File> expected = new LinkedList<File>();

		final String[] extDirs = ext.split(File.pathSeparator);

		for (final String dir : extDirs)
		{
			final File[] jars = new File(dir).listFiles(JARFileFilter
					.defaultInstance());

			if (jars != null)
			{
				for (final File jar : jars)
				{
					expected.add(jar);
				}
			}
		}

		Assert.assertEquals(expected.size(), ferret.getScanPath().size());
	}

	public void testAddJarDirs() throws Exception
	{
		// Testing bad path only. Happy path is tested elsewhere
		final Scanner ferret = new Scanner();

		ferret.addJarDir("IDONTEXIST");

		Assert.assertEquals(0, ferret.getScanPath().size());
	}

	public void testAddJars() throws MalformedURLException
	{
		final File srcDir = new File("src");

		final Scanner ferret = new Scanner();

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.add(srcDir.toURI().toURL());

		Assert.assertEquals(1, ferret.getScanPath().size());

		// Duplicate add should do nothing
		ferret.add(srcDir.toURI().toURL());

		Assert.assertEquals(1, ferret.getScanPath().size());
	}

	public void testAddJavaClassPath() throws IOException
	{
		final Scanner ferret = new Scanner();

		final String classPath = System.getProperty("java.class.path");

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.addClassPath();

		final String[] expected = classPath.split(File.pathSeparator);

		final Set<String> expectedSet = new HashSet<String>();

		expectedSet.addAll(Arrays.asList(expected));

		/*
		 * Sometimes the property contains paths that don't exist. We need to
		 * remove them because if the path does not exist then it will not be
		 * added to the scanner, and the numbers will not match up because we
		 * will get a false positive.
		 */
		final Set<String> missing = new HashSet<String>();

		for (final String doesItExist : expectedSet)
		{
			if (!new File(doesItExist).exists())
			{
				missing.add(doesItExist);
			}
		}

		expectedSet.removeAll(missing);

		// Generate set of expected urls
		final Set<URL> expectedURLS = new HashSet<URL>();

		for (final String expect : expectedSet)
		{
			final URL url = new File(expect).toURI().toURL();

			expectedURLS.add(url);
		}

		Assert.assertEquals(expectedSet.size(), ferret.getScanPath().size());
	}

	public void testAddLibPath() throws IOException
	{
		final Scanner ferret = new Scanner();

		final String libPath = System.getProperty("java.library.path");

		Assert.assertEquals(0, ferret.getScanPath().size());

		ferret.addLibPath();

		final String[] expected = libPath.split(File.pathSeparator);

		final Set<String> expectedSet = new HashSet<String>();

		expectedSet.addAll(Arrays.asList(expected));

		/*
		 * Sometimes the property contains paths that don't exist. We need to
		 * remove them because if the path does not exist then it will not be
		 * added to the scanner, and the numbers will not match up because we
		 * will get a false positive.
		 */
		final Set<String> missing = new HashSet<String>();

		for (final String doesItExist : expectedSet)
		{
			if (!new File(doesItExist).exists())
			{
				missing.add(doesItExist);
			}
		}

		expectedSet.removeAll(missing);

		// Generate set of expected urls
		final Set<URL> expectedURLS = new HashSet<URL>();

		for (final String expect : expectedSet)
		{
			final URL url = new File(expect).toURI().toURL();

			expectedURLS.add(url);
		}

		Assert.assertEquals(expectedSet.size(), ferret.getScanPath().size());
	}

	public void testAddPaths() throws Exception
	{
		// Testing bad path only. Happy path is tested elsewhere
		final Scanner ferret = new Scanner();

		ferret.add("IDONTEXIST");

		Assert.assertEquals(0, ferret.getScanPath().size());
	}

	public void testJarLoadFromNetwork() throws Exception
	{
		final String url = "https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar";

		final Scanner ferret = new Scanner();

		ferret.add(new URL(url));

		Assert.assertEquals(1, ferret.getScanPath().size());

		final List<PathElement> filesInJar = new LinkedList<PathElement>();

		final ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				filesInJar.add(resource);

				return false;
			}
		};

		ferret.scan(filter);

		// This file should have more that 2 files in it
		Assert.assertTrue(filesInJar.size() > 2);
	}

	public void testScan() throws Exception
	{
		final Scanner ferret = new Scanner();

		ferret.addBootPath();

		final Set<PathElement> results = ferret.scan(new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return "java/lang/Object.class".equals(resource.getName());
			}
		});

		Assert.assertEquals(1, results.size());
	}

	public void testScanDirectories() throws Exception
	{
		Scanner ferret = new Scanner();

		// This should work for both the IDE and Maven
		ferret.addClassPath();

		// Scanner should be on the path in a directory
		Set<PathElement> results = ferret.scan(new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return "Scanner.class".equals(resource.getName());
			}
		});

		Assert.assertEquals(1, results.size());

		// Scan for non-class resources
		ferret = new Scanner();

		ferret.add(new File("src/").toURI().toURL());

		results = ferret.scan(new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return resource.getName().endsWith(".java");
			}
		});

		Assert.assertTrue(results.size() > 0);
	}

	public void testScanJars() throws Exception
	{
		final Scanner ferret = new Scanner();

		ferret.addBootPath();

		// java.lang.object should be in rt.jar
		Set<PathElement> results = ferret.scan(new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return "java/lang/Object.class".equals(resource.getName());
			}
		});

		Assert.assertEquals(1, results.size());

		// Scan for non class things
		results = ferret.scan(new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return resource.getName().endsWith(".MF");
			}
		});

		Assert.assertTrue(results.size() > 0);
	}

	public void testZipLoadFromNetwork() throws Exception
	{
		final String url = "http://archive.apache.org/dist/continuum/binaries/apache-continuum-1.2-bin.zip";

		final Scanner ferret = new Scanner();

		ferret.add(new URL(url));

		Assert.assertEquals(1, ferret.getScanPath().size());

		final ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return true;
			}
		};

		Set<PathElement> results = ferret.scan(filter);

		// This file should have more that 2 files in it : )
		Assert.assertTrue(results.size() > 2);
	}

	public void testFileLoadFromNetwork() throws Exception
	{
		final String url = "http://blog.smartbear.com/code-review/is-bad-software-our-generations-biggest-stressor/";

		final Scanner ferret = new Scanner();

		ferret.add(new URL(url));

		Assert.assertEquals(1, ferret.getScanPath().size());

		final ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return true;
			}
		};

		Set<PathElement> results = ferret.scan(filter);

		Assert.assertEquals(results.size(), 1);

		assertTrue(results.iterator().next() instanceof com.pureperfect.ferret.vfs.File);
	}

	public void testClassFileLoadFromNetwork() throws Exception
	{
		final String url = "http://localhost:8080/ferret-test/testclass.class";

		final Scanner ferret = new Scanner();

		ferret.add(new URL(url));

		Assert.assertEquals(1, ferret.getScanPath().size());

		final ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(final PathElement resource)
			{
				return true;
			}
		};

		Set<PathElement> results = ferret.scan(filter);

		Assert.assertEquals(results.size(), 1);

		assertTrue(results.iterator().next() instanceof com.pureperfect.ferret.vfs.ClassFile);
		assertTrue(results.iterator().next().getName().endsWith("testclass.class"));
	}
}