package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.ScrollPane;
import javax.swing.JPanel;

public class PointList extends Panel
{
	public PointList()
	{
		setForeground(SystemColor.controlShadow);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new AddButtonActionListener());
		springLayout.putConstraint(SpringLayout.WEST, btnAdd, 108, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAdd, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnAdd, -108, SpringLayout.EAST, this);
		add(btnAdd);
		
		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -7, SpringLayout.NORTH, btnAdd);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);
		
		listPanel = new JPanel();
		scrollPane.setViewportView(listPanel);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}
	
	private void addPointElement()
	{
		PointListElement element = new PointListElement();
		listPanel.add(element);
		
		listPanel.revalidate();
		listPanel.repaint();
	}
	
	private class AddButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			addPointElement();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898176351964283864L;
	private JPanel listPanel;
	private JScrollPane scrollPane;

}
