package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.util.List;

/**
 * A container is a PathElement that contains other PathElements. This will either be an
 * {@link Archive} or a {@link Directory}.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface Container extends PathElement
{
	/**
	 * Get the children of this container.
	 * 
	 * @return the children of this container
	 * @throws IOException
	 *             if it happens
	 */
	public List<PathElement> getChildren() throws IOException;
}
