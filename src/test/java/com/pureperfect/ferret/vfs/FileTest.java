package com.pureperfect.ferret.vfs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import com.pureperfect.ferret.Scanner;
import com.pureperfect.ferret.ScanFilter;

import junit.framework.TestCase;

public class FileTest extends TestCase
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
				return resource.getName().endsWith(".txt");
			}
		});

		File child = (File) results.iterator().next();

		assertEquals("directorytest", child.getParent().getName());
	}

	public void testGetFile()
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

		File child = (File) results.iterator().next();

		assertEquals("inputstreamtest.txt", child.getFile().getName());
	}

	public void testOpenStream() throws Exception
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

		File child = (File) results.iterator().next();

		InputStream in = child.openStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		assertEquals("thisisatest", reader.readLine());
		assertNull(reader.readLine());
	}

	public void testGetName()
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

		File child = (File) results.iterator().next();

		assertEquals(child.getFile().getName(), child.getName());
	}

	public void testToString()
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

		File child = (File) results.iterator().next();

		assertEquals(child.getFile().getAbsolutePath(), child.toString());
	}

	public void testGetFullPath()
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

		File child = (File) results.iterator().next();

		assertEquals(child.getFile().getAbsolutePath(), child.getFullPath());
	}

	public void testHashcode()
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
				return resource.getName().endsWith(".txt");
			}
		};

		File one = (File) scanner.scan(filter).iterator().next();
		File two = (File) scanner.scan(filter).iterator().next();

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
				return resource.getName().endsWith(".txt");
			}
		};

		File one = (File) scanner.scan(filter).iterator().next();
		File two = (File) scanner.scan(filter).iterator().next();

		assertEquals(one, two);
	}
}