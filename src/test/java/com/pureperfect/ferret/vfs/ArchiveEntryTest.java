package com.pureperfect.ferret.vfs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Set;

import junit.framework.TestCase;

import com.pureperfect.ferret.Scanner;
import com.pureperfect.ferret.ScanFilter;

public class ArchiveEntryTest extends TestCase
{
	public void testGetParent()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry child = (ArchiveEntry) results.iterator().next();

		assertEquals("file:" + file.getAbsolutePath(), child.getParent()
				.getName());
	}

	public void testGetEntry()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry entry = (ArchiveEntry) results.iterator().next();

		assertEquals("inputstreamtest.txt", entry.getEntry().getName());
	}

	public void testOpenStream() throws Exception
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry entry = (ArchiveEntry) results.iterator().next();

		InputStream in = entry.openStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		assertEquals("thisisatest", reader.readLine());
		assertNull(reader.readLine());
	}

	public void testGetName()
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry entry = (ArchiveEntry) results.iterator().next();

		assertEquals("inputstreamtest.txt", entry.getName());
	}

	public void testToString() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry entry = (ArchiveEntry) results.iterator().next();

		assertEquals(file.toURI().toURL() + "!/inputstreamtest.txt",
				entry.toString());
	}

	public void testGetFullPath() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry entry = (ArchiveEntry) results.iterator().next();

		assertEquals(file.toURI().toURL() + "!/inputstreamtest.txt",
				entry.getFullPath());
	}

	public void testHashCode() throws Exception
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry one = (ArchiveEntry) scanner.scan(filter).iterator()
				.next();

		ArchiveEntry two = (ArchiveEntry) scanner.scan(filter).iterator()
				.next();

		assertEquals(one.hashCode(), two.hashCode());
	}

	public void testEquals() throws Exception
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

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

		ArchiveEntry one = (ArchiveEntry) scanner.scan(filter).iterator()
				.next();

		ArchiveEntry two = (ArchiveEntry) scanner.scan(filter).iterator()
				.next();

		assertEquals(one, two);
	}
}