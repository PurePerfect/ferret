package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;

import com.pureperfect.ferret.Scanner;
import com.pureperfect.ferret.ScanFilter;

public class DirectoryTest extends TestCase
{
	public void testGetParent()
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
				return resource.getName().endsWith("subdir");
			}
		});

		Directory child = (Directory) results.iterator().next();

		assertEquals("directorytest", child.getParent().getName());
	}

	public void testGetFile()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		FileSystem fs = FileSystem.getInstance();

		Directory dir = (Directory) fs.getVirtualPath(null, file);

		assertEquals(file, dir.getFile());
	}

	public void testGetChildren() throws IOException
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
				return resource.getName().endsWith(".txt");
			}
		});

		assertEquals(1, results.size());
	}

	public void testOpenStream() throws Exception
	{
		FileSystem fs = FileSystem.getInstance();

		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Directory dir = (Directory) fs.getVirtualPath(null, file);

		try
		{
			dir.openStream();
			fail();
		}
		catch (UnsupportedOperationException e)
		{
			assertTrue(e.getMessage().contains("testdata/directorytest is a directory."));
		}
	}

	public void testGetName()
	{
		FileSystem fs = FileSystem.getInstance();

		Directory dir = (Directory) fs.getVirtualPath(null, new java.io.File(Constants.TESTDIR
				+ "directorytest"));

		assertEquals("directorytest", dir.getName());
	}

	public void testToString()
	{
		FileSystem fs = FileSystem.getInstance();

		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Directory dir = (Directory) fs.getVirtualPath(null, file);

		assertEquals(file.getAbsolutePath(), dir.toString());
	}

	public void testGetFullPath()
	{
		FileSystem fs = FileSystem.getInstance();

		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Directory dir = (Directory) fs.getVirtualPath(null, file);

		assertEquals(file.getAbsolutePath(), dir.getFullPath());
	}

	public void testHashcode()
	{
		FileSystem fs = FileSystem.getInstance();

		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Directory one = (Directory) fs.getVirtualPath(null, file);
		Directory two = (Directory) fs.getVirtualPath(null, file);

		assertEquals(one.hashCode(), two.hashCode());
	}

	public void testEquals()
	{
		FileSystem fs = FileSystem.getInstance();

		java.io.File file = new java.io.File(Constants.TESTDIR
				+ "directorytest");

		Directory one = (Directory) fs.getVirtualPath(null, file);
		Directory two = (Directory) fs.getVirtualPath(null, file);

		assertEquals(one, two);
	}
}