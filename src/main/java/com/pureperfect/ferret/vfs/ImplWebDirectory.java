package com.pureperfect.ferret.vfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * Implementation of a web directory.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class ImplWebDirectory extends ImplWebFile implements WebDirectory
{
	/**
	 * Create a new web directory.
	 * 
	 * @param parent
	 *            the parent directory
	 * @param context
	 *            the servlet context
	 * @param path
	 *            the path to this directory
	 */
	public ImplWebDirectory(WebDirectory parent, ServletContext context,
			String path)
	{
		super(parent, context, path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PathElement> getChildren() throws IOException
	{
		WebFileSystem fs = WebFileSystem.getInstance();
		
		Set<String> childPaths = context.getResourcePaths(this.path);

		List<PathElement> results = new ArrayList<PathElement>();

		for (String path : childPaths)
		{
			results.add(fs.getVirtualPath(this, context, path));
		}

		return results;
	}
}