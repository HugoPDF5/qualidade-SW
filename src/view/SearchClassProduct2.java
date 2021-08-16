package view;


import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.io.Serializable;

public class SearchClassProduct2 implements Serializable {
	private JRadioButton rbtnCode;
	private JRadioButton rbtnFilter;

	public JRadioButton getRbtnCode() {
		return rbtnCode;
	}

	public void setRbtnCode(JRadioButton rbtnCode) {
		this.rbtnCode = rbtnCode;
	}

	public JRadioButton getRbtnFilter() {
		return rbtnFilter;
	}

	public void setRbtnFilter(JRadioButton rbtnFilter) {
		this.rbtnFilter = rbtnFilter;
	}

	/**
	* Create buttons and listeners.
	*/
	public void createButtonsAndListeners(SearchClass searchClass) {
		searchClass.panel();
		ButtonGroup group = new ButtonGroup();
		group.add(rbtnCode);
		group.add(rbtnFilter);
	}
}