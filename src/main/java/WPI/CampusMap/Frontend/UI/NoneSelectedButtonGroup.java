package WPI.CampusMap.Frontend.UI;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

@SuppressWarnings("serial")
public class NoneSelectedButtonGroup extends ButtonGroup
{
	@Override
	public void setSelected(ButtonModel m, boolean b)
	{
		if (b)
		{
			super.setSelected(m, b);
	    } 
		else if (getSelection() == m)
		{
			clearSelection();
	    }
	}
}
