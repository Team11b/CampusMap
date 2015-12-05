package WPI.CampusMap.UI;

import java.awt.Panel;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;

public class DestinationsList extends Panel
{
	public DestinationsList()
	{
		setForeground(SystemColor.controlShadow);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnAdd = new JButton("Add");
		springLayout.putConstraint(SpringLayout.WEST, btnAdd, 108, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAdd, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnAdd, -108, SpringLayout.EAST, this);
		add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -7, SpringLayout.NORTH, btnAdd);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);
		
		Panel listPanel = new Panel();
		scrollPane.setViewportView(listPanel);
		listPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898176351964283864L;

}
