package com.pureperfect.ferret.vfs;

/**
 * A file in a web application (WAR File). This is merely a marker interface to
 * provide for easy type distinction. It does not define any additional
 * behavior beyond that of a generic {@link PathElement}.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface WebFile extends PathElement
{
	// No additional behavior
}
