package com.pureperfect.ferret.vfs;

/**
 * A directory in a web application (WAR). This is merely a marker interface to
 * provide for easy type distinction. It does not define any additional
 * behavior beyond that of a generic {@link Directory}.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface WebDirectory extends Container
{
	//No additional behavior
}
