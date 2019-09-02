package com.lziegler.PneumaticGripperPC.impl;

import javax.swing.JPanel;


import com.ur.urcap.api.contribution.toolbar.ToolbarAPIProvider;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;


public class ToolbarContribution implements SwingToolbarContribution {

	private final ToolbarAPIProvider apiProvider;
	private GripperLiveControl liveControl;
	private UndoRedoManager undoRedoManager;
	private DataModel model;
	private GripperView view;
	
	private static final String KEY_OUTPUT = "Output";
	private static final Integer DEFAULT_OUTPUT = 0;
	
	public ToolbarContribution(ToolbarContext context) {
		this.apiProvider = context.getAPIProvider();
	}
	public ToolbarContribution(ToolbarContext context,GripperView view) {
		this.view = view;
		this.apiProvider = context.getAPIProvider();
	}
	
	@Override
	public void buildUI(JPanel panel) {
		// TODO Auto-generated method stub
		liveControl = new GripperLiveControl(this.apiProvider.getApplicationAPI());
		liveControl.createUI();
		
		panel.add(liveControl);
	}
	public void onOutputSelection(final Integer output) {
		undoRedoManager.recordChanges(new UndoableChanges() {

			@Override
			public void executeChanges() {
				model.set(KEY_OUTPUT, output);
			}
					
		});
		
	}
	private Integer getOutput() {
		return model.get(KEY_OUTPUT, DEFAULT_OUTPUT);
	}
	
	private Integer[] getOutputItems() {
		Integer[] items = new Integer[8];
		for(int i = 0; i<8; i++) {
			items[i] = i;
		}
		return items;
	}
	

	@Override
	public void openView() {
		// TODO Auto-generated method stub
		liveControl.openView();
		liveControl.setIOComboBoxItems(getOutputItems());
		liveControl.setIOComboBoxSelection(getOutput());
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		liveControl.closeView();
	}

}
