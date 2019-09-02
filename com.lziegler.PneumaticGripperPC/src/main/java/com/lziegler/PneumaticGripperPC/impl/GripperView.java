package com.lziegler.PneumaticGripperPC.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;
import com.ur.urcap.api.domain.SoftwareVersion;


	

public class GripperView implements SwingProgramNodeView<GripperContribution>{

	private final ViewAPIProvider apiProvider;
	private final boolean showGripperLiveControl; 
	public JLabel imageLabel;
	
	public GripperView(ViewAPIProvider apiProvider) {
		System.out.println("Constructing ManipulateIO View-class");
		this.apiProvider = apiProvider;
		// Only show Live Control panel in CB3
		// Toolbar is used for live control in e-Series
		SoftwareVersion version = this.apiProvider.getSystemAPI().getSoftwareVersion();
		if(version.getMajorVersion() >= 5) {
			showGripperLiveControl = false;
		} else {
			showGripperLiveControl = true;
		}
	}
	

	
	private ButtonGroup nodeFunctionButtonGroup = new ButtonGroup();
	private ActionListener nodeFunctionActionListener;
	private JRadioButton gripRadioButton = new JRadioButton("Grip");
	private JRadioButton releaseRadioButton = new JRadioButton("Release");
	private JComboBox<Integer> ioComboBox = new JComboBox<Integer>();
	
	private GripperLiveControl liveControl;
	
	
	
	@Override
	public void buildUI(JPanel panel, ContributionProvider<GripperContribution> provider) {
		// TODO Auto-generated method stub
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(setLogo());
		panel.add(createHorizontalSpacer(5));
		panel.add(createDescriptionLabel("ProCobot gripper software that is allowing user to control a pneumatic gripper "));
		panel.add(createDescriptionLabel("through a digital output. You can test it by clicking GRIP/RELEASE.  "));
		panel.add(createHorizontalSpacer(5));
		panel.add(createHorizontalSpacer(15));
		
		configureNodeFunctionRadioButtons(provider);
		panel.add(createRadioButton("Grip", gripRadioButton, nodeFunctionActionListener));
		panel.add(createHorizontalSpacer(5));
		panel.add(createRadioButton("Release", releaseRadioButton, nodeFunctionActionListener));
		panel.add(createHorizontalSpacer(15));
		panel.add(createIOComboBox(ioComboBox));
		
		if(showGripperLiveControl) {
			liveControl = new GripperLiveControl(provider);
			liveControl.createUI();
			
			TitledBorder liveControlBorder;
			Border border = BorderFactory.createLineBorder(new Color(179, 179, 179));
			liveControlBorder = BorderFactory.createTitledBorder(border, "Live Control");
			liveControlBorder.setTitleJustification(TitledBorder.CENTER);
			liveControl.setBorder(liveControlBorder);
			
			Box box = Box.createVerticalBox();
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			box.add(liveControl);
			
			panel.add(createHorizontalSpacer(15));
			panel.add(box);
		}
	}

	
	public void setRadioButtons(boolean gripActive) {
		gripRadioButton.setSelected(gripActive);
		releaseRadioButton.setSelected(!gripActive);
	}
	
	public void updateLiveControl() {
		if(showGripperLiveControl) {
			liveControl.openView();
		}
	}
	
	public void stopLiveControl() {
		if(showGripperLiveControl) {
			liveControl.closeView();
		}
	}
		
	private void configureNodeFunctionRadioButtons(final ContributionProvider<GripperContribution> provider) {
		nodeFunctionButtonGroup.add(gripRadioButton);
		nodeFunctionButtonGroup.add(releaseRadioButton);
		
		nodeFunctionActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Radio button action listener: "+e.getActionCommand());
				boolean gripSelected = e.getActionCommand().equals("Grip");
				provider.get().setGripNode(gripSelected);
			}
		};
	}
	
	
	/*****
	 * Creates a rigid area with the specified height
	 * @param height The height of the spacer
	 * @return A Component that can be used as a spacer
	 */
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
	

	
	/*****
	 * 
	 * @param label
	 * @param radioButton
	 * @param actionListener
	 * @return
	 */
	private Box createRadioButton(String name, JRadioButton radioButton, ActionListener actionListener) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		radioButton.addActionListener(actionListener);
		radioButton.setActionCommand(name);
		
		box.add(radioButton);
		
		return box;
	}
	
	private Component setLogo() {
		try {
			Component comp = Box.createHorizontalBox();
			BufferedImage image = ImageIO.read(this.getClass().getResource("/icons/procobotbig.png"));
			imageLabel = new JLabel(new ImageIcon(image));
	
		}catch (java.io.IOException e){
			e.printStackTrace();
		}
		return imageLabel;
			
	}
	
	private Box createIOComboBox(final JComboBox<Integer> combo) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel("Digital output: ");
		
		combo.setPreferredSize(new Dimension (105,30));
		combo.setMaximumSize(combo.getPreferredSize());
		combo.addItemListener(new ItemListener()
				{

					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getStateChange() == ItemEvent.SELECTED) {
							
						}
					}
			
				});
				
		box.add(label);
		box.add(combo);
		
		
		return box;
		
	}
	
	
	
	
	//BufferedImage myPicture = ImageIO.read(this.getClass().getResource("logoprocobot.png"));
	//JLabel 
	
	
}
