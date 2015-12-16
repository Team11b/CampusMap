package WPI.CampusMap.Frontend.UI;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Backend.PathPlanning.Route.Instruction;
import WPI.CampusMap.Backend.PathPlanning.Route.Route;
import WPI.CampusMap.Frontend.Graphics.User.UserPointGraphicsObject;

public class AppUserModeControl extends JComponent {
	public static AppMainWindow window;
	private JTree tree = new JTree();

	PointList scrollPane = new PointList();

	/** Creates a new User Mode Control in the given window.
	 * @param window Window to create the User Mode Control
	 */
	@SuppressWarnings("static-access")
	public AppUserModeControl(AppMainWindow window) {
		this.window = window;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		scrollPane.addListener(new DestinationListListener());
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		add(scrollPane);

		JButton routeButton = new JButton("Route Me!");
		routeButton.setToolTipText("Get turn-by-turn directions between each point");
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.NORTH, routeButton);
		springLayout.putConstraint(SpringLayout.NORTH, routeButton, 373, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, routeButton, 54, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, routeButton, -61, SpringLayout.EAST, this);
		routeButton.setIcon(new ImageIcon(
				AppUserModeControl.class.getResource("/javax/swing/plaf/metal/icons/ocean/expanded.gif")));
		routeButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(routeButton);
		routeButton.addActionListener(new RouteMeActionListener());

