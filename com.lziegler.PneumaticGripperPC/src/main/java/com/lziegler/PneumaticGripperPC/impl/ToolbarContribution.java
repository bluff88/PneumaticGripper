package com.lziegler.PneumaticGripperPC.impl;

import javax.swing.JPanel;


import com.ur.urcap.api.contribution.toolbar.ToolbarAPIProvider;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;


public class ToolbarContribution implements SwingToolbarContribution {

	private final ToolbarAPIProvider apiProvider;
	private GripperLiveControl liveControl;
	
	public ToolbarContribution(ToolbarContext context) {
		this.apiProvider = context.getAPIProvider();
	}
	
	@Override
	public void buildUI(JPanel panel) {
		// TODO Auto-generated method stub
		liveControl = new GripperLiveControl(this.apiProvider.getApplicationAPI());
		liveControl.createUI();
		
		panel.add(liveControl);
	}

	@Override
	public void openView() {
		// TODO Auto-generated method stub
		liveControl.openView();
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		liveControl.closeView();
	}

}
