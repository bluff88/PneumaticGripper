package com.lziegler.PneumaticGripperPC.impl;

import java.util.Locale;


import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class GripperService implements SwingProgramNodeService<GripperContribution, GripperView> {

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "gripperNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		// TODO Auto-generated method stub
		configuration.setChildrenAllowed(false);
		configuration.setUserInsertable(true);
	}

	@Override
	public String getTitle(Locale locale) {
		// TODO Auto-generated method stub
		return "ProCobot Gripper";
	}

	@Override
	public GripperView createView(ViewAPIProvider apiProvider) {
		// TODO Auto-generated method stub
		return new GripperView(apiProvider);
	}

	@Override
	public GripperContribution createNode(ProgramAPIProvider apiProvider, GripperView view, DataModel model,
			CreationContext context) {
		// TODO Auto-generated method stub
		return new GripperContribution(apiProvider, view, model, context);
	}

	
////	@Override
////	public GripperContribution createNode(ProgramAPIProvider apiProvider, GripperView view, DataModel model,
////			CreationContext context) {
////		// TODO Auto-generated method stub
////		return new GripperContribution(apiProvider, view, model, context);
//	}
	
	

}
