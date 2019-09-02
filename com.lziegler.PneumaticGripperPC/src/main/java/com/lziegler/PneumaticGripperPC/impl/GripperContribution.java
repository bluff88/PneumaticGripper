package com.lziegler.PneumaticGripperPC.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;

public class GripperContribution implements ProgramNodeContribution {
	
	private final ProgramAPI programAPI;
	private final DataModel model;
	private final GripperView view;
	private final UndoRedoManager undoRedoManager;
	

	public GripperContribution(ProgramAPIProvider apiProvider, GripperView view, DataModel model, com.ur.urcap.api.contribution.program.CreationContext context) {
		// TODO Auto-generated constructor stub
		this.programAPI = apiProvider.getProgramAPI();
		this.view = view;
		this.model = model;
		this.undoRedoManager = apiProvider.getProgramAPI().getUndoRedoManager();
		
	}
	
	
	private static final String KEY_NODEFUNCTION = "gripperNodeFunction";
	private static final String KEY_OUTPUT = "Output";
	private static final Integer DEFAULT_OUTPUT = 0;
	
	private boolean isGripNode() {
		return model.get(KEY_NODEFUNCTION, true);
	}
	
	public void setGripNode(final boolean gripNotRelease) {
		undoRedoManager.recordChanges(new UndoableChanges() {

			@Override
			public void executeChanges() {
				// TODO Auto-generated method stub
				model.set(KEY_NODEFUNCTION, gripNotRelease);
			}
			
		});
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
		return model.get(KEY_OUTPUT, DEFAULT_OUTPUT );
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
		System.out.println("openView of ManipulateIO program node");
		view.setRadioButtons(isGripNode());
		view.updateLiveControl();
		view.setIOComboBoxItems(getOutputItems());
		view.setIOComboBoxSelection(getOutput());

		
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		view.stopLiveControl();
	}

	@Override
	public String getTitle() {
		if(isGripNode()){
			return "Grip it! DO"+getOutput();
		}
		else{
			return "Release it! DO"+getOutput();
		}
		
	}

	@Override
	public boolean isDefined() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		if(isGripNode()){
			writer.appendLine("set_digital_out("+getOutput()+",True)");
			writer.sleep(0.2);
		}
		else{
			writer.appendLine("set_digital_out("+getOutput()+", False)");
			writer.sleep(0.2);
		}
		
	}

	public ProgramAPI getProgramAPI() {
		return this.programAPI;
	}

	
}