		Label label = new Label("Destinations");
		springLayout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, scrollPane);
		add(label);

		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.NORTH, separator, 19, SpringLayout.SOUTH, routeButton);
		springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, 27, SpringLayout.SOUTH, routeButton);
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, this);
		add(separator);

		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 25, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, this);
		add(scrollPane_1);

		Label label_1 = new Label("Directions");
		springLayout.putConstraint(SpringLayout.SOUTH, label_1, 0, SpringLayout.NORTH, scrollPane_1);
		springLayout.putConstraint(SpringLayout.WEST, label_1, 10, SpringLayout.WEST, this);

		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		Icon closedIcon = new ImageIcon("icon.png");
		Icon openIcon = new ImageIcon("icon.png");
		Icon leafIcon = new ImageIcon("icon.png");
		renderer.setClosedIcon(closedIcon);
		renderer.setOpenIcon(openIcon);
		renderer.setLeafIcon(leafIcon);
		System.out.println("H: " + openIcon.getIconHeight() + "W: " + openIcon.getIconWidth());

		tree.setRootVisible(false);
		tree.setModel(null);
		tree.addTreeSelectionListener(new DirectionsSelectionListener());
		tree.setCellRenderer(new CustomCellRenderer());
		scrollPane_1.setViewportView(tree);
		add(label_1);

		JButton expandAllBtn = new JButton("Expand All");
		springLayout.putConstraint(SpringLayout.NORTH, expandAllBtn, 0, SpringLayout.NORTH, label_1);
		springLayout.putConstraint(SpringLayout.SOUTH, expandAllBtn, -1, SpringLayout.SOUTH, label_1);
		expandAllBtn.addActionListener(new ExpandAllButtonListener());
		JButton collapseAllBtn = new JButton("Collapse All");
		springLayout.putConstraint(SpringLayout.NORTH, collapseAllBtn, 0, SpringLayout.NORTH, expandAllBtn);
		springLayout.putConstraint(SpringLayout.SOUTH, collapseAllBtn, 0, SpringLayout.SOUTH, expandAllBtn);
		collapseAllBtn.addActionListener(new CollapseAllButtonListener());
		
		springLayout.putConstraint(SpringLayout.WEST, collapseAllBtn, 180, SpringLayout.WEST, this);
		
		springLayout.putConstraint(SpringLayout.WEST, expandAllBtn, 80, SpringLayout.WEST, this);
		//springLayout.putConstraint(SpringLayout.EAST, label_1, 10, SpringLayout.WEST, expandAllBtn);
		
		add(expandAllBtn);
		add(collapseAllBtn);
		
		Panel panel = new Panel();
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, -10, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.NORTH, panel, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, scrollPane);
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new PrevButtonSelectionListener());
		panel.add(btnPrevious);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new NextButtonSelectionListener());
		panel.add(btnNext);
	}

	/** Sets the directions for the given path.
	 * @param path The directions for the given path.
	 */
	public void setRoute(Path path) {
		Route directions = new Route(path);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Directions");

		String currentMap = null;
		DefaultMutableTreeNode mapRoot = null;
		for (Section section : path) {
			if (currentMap == null || !section.getMap().equals(currentMap)) {
				currentMap = section.getMap();
				section.getDisplayName();
				mapRoot = new MapSectionTreeNode(currentMap, section);
				mapRoot.getUserObject();
				root.add(mapRoot);
			}

			LinkedList<Section> sectionsInCurrentMap = path.getSections(AllMaps.getInstance().getMap(currentMap));
			int routeNumber = sectionsInCurrentMap.indexOf(section) + 1;

			PathSectionTreeNode sectionRoot = new PathSectionTreeNode(section, routeNumber);
			mapRoot.add(sectionRoot);

			LinkedList<Instruction> instructions = directions.getRoute(section);
			for (Instruction instruction : instructions) {
				InstructionTreeNode instructionLeaf = new InstructionTreeNode(instruction, section);
				sectionRoot.add(instructionLeaf);
			}
		}

		tree.setModel(new DefaultTreeModel(root));
		tree.setSelectionPath(new TreePath(root.getFirstChild()));

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	/** Adds a destination
	 * @param point Point to add as destination.
	 */
	public void addDestination(UserPointGraphicsObject point)
	{
		scrollPane.addPoint(point.getRepresentedObject());
	}

	/**
	 * Clears the destination.
	 */
	public void clearDestinations()
	{
		scrollPane.clearPointDescriptors();
	}

	/**
	 * Calls the RouteMe method. Calculates the route from the selected nodes.
	 */
	private static class RouteMeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			window.getUserMode().onRouteButton();
		}

	}

	private class DirectionsSelectionListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			if (e.getPath().getLastPathComponent() instanceof DirectionsBaseTreeNode) {
				DirectionsBaseTreeNode node = (DirectionsBaseTreeNode) e.getPath().getLastPathComponent();
				node.onSelected();
			}
		}

	}

	/**
	 * Collapses all of the rows in the Directions tree.
	 * @author Will Spurgeon
	 *
	 */
	private class CollapseAllButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			collapseTree();
		}
	}
	
	/**
	 * Expands all of the rows in the Directions tree.
	 * @author Will Spurgeon
	 *
	 */
	private class ExpandAllButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			expandTree();
		}
	}
	
	/**
	 * Selects the next direction in the Directions tree.
	 */
	private class NextButtonSelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int[] selected = tree.getSelectionRows();
			if (selected == null || selected.length == 0)
				return;

			int selectedIndex = selected[0] + 1;
			if (selectedIndex >= tree.getRowCount())
				selectedIndex = tree.getRowCount() - 1;

			tree.setSelectionRow(selectedIndex);
		}
	}

	/**
	 * Selects the previous direction in the Directions tree.
	 */
	private class PrevButtonSelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int[] selected = tree.getSelectionRows();
			if (selected == null || selected.length == 0)
				return;

			int selectedIndex = selected[0] - 1;
			if (selectedIndex < 0)
				selectedIndex = 0;

			tree.setSelectionRow(selectedIndex);
		}
	}

	private class DestinationListListener implements PointListEventHandler {

		@Override
		public void pointDescriptorAdded(PointListElement element) {
			window.getUserMode().onPointDescriptorAddedToDestinations(element.getCurrentName(), element.getIndex());
		}

		@Override
		public void pointDescriptorRemoved(PointListElement element) {
			window.getUserMode().onPointRemovedFromDestinations(element.getCurrentName());
		}

		@Override
		public boolean pointDescriptorNameCheck(PointListElement element, String newName) {
			return window.getUserMode().onCheckPointName(newName);
		}

		@Override
		public void pointDescriptorRenamed(PointListElement element, String oldName) {
			window.getUserMode().onPointDescriptorRenamedDestination(oldName, element.getCurrentName(),
					element.getIndex());
		}

		@Override
		public void pointDescriptorNameCheckFailed(PointListElement element, String failedName) {
			// TODO Auto-generated method stub

		}

		@Override
		public void pointDescriptorShow(PointListElement element) {
			// TODO Auto-generated method stub

		}

		@Override
		public void pointDescriptorMoved(PointListElement element) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Specialized class for the Directions node tree.
	 */
	private abstract class DirectionsBaseTreeNode extends DefaultMutableTreeNode {
		/**
		 * 
		 */
		private static final long serialVersionUID = 123464203285703937L;

		public DirectionsBaseTreeNode(Object userObject, boolean allowChildren) {
			super(userObject, allowChildren);
		}

		public abstract void onSelected();
	}

	private class InstructionTreeNode extends DirectionsBaseTreeNode {
		/**
		 * Implementation of a Direction tree node.
		 */
		private static final long serialVersionUID = 9130117587484385937L;
		private Instruction source;
		private Section section;

		public InstructionTreeNode(Instruction source, Section section) {
			super(source.getInstruction(), false);
			this.source = source;
			this.section = section;
		}

		@Override
		public void onSelected() {
			window.forceMapSwitch(section.getMap());
			window.getUserMode().selectRouteSection(section);
			if (source.getStart() == null)
				return;
			window.getUserMode().selectCurrentNode(source.getStart());
		}
	}

	public class PathSectionTreeNode extends DirectionsBaseTreeNode {
		/**
		 * Implementation of a tree node for displaying a path.
		 */
		private static final long serialVersionUID = 8463985454935131567L;
		private Section section;

		public PathSectionTreeNode(Section section, int sectionCount) {
			super(String.format("%s [Route %s]", section.getDisplayName(), sectionCount), true);
			this.section = section;
		}

		@Override
		public void onSelected() {
			window.forceMapSwitch(section.getMap());
			window.getUserMode().selectRouteSection(section);
		}
	}

	public class MapSectionTreeNode extends DirectionsBaseTreeNode {
		/**
		 * Implementation of a tree node for displaying a map section.
		 */
		private static final long serialVersionUID = 2289516290900523443L;
		private String mapName;
		private Section firstSection;

		public MapSectionTreeNode(String mapName, Section firstSection) {
			super(firstSection.getDisplayName(), true);
			this.mapName = mapName;
			this.firstSection = firstSection;
		}

		@Override
		public void onSelected() {
			window.forceMapSwitch(mapName);
			window.getUserMode().selectRouteSection(firstSection);
		}
	}

	/**
	 * Expands all of the rows in the Directions tree.
	 * 
	 * @author Will Spurgeon
	 *
	 */
	private void expandTree() {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	/**
	 * Collapses all of the rows in the Directions tree.
	 * 
	 * @author Will Spurgeon
	 *
	 */
	private void collapseTree() {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.collapseRow(i);
		}
	}

	private static final long serialVersionUID = 768017196182798774L;
}
