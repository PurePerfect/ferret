package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Set;

import junit.framework.TestCase;

import com.pureperfect.ferret.Scanner;
import com.pureperfect.ferret.ScanFilter;

public class ClassFileTest extends TestCase
{
	public void testGetParentFromArchive() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "jarclasstest.jar");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals(file.toURI().toURL(),
				((Archive) entry.getParent()).getArchive());
	}

	public void testGetClassFileFromArchive() throws IOException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "jarclasstest.jar");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals("com.pureperfect.ferret.ClasspathScannerTest", entry
				.getClassFile().getName());
	}

	public void testOpenStreamFromArchive() throws IOException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "jarclasstest.jar");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		InputStream in = entry.openStream();

		int count = 0;

		for (int c = in.read(); c != -1; c = in.read())
		{
			count++;
		}

		assertEquals(10024, count);
	}

	public void testGetNameFromArchive()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "jarclasstest.jar");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals("com/pureperfect/ferret/ClasspathScannerTest.class",
				entry.getName());
	}

	public void testToStringFromArchive()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "jarclasstest.jar");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals(entry.getFullPath(), entry.toString());
	}

	public void testGetParentFromDirectory() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals("ferret", entry.getParent().getName());
	}

	public void testGetClassFileFromFile() throws IOException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals("com.pureperfect.ferret.ClasspathScannerTest", entry
				.getClassFile().getName());
	}

	public void testOpenStreamFromFile() throws IOException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		InputStream in = entry.openStream();

		int count = 0;

		for (int c = in.read(); c != -1; c = in.read())
		{
			count++;
		}

		assertEquals(10024, count);
	}

	public void testGetNameFromDirectory()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals("ClasspathScannerTest.class", entry.getName());
	}

	public void testToStringFromDirectory()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals(entry.getFullPath(), entry.toString());
	}

	public void testHashCode()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		};

		ClassFile one = (ClassFile) scanner.scan(filter).iterator().next();
		ClassFile two = (ClassFile) scanner.scan(filter).iterator().next();

		assertEquals(one.hashCode(), two.hashCode());
	}

	public void testEquals()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		};

		ClassFile one = (ClassFile) scanner.scan(filter).iterator().next();
		ClassFile two = (ClassFile) scanner.scan(filter).iterator().next();

		assertEquals(one, two);
	}

	public void testGetFullPathFromFile() throws Exception
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		ScanFilter filter = new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		};

		ClassFile one = (ClassFile) scanner.scan(filter).iterator().next();

		assertEquals(file.getAbsolutePath()
				+ "/com/pureperfect/ferret/ClasspathScannerTest.class",
				one.getFullPath());
	}

	public void testGetFullPathFromArchive() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "jarclasstest.jar");

		Scanner scanner = new Scanner();

		scanner.add(file.getAbsolutePath());

		Set<PathElement> results = scanner.scan(new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return resource.getName().endsWith("Test.class");
			}
		});

		ClassFile entry = (ClassFile) results.iterator().next();

		assertEquals(file.toURI().toURL().toString()
				+ "!/com/pureperfect/ferret/ClasspathScannerTest.class",
				entry.getFullPath());
	}
}