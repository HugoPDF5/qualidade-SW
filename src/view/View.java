package view;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import view.decorator.EnrollStudentDecorator;
import view.decorator.NewCourseDecorator;
import view.decorator.NewPackageDecorator;
import view.decorator.NewTeacherDecorator;
import view.decorator.ServiceItemDecorator;
import view.decorator.class_decorator.NewClassDecorator;
import view.forms.ClassForm;
import view.forms.ServiceItemForm;
import view.forms.StudentForm;
import view.forms.TeacherForm;
import exception.AuthenticationException;
import exception.CPFException;
import exception.ClassException;
import exception.CourseException;
import exception.DateException;
import exception.PersonException;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	private ViewProduct viewProduct = new ViewProduct();
	protected static JFrame frame = new JFrame();
	private PersonView teacherFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void instantiateMenuBar(){
		
		viewProduct.instantiateMenuBar(this);
	}
	
	@SuppressWarnings("deprecation")
	protected boolean getPermissionToAccess(){
		
		return viewProduct.getPermissionToAccess();
	}
	
	/**
	 * Create the frame.
	 */
	public View(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		Toolkit kit = this.getToolkit();
		setBounds(new Rectangle(kit.getScreenSize()));
		
		viewProduct.instantiateMenuBar(this);
	}
	
	protected void showInfoMessage(String message){
		
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
}
