package WPI.CampusMap.Frontend.UI;

import java.awt.Choice;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

public class AppDevModeControl extends JComponent {
	private AppMainWindow window;

	public AppDevModeControl(AppMainWindow window) {
		this.window = window;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		AppDevModePointEditorControl panel = new AppDevModePointEditorControl(window);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, this);
		add(panel);

		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, this);
		add(separator);

		JButton btnSave = new JButton("Save");
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.NORTH, btnSave);
		btnSave.setToolTipText("Save the current map");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 6, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 80, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -80, SpringLayout.EAST, this);
		add(btnSave);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
}
