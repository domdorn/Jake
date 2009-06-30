package com.jakeapp.gui.swing.xcore;

import com.jakeapp.gui.swing.helpers.ITimeUtilities;
import com.jakeapp.gui.swing.helpers.PetersTimeUtilities;
import com.jakeapp.gui.swing.helpers.PrettyTimeTimeUtilities;
import com.jakeapp.gui.swing.ICoreAccess;
import org.apache.log4j.Logger;

/**
 * This is a dirty singleton class trying to help the transition from peters other singleton classes
 * to a dependency injected world with loosely coupled components. 
 */
public class ObjectRegistry {
	private static Logger log = Logger.getLogger(ObjectRegistry.class);
	// instance variable
	private static ObjectRegistry instance;

	// holds the beans
	private ITimeUtilities timeUtilities;
	private ICoreAccess coreAccess;

	{
		// other initialization-code
		log.debug("class initialization");



		// manually initialize stuff
		log.debug("initialize timeUtilities");
//		timeUtilities = new PetersTimeUtilities();
		timeUtilities = new PrettyTimeTimeUtilities();

	}

	private static ObjectRegistry getInstance()
	{
		if(instance == null)
			instance = new ObjectRegistry();
		return instance;
	}





	public static ITimeUtilities getTimeUtilities()
	{
		return getInstance().timeUtilities;
	}

	public static ICoreAccess getCoreAccess()
	{
		return getInstance().coreAccess;
	}


}
