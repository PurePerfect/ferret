package com.pureperfect.ferret.vfs;

import java.io.IOException;

/**
 * A Java class file. It's parent will be either a {@link Directory} or an {@link Archive}.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface ClassFile extends PathElement
{
	/**
	 * Get the underlying class file.
	 * 
	 * @return the underlying class file
	 * @throws IOException
	 *             if an error occurs trying to parse the class file
	 */
	public abstract javassist.bytecode.ClassFile getClassFile()
			throws IOException;
}