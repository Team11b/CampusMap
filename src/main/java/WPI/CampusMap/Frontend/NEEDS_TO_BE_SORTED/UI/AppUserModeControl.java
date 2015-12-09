package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Label;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Backend.PathPlanning.Route.Instruction;
import WPI.CampusMap.Backend.PathPlanning.Route.Route;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.UserPointGraphicsObject;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Choice;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class AppUserModeControl extends JComponent {
	public static AppMainWindow window;
	private JTree tree = new JTree();

	@SuppressWarnings("serial")
	public AppUserModeControl(AppMainWindow window) {
		this.window = window;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		PointList scrollPane = new PointList();
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

		tree.setRootVisible(false);
		tree.setModel(null);
		tree.addTreeSelectionListener(new DirectionsSelectionListener());
		scrollPane_1.setViewportView(tree);
		add(label_1);
		
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

	public void setRoute(Path path) 
	{
		Route directions = new Route(path);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Directions");
		
		String currentMap = null;
		DefaultMutableTreeNode mapRoot = null;
		for(Section section : path)
		{
			if(currentMap == null || !section.getMap().equals(currentMap))
			{
				currentMap = section.getMap();
				mapRoot = new MapSectionTreeNode(currentMap, section);
				root.add(mapRoot);
			}
			
			LinkedList<Section> sectionsInCurrentMap = path.getSections(AllMaps.getInstance().getMap(currentMap));
			int routeNumber = sectionsInCurrentMap.indexOf(section) + 1;
			
			PathSectionTreeNode sectionRoot = new PathSectionTreeNode(section, routeNumber);
			mapRoot.add(sectionRoot);
			
			LinkedList<Instruction> instructions = directions.getRoute(section);
			for(Instruction instruction : instructions)
			{
				InstructionTreeNode instructionLeaf = new InstructionTreeNode(instruction, section);
				sectionRoot.add(instructionLeaf);
			}
		}
		
		tree.setModel(new DefaultTreeModel(root));
		tree.setSelectionPath(new TreePath(root.getFirstChild()));
		
		for(int i = 0; i < tree.getRowCount(); i++)
		{
			tree.expandRow(i);
		}
	}
	
	private static class RouteMeActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			window.getUserMode().onRouteButton();
		}

	}
	
	private class DirectionsSelectionListener implements TreeSelectionListener
	{
		@Override
		public void valueChanged(TreeSelectionEvent e) 
		{
			if(e.getPath().getLastPathComponent() instanceof DirectionsBaseTreeNode)
			{
				DirectionsBaseTreeNode node = (DirectionsBaseTreeNode)e.getPath().getLastPathComponent();
				node.onSelected();
			}
		}
		
	}
	
	private class NextButtonSelectionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int[] selected = tree.getSelectionRows();
			if(selected == null || selected.length == 0)
				return;
			
			int selectedIndex = selected[0] + 1;
			if(selectedIndex >= tree.getRowCount())
				selectedIndex = tree.getRowCount() - 1;
			
			tree.setSelectionRow(selectedIndex);
		}
	}
	
	private class PrevButtonSelectionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int[] selected = tree.getSelectionRows();
			if(selected == null || selected.length == 0)
				return;
			
			int selectedIndex = selected[0] - 1;
			if(selectedIndex < 0)
				selectedIndex = 0;
			
			tree.setSelectionRow(selectedIndex);
		}
	}
	
	private abstract class DirectionsBaseTreeNode extends DefaultMutableTreeNode
	{
		public DirectionsBaseTreeNode(Object userObject, boolean allowChildren)
		{
			super(userObject, allowChildren);
		}
		
		public abstract void onSelected();
	}
	
	private class InstructionTreeNode extends DirectionsBaseTreeNode
	{
		private Instruction source;
		private Section section;
		
		public InstructionTreeNode(Instruction source, Section section)
		{
			super(source.getInstruction(), false);
			this.source = source;
			this.section = section;
		}

		@Override
		public void onSelected() 
		{
			window.getUserMode().loadMap(source.getMap());
			window.getUserMode().selectRouteSection(section);
		}
	}
	
	public class PathSectionTreeNode extends DirectionsBaseTreeNode
	{
		private Section section;
		
		public PathSectionTreeNode(Section section, int sectionCount) 
		{
			super(String.format("%s [Route %s]", section.getMap(), sectionCount), true);
			this.section = section;
		}

		@Override
		public void onSelected()
		{
			window.getUserMode().loadMap(section.getMap());
			window.getUserMode().selectRouteSection(section);
		}
	}
	
	public class MapSectionTreeNode extends DirectionsBaseTreeNode
	{
		private String mapName;
		private Section firstSection;
		
		public MapSectionTreeNode(String mapName, Section firstSection) 
		{
			super(mapName, true);
			this.mapName = mapName;
			this.firstSection = firstSection;
		}

		@Override
		public void onSelected() 
		{
			window.getUserMode().loadMap(mapName);
			window.getUserMode().selectRouteSection(firstSection);
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 768017196182798774L;
}
