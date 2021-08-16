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
import java.io.Serializable;

public class ViewProductProduct implements Serializable {
	private JMenuBar menuBar;
	private PersonView personFrame;

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void instantiateMenuBar(final View view, ViewProduct viewProduct) {
		menuBar = new JMenuBar();
		view.setJMenuBar(menuBar);
		JMenu homePage = new JMenu("Início");
		menuBar.add(homePage);
		JMenuItem backHomepage = new JMenuItem("Voltar para página inicial");
		backHomepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				SimCta frame = new SimCta();
				frame.setVisible(true);
			}
		});
		homePage.add(backHomepage);
		addCourseOptionsToMenu(view);
		addPackageOptionsToMenu(view);
		addStudentsOptionsToMenu(view);
		addTeacherOptionsToMenu(view);
		try {
			viewProduct.addClassOptionsToMenu(view);
		} catch (CourseException | PersonException | CPFException | ClassException | DateException e) {
			e.printStackTrace();
		}
		addDirectorOptionsToMenu(view);
	}

	public void addTeacherOptionsToMenu(final View view) {
		JMenu teacherMenu = new JMenu("Professores");
		menuBar.add(teacherMenu);
		JMenuItem newTeacher = new JMenuItem("Cadastrar professor");
		newTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				personFrame = new NewTeacherDecorator(new TeacherForm());
				personFrame.buildScreen(personFrame, null);
				personFrame.setVisible(true);
			}
		});
		teacherMenu.add(newTeacher);
		JMenuItem searchTeacher = new JMenuItem("Visualizar professor");
		searchTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				personFrame = new SearchTeacher();
				personFrame.buildScreen(personFrame, null);
				personFrame.setVisible(true);
			}
		});
		teacherMenu.add(searchTeacher);
	}

	public void addStudentsOptionsToMenu(final View view) {
		JMenu studentMenu = new JMenu("Alunos");
		menuBar.add(studentMenu);
		JMenuItem newStudent = new JMenuItem("Matricular aluno");
		newStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				PersonView newStudentFrame = new EnrollStudentDecorator(new StudentForm());
				newStudentFrame.buildScreen(newStudentFrame, null);
				newStudentFrame.setVisible(true);
			}
		});
		studentMenu.add(newStudent);
		JMenuItem searchStudent = new JMenuItem("Visualizar aluno");
		searchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				PersonView searchStudentFrame = new SearchStudent();
				searchStudentFrame.buildScreen(searchStudentFrame, null);
				searchStudentFrame.setVisible(true);
			}
		});
		studentMenu.add(searchStudent);
	}

	public void addDirectorOptionsToMenu(final View view) {
		JMenu directorMenu = new JMenu("Diretor");
		menuBar.add(directorMenu);
		JMenuItem updatePassword = new JMenuItem("Trocar senha");
		updatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean permissionToAccess = getPermissionToAccess();
				if (permissionToAccess) {
					view.dispose();
					UpdateDirectorPassword directorFrame = new UpdateDirectorPassword();
					directorFrame.setVisible(true);
				} else {
					view.dispose();
					View frame = new View();
					frame.setVisible(true);
				}
			}
		});
		directorMenu.add(updatePassword);
	}

	public void addPackageOptionsToMenu(final View view) {
		JMenu packageMenu = new JMenu("Pacotes");
		menuBar.add(packageMenu);
		JMenuItem registerPackage = new JMenuItem("Cadastrar Pacote");
		registerPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean permissionToAccess = false;
				permissionToAccess = getPermissionToAccess();
				if (permissionToAccess == true) {
					view.dispose();
					ServiceItemDecorator newPackageFrame = new NewPackageDecorator(new ServiceItemForm());
					newPackageFrame.buildScreen(newPackageFrame, null);
					newPackageFrame.setVisible(true);
				} else {
					View frame = new View();
					frame.setVisible(true);
				}
			}
		});
		packageMenu.add(registerPackage);
		JMenuItem searchPackage = new JMenuItem("Visualizar Pacote");
		searchPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				ServiceItemView searchPackageFrame = new SearchPackage();
				searchPackageFrame.buildScreen(searchPackageFrame, null);
				searchPackageFrame.setVisible(true);
			}
		});
		packageMenu.add(searchPackage);
	}

	public JMenu courseMenu(final View view) {
		JMenu courseMenu = new JMenu("Cursos");
		JMenuItem registerCourse = new JMenuItem("Cadastrar Curso");
		registerCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean permissionToAccess = false;
				permissionToAccess = getPermissionToAccess();
				if (permissionToAccess == true) {
					view.dispose();
					NewCourseDecorator newCourseFrame = new NewCourseDecorator(new ServiceItemForm());
					newCourseFrame.buildScreen(newCourseFrame, null);
					newCourseFrame.setVisible(true);
				} else {
					View frame = new View();
					frame.setVisible(true);
				}
			}
		});
		courseMenu.add(registerCourse);
		JMenuItem searchCourse = new JMenuItem("Visualizar Curso");
		searchCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.dispose();
				ServiceItemView searchCourseFrame = new SearchCourse();
				searchCourseFrame.buildScreen(searchCourseFrame, null);
				searchCourseFrame.setVisible(true);
			}
		});
		courseMenu.add(searchCourse);
		return courseMenu;
	}

	@SuppressWarnings("deprecation")
	public boolean getPermissionToAccess() {
		boolean canAccess = false;
		String messageToUser = "";
		String enteredPassword = "senha digitada";
		final Object[] inputPassword;
		final JPasswordField enteredPasswordField;
		final JLabel passwordLabel;
		passwordLabel = new JLabel("Digite a senha:");
		enteredPasswordField = new JPasswordField();
		inputPassword = new Object[] { passwordLabel, enteredPasswordField };
		while (canAccess == false) {
			AuthenticationView authenticationFrame = new AuthenticationView();
			int verify = authenticationFrame.showConfirmDialog(null, inputPassword, "Senha:",
					authenticationFrame.OK_CANCEL_OPTION, authenticationFrame.PLAIN_MESSAGE);
			enteredPassword = enteredPasswordField.getText();
			if (verify == JOptionPane.OK_OPTION) {
				if (enteredPassword != null) {
					try {
						canAccess = authenticationFrame.authenticateUser(enteredPassword);
					} catch (AuthenticationException | SQLException e) {
						messageToUser = e.toString();
						int indexToSepare = messageToUser.indexOf(":");
						messageToUser = messageToUser.substring(indexToSepare + 2);
						authenticationFrame.showMessageDialog(null, messageToUser);
					}
				}
			} else if (verify == JOptionPane.CANCEL_OPTION) {
				break;
			}
		}
		return canAccess;
	}

	public void addCourseOptionsToMenu(View view) {
		JMenu courseMenu = courseMenu(view);
		menuBar.add(courseMenu);
	}
}