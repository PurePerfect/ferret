package com.pureperfect.ferret.vfs;

import java.net.MalformedURLException;
import java.util.Set;

import com.pureperfect.ferret.Scanner;
import com.pureperfect.ferret.ScanFilter;

import junit.framework.TestCase;

public class ArchiveTest extends TestCase
{
	public void testGetArchive() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

		Archive archive = (Archive) FileSystem.getInstance().getVirtualPath(null, file);

		assertEquals(file.toURI().toURL(), archive.getArchive());
	}

	public void testGetChildren()
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

	public void testGetName() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

		Archive archive = (Archive) FileSystem.getInstance().getVirtualPath(null, file);

		assertEquals("file:" + file.getAbsolutePath(), archive.getName());
	}

	public void testToString() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

		Archive archive = (Archive) FileSystem.getInstance().getVirtualPath(null, file);

		assertEquals("file:" + file.getAbsolutePath(), archive.toString());
	}

	public void testGetFullPath() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

		Archive archive = (Archive) FileSystem.getInstance().getVirtualPath(null, file);

		assertEquals("file:" + file.getAbsolutePath(), archive.getFullPath());
	}

	public void testHashcode() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

		Archive archive = (Archive) FileSystem.getInstance().getVirtualPath(null, file);
		Archive archive2 = (Archive) FileSystem.getInstance().getVirtualPath(null, file);

		assertEquals(archive.hashCode(), archive2.hashCode());
	}

	public void testEquals() throws MalformedURLException
	{
		java.io.File file = new java.io.File(Constants.TESTDIR + "jartest.jar");

		Archive archive = (Archive) FileSystem.getInstance().getVirtualPath(null, file);
		Archive archive2 = (Archive) FileSystem.getInstance().getVirtualPath(null, file);

		assertEquals(archive, archive2);
	}
}