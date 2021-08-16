package view;


import javax.swing.JComboBox;
import javax.swing.JComboBox;
import java.io.Serializable;
import javax.swing.JPanel;

public class SearchClassProduct implements Serializable {
	private JComboBox<String> cmbCourse;
	private JComboBox<String> cmbTeacher;
	private JComboBox<String> cmbShift;

	public JComboBox<String> getCmbCourse() {
		return cmbCourse;
	}

	public JComboBox<String> getCmbTeacher() {
		return cmbTeacher;
	}

	public JComboBox<String> getCmbShift() {
		return cmbShift;
	}

	/**
	* Create Comboxes.
	*/
	public void createComboBoxes(JPanel thisPanel_1) {
		panel_1(thisPanel_1);
		cmbCourse.addItem("Teste");
	}

	public void panel_1(JPanel thisPanel_1) {
		cmbCourse = new JComboBox<String>();
		cmbCourse.setBounds(67, 7, 186, 24);
		cmbCourse.setSelectedItem(0);
		thisPanel_1.add(cmbCourse);
		cmbTeacher = new JComboBox();
		cmbTeacher.setBounds(114, 38, 139, 24);
		thisPanel_1.add(cmbTeacher);
		cmbShift = new JComboBox();
		cmbShift.setBounds(150, 69, 103, 24);
		thisPanel_1.add(cmbShift);
	}
}