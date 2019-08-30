package com.lziegler.PneumaticGripperPC.impl;

import java.util.Collection;
import java.util.Iterator;

import com.ur.urcap.api.domain.io.DigitalIO;
import com.ur.urcap.api.domain.io.IOModel;

public class IOHandler {

	private final IOModel ioModel;
	
	public IOHandler(IOModel ioModel) {
		this.ioModel = ioModel; 
	}
	
	
	
	//public IOHandler() {
		// TODO Auto-generated constructor stub
	
	//}



	public DigitalIO getDigitalIO(String defaultName) {
		Collection<DigitalIO> IOCollection = ioModel.getIOs(DigitalIO.class);
		int IO_count = IOCollection.size();
		if(IO_count > 0) {
			Iterator<DigitalIO> IO_itr = IOCollection.iterator();
			while(IO_itr.hasNext()) {
				DigitalIO thisIO = IO_itr.next();
				String thisDefaultName = thisIO.getDefaultName();
				System.out.println("Found an IO named:"+thisDefaultName);
				if(thisDefaultName.equals(defaultName)) {
					return thisIO;
				}
			}
		}
		return null;
	}
}
