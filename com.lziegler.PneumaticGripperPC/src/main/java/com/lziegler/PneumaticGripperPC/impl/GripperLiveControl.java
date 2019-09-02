package com.lziegler.PneumaticGripperPC.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.domain.ApplicationAPI;
import com.ur.urcap.api.domain.io.AnalogIO;
import com.ur.urcap.api.domain.io.DigitalIO;

public class GripperLiveControl extends JPanel {

	private static final long serialVersionUID = 1L;
	private final ContributionProvider<GripperContribution> provider;
	private final ApplicationAPI applicationAPI;
	private JComboBox<Integer> ioComboBox = new JComboBox<Integer>();
	
	
	
	private enum ContributionType {
		TOOLBAR,
		PROGRAMNODE
	}
	private final ContributionType contributionType;
	
	private IOHandler ioHandler;
	private DigitalIO digital_out0;
	private DigitalIO digital_out1;
	private DigitalIO digital_out2;
	private DigitalIO digital_out3;
	private DigitalIO digital_out4;
	private DigitalIO digital_out5;
	private DigitalIO digital_out6;
	private DigitalIO digital_out7;
	
	
	public GripperLiveControl(ApplicationAPI applicationAPI) {
		this.provider = null;
		this.applicationAPI = applicationAPI; 
		
		this.contributionType = ContributionType.TOOLBAR;
		
	}
	
	/*****
	 * Constructor when called in a Program Node
	 * @param programAPI
	 */
	public GripperLiveControl(ContributionProvider<GripperContribution> provider) {
		this.provider = provider;
		this.applicationAPI = null; 
		
		this.contributionType = ContributionType.PROGRAMNODE;
	}
		
		private void InitializeIO() {
			if(contributionType.equals(ContributionType.TOOLBAR)) {
				this.ioHandler = new IOHandler(applicationAPI.getIOModel());
			} else if (contributionType.equals(ContributionType.PROGRAMNODE)) {
				this.ioHandler = new IOHandler(provider.get().getProgramAPI().getIOModel());
			}
			
			digital_out0 = 	ioHandler.getDigitalIO("digital_out[0]");
			digital_out1 = 	ioHandler.getDigitalIO("digital_out[1]");
			digital_out2 = 	ioHandler.getDigitalIO("digital_out[2]");
			digital_out3 = 	ioHandler.getDigitalIO("digital_out[3]");
			digital_out4 = 	ioHandler.getDigitalIO("digital_out[4]");
			digital_out5 = 	ioHandler.getDigitalIO("digital_out[5]");
			digital_out6 = 	ioHandler.getDigitalIO("digital_out[6]");
			digital_out7 = 	ioHandler.getDigitalIO("digital_out[7]");
			
		}
		

		
	
		
		public void createUI() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.add(createIOComboBox(ioComboBox, provider));
			this.add(createDescriptionLabel("Click GRIP or RELEASE below, to test the gripper:"));
			this.add(createHorizontalSpacer(25));
			this.add(createGripperTestButtons("GRIP", "RELEASE"));
			
		}
		
		public void openView() {
			if(ioHandler==null) {
				InitializeIO();}
			}
			
			public void closeView() {
				
			}	
		
		
		
		private Component createHorizontalSpacer(int height){
			Component spacer = Box.createRigidArea(new Dimension(0, height));
			return spacer;
		}
		
		/*****
		 * Creates a Box with a Label containing the specified text
		 * @param text The specified text
		 * @return The creates box
		 */
		private Box createDescriptionLabel(String text) {
			Box box = Box.createHorizontalBox();
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JLabel label = new JLabel(text);
			
			box.add(label);
			
			return box;
		}
		
		private Box createGripperTestButtons(String text1, String text2) {
			Box box = Box.createHorizontalBox();
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JButton button1 = new JButton(text1);
			JButton button2 = new JButton(text2);
			
			Dimension buttonSize = new Dimension(200, 50);
			
			button1.setPreferredSize(buttonSize);
			button1.setMinimumSize(button1.getPreferredSize());
			button1.setMaximumSize(button1.getPreferredSize());
			button2.setPreferredSize(buttonSize);
			button2.setMinimumSize(button2.getPreferredSize());
			button2.setMaximumSize(button2.getPreferredSize());
			
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					testGrip();
				}
			});
			
			button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					testRelease();
				}
			});
			
			box.add(button1);
			box.add(button2);
			
			return box;
		}
		
		private Box createStatusLabel(String description, JLabel status) {
			Box box = Box.createHorizontalBox();
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JLabel desc = new JLabel(description);
			desc.setPreferredSize(new Dimension(200, 20));
			desc.setMinimumSize(desc.getPreferredSize());
			desc.setMaximumSize(desc.getPreferredSize());
			
			status.setPreferredSize(new Dimension(200, 20));
			status.setMinimumSize(status.getPreferredSize());
			status.setMaximumSize(status.getPreferredSize());
			
			box.add(desc);
			box.add(status);
			
			return box;
		}
		
		private Box createIOComboBox(final JComboBox<Integer> combo, final ContributionProvider<GripperContribution> provider) {
			Box box = Box.createHorizontalBox();
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JLabel label = new JLabel("Output's No.: ");
			
			combo.setPreferredSize(new Dimension (105,30));
			combo.setMaximumSize(combo.getPreferredSize());
			combo.addItemListener(new ItemListener()
					{

						@Override
						public void itemStateChanged(ItemEvent e) {
							// TODO Auto-generated method stub
							if(e.getStateChange() == ItemEvent.SELECTED) {
								provider.get().onOutputSelection((Integer) e.getItem());
							}
//							else if(e.getStateChange() == ItemEvent.DESELECTED) {
//								
//							}
						}
				
					});
					
			box.add(label);
			box.add(combo);
			
			
			return box;
			
		}
		
		

		public void setIOComboBoxItems(Integer[] items) {
			ioComboBox.removeAllItems();
			ioComboBox.setModel(new DefaultComboBoxModel<Integer>(items));
		}
		
		public void setIOComboBoxSelection(Integer item) {
			ioComboBox.setSelectedItem(item);
		}
		
		private void testGrip(){
			if(digital_out0!=null){
				digital_out0.setValue(true);
				
			}
		}
		
		private void testRelease(){
			if(digital_out0!=null){
				digital_out0.setValue(false);
			}
		}
		
}
