package WPI.CampusMap.Frontend.UI;

import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class PointList extends Panel {
	public PointList() {
		setForeground(SystemColor.controlShadow);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		// TODO: Add in
		/*
		 * JButton btnAdd = new JButton("Add"); btnAdd.setToolTipText(
		 * "Add a destination to your trip"); btnAdd.addActionListener(new
		 * AddButtonActionListener());
		 * springLayout.putConstraint(SpringLayout.WEST, btnAdd, 108,
		 * SpringLayout.WEST, this);
		 * springLayout.putConstraint(SpringLayout.SOUTH, btnAdd, -10,
		 * SpringLayout.SOUTH, this);
		 * springLayout.putConstraint(SpringLayout.EAST, btnAdd, -108,
		 * SpringLayout.EAST, this); add(btnAdd);
		 */

		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);

		listPanel = new JPanel();
		scrollPane.setViewportView(listPanel);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}

	public void addListener(PointListEventHandler handler) {
		handlers.add(handler);
	}

	public PointListElement addPointElement(String name) {
		if (elements.containsKey(name))
			return null;
		PointListElement element;
		element = new PointListElement(this, name);
		elements.put(name, element);
		listPanel.add(element);
		listPanel.revalidate();
		listPanel.repaint();
		for (PointListEventHandler handler : handlers) {
			handler.pointDescriptorAdded(element);
		}
		
		//For removing underscores
		renamePointElement(name, name.replace('_', ' '));
		guiRenameElement(element, name);
		renamePointElement(name.replace(' ', '_'), name);
		
		return element;
	}

	public void removePointElement(String name) {
		if (!elements.containsKey(name))
			return;

		PointListElement element = elements.get(name);
		listPanel.remove(element);
		elements.remove(name);

		listPanel.revalidate();
		listPanel.repaint();

		for (PointListEventHandler handler : handlers) {
			handler.pointDescriptorRemoved(element);
		}
	}

	public void clearPointElements() {
		for (PointListElement element : elements.values()) {
			listPanel.remove(element);
		}

		listPanel.revalidate();
		listPanel.repaint();
	}

	public void gotoPointElement(String name) {
		if (!elements.containsKey(name))
			return;

		PointListElement element = elements.get(name);

		for (PointListEventHandler handler : handlers) {
			handler.pointDescriptorShow(element);
		}
	}

	public void renamePointElement(String oldName, String newName) {
		if (!elements.containsKey(oldName))
			return;

		PointListElement element = elements.get(oldName);

		element.setPointName(newName);
	}

	protected void guiRenameElement(PointListElement element, String oldName) {
		if (!elements.containsKey(oldName))
			return;

		elements.remove(oldName);
		elements.put(element.getName(), element);

		for (PointListEventHandler handler : handlers) {
			handler.pointDescriptorRenamed(element, oldName);
		}
	}

	private class AddButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addPointElement("");
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898176351964283864L;
	private JPanel listPanel;
	private JScrollPane scrollPane;
	private LinkedList<PointListEventHandler> handlers = new LinkedList<>();

	private HashMap<String, PointListElement> elements = new HashMap<>();
}
