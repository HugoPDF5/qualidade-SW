package view;


import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import exception.CourseException;
import exception.PersonException;
import exception.CPFException;
import exception.ClassException;
import exception.DateException;
import view.decorator.NewTeacherDecorator;
import view.forms.TeacherForm;
import view.decorator.EnrollStudentDecorator;
import view.forms.StudentForm;
import view.decorator.ServiceItemDecorator;
import view.decorator.NewPackageDecorator;
import view.forms.ServiceItemForm;
import view.decorator.NewCourseDecorator;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import exception.AuthenticationException;
import java.sql.SQLException;
import view.decorator.class_decorator.NewClassDecorator;
import view.forms.ClassForm;
import java.io.Serializable;

public class ViewProduct implements Serializable {
	private ViewProductProduct viewProductProduct = new ViewProductProduct();
	private ClassView classFrame;
	public void instantiateMenuBar(final View view) {
		viewProductProduct.instantiateMenuBar(view, this);
	}

	public void addTeacherOptionsToMenu(final View view) {
		viewProductProduct.addTeacherOptionsToMenu(view);
	}

	public void addStudentsOptionsToMenu(final View view) {
		viewProductProduct.addStudentsOptionsToMenu(view);
	}

	public void addDirectorOptionsToMenu(final View view) {
		viewProductProduct.addDirectorOptionsToMenu(view);
	}

	public void addPackageOptionsToMenu(final View view) {
		viewProductProduct.addPackageOptionsToMenu(view);
	}

	public JMenu courseMenu(final View view) {
		return viewProductProduct.courseMenu(view);
	}

	@SuppressWarnings("deprecation")
	public boolean getPermissionToAccess() {
		return viewProductProduct.getPermissionToAccess();
	}

	public void addCourseOptionsToMenu(View view) {
		viewProductProduct.addCourseOptionsToMenu(view);
	}

	public void addClassOptionsToMenu(final View view)
			throws CourseException, PersonException, CPFException, ClassException, DateException {
		JMenu classMenu = new JMenu("Turmas");
		viewProductProduct.getMenuBar().add(classMenu);
		JMenuItem newClass = new JMenuItem("Abrir turma");
		newClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				classFrame = new NewClassDecorator(new ClassForm());
				classFrame.buildScreen(classFrame, null);
				classFrame.setVisible(true);
			}
		});
		classMenu.add(newClass);
		JMenuItem searchClass = new JMenuItem("Visualizar turma");
		searchClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.dispose();
				SearchClass searchClassFrame;
				searchClassFrame = new SearchClass();
				searchClassFrame.setVisible(true);
			}
		});
		classMenu.add(searchClass);
	}
}