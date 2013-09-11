package com.pureperfect.ferret.vfs;

/**
 * Common logic for path elements.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
abstract class AbstractPathElement implements PathElement
{
	private Container parent;

	public AbstractPathElement(Container parent)
	{
		this.parent = parent;
	}

	public final Container getParent()
	{
		return this.parent;
	}

	@Override
	public final int hashCode()
	{
		return this.getFullPath().hashCode();
	}

	@Override
	public final boolean equals(Object obj)
	{
		if (obj instanceof PathElement)
		{
			PathElement other = (PathElement) obj;

			return this.getFullPath().equals(other.getFullPath());
		}

		return false;
	}
	
	public String getFullPath()
	{
		if(this.parent == null)
			return this.getName();
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.parent.getFullPath());
		
		builder.append(this.getName());
		
		return builder.toString();
	}
	
	@Override
	public final String toString()
	{
		return this.getFullPath();
	}
}