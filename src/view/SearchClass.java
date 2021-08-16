package view;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.ClassController;
import exception.ClassException;
import exception.CourseException;
import exception.StudentException;
import model.Class;
import util.ButtonColumn;
import view.decorator.class_decorator.NewClassDecorator;
import view.decorator.class_decorator.ShowStudentsClassDecorator;
import view.forms.ClassForm;
import view.forms.ClassShowForm;

public class SearchClass extends View {

	private SearchClassProduct2 searchClassProduct2 = new SearchClassProduct2();
	private SearchClassProduct searchClassProduct = new SearchClassProduct();
	private JPanel panel_0;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField txtCodigo;
	private JScrollPane scrollPane;
	private TableModel tableModel;
	private JTable jTable;
	private JButton btnSearch;
	
	private ClassController classController;
	private ClassView classFrame;


	/**
	 * Contructor's method of SearchClass.
	 */
	public SearchClass() {

		super();

		getContentPane().setLayout(null);

		initialize();
		searchClassProduct2.getRbtnCode().setSelected(true);
		txtCodigo.requestFocus();
		searchClassProduct2.getRbtnFilter().setEnabled(false);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		createStructOfWindow();

		createLabelsAndFields();

		searchClassProduct.createComboBoxes(this.panel_1);

		searchClassProduct2.createButtonsAndListeners(this);

		createStructOfResultTable();

	}

	/**
	 * Initialize the struct of the frame.
	 */
	public void createStructOfWindow() {
		panel_0 = new JPanel();
		panel_0.setBounds(100, 100, 788, 617);
		setContentPane(panel_0);
		panel_0.setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(134, 39, 525, 160);
		panel_0.add(panel);
		panel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(90, 41, 296, 102);
		panel.add(panel_1);
		panel_1.setLayout(null);

	}

	/**
	 * Create labels and fields.
	 */
	public void createLabelsAndFields() {
		JLabel lblConsultarTurma = new JLabel("Consultar Turma");
		lblConsultarTurma.setBounds(268, 12, 124, 15);
		panel_0.add(lblConsultarTurma);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(90, 10, 154, 23);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setBounds(12, 12, 48, 15);
		panel_1.add(lblCurso);

		JLabel lblProfessor = new JLabel("Professor");
		lblProfessor.setBounds(12, 43, 70, 15);
		panel_1.add(lblProfessor);

		JLabel lblTurno = new JLabel("Turno");
		lblTurno.setBounds(12, 74, 70, 15);
		panel_1.add(lblTurno);

	}

	/**
	 * Create Comboxes.
	 */
	public void createComboBoxes() {
		searchClassProduct.createComboBoxes(this.panel_1);

	}

	/**
	 * Create buttons and listeners.
	 */
	public void createButtonsAndListeners() {

		searchClassProduct2.createButtonsAndListeners(this);

	}

	public void panel() throws HeadlessException {
		searchClassProduct2.setRbtnCode(new JRadioButton("Código"));
		searchClassProduct2.getRbtnCode().setBounds(8, 8, 74, 23);
		panel.add(searchClassProduct2.getRbtnCode());
		searchClassProduct2.setRbtnFilter(new JRadioButton("Filtros"));
		searchClassProduct2.getRbtnFilter().setBounds(8, 35, 81, 23);
		panel.add(searchClassProduct2.getRbtnFilter());
		JButton btnSearch = new JButton("Pesquisar");
		btnSearch.setBounds(398, 34, 117, 25);
		panel.add(btnSearch);
		searchClassProduct2.getRbtnCode().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					searchClassProduct.getCmbCourse().setEnabled(false);
					searchClassProduct.getCmbTeacher().setEnabled(false);
					searchClassProduct.getCmbShift().setEnabled(false);
					txtCodigo.setEnabled(true);
					txtCodigo.requestFocus();
				}
			}
		});
		searchClassProduct2.getRbtnFilter().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				searchClassProduct.getCmbCourse().setEnabled(true);
				searchClassProduct.getCmbTeacher().setEnabled(true);
				searchClassProduct.getCmbShift().setEnabled(true);
				txtCodigo.setEnabled(false);
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetTable();
				if (searchClassProduct2.getRbtnCode().isSelected()) {
					classController = new ClassController();
					try {
						ArrayList<Class> cls = classController.searchClass(txtCodigo.getText().toString());
						if (cls == null) {
							JOptionPane.showMessageDialog(searchClassProduct2.getRbtnCode(), "Turma não encontrada.");
						} else {
							int arrayListSize = cls.size();
							int i = 0;
							while (i < arrayListSize) {
								String[] classeString = cls.get(i).getLineOfStringByClass();
								((DefaultTableModel) tableModel).addRow(classeString);
								i++;
							}
						}
					} catch (ClassException e1) {
						JOptionPane.showMessageDialog(frame, "Ocorreu um erro:" + e1.getMessage());
					}
				} else if (searchClassProduct2.getRbtnFilter().isSelected()) {
					JOptionPane.showMessageDialog(frame, "Teste3");
				}
			}
		});
	}
	
	/** 
	 * Create a struct of result table
	 */
	public void createStructOfResultTable() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 217, 1000, 317);
		panel_0.add(scrollPane);
		scrollPane.setBackground(Color.WHITE);

		JTable tableOfClasses = tableOfClasses();
		scrollPane.setViewportView(tableOfClasses);

		jTable = tableOfClasses;
		
		((JScrollPane) scrollPane).setViewportView(tableOfClasses);
		
	}

	private JTable tableOfClasses() {
		String[] columns = { "Código", "Curso", "Professor", "Turno", "Início", "Término", "Editar",
				"Visualizar alunos", "Matricular alunos" };
		tableModel = new DefaultTableModel(null, columns);
		final JTable tableOfClasses = new JTable(tableModel);
		tableOfClasses.setBackground(Color.WHITE);
		Action editClass = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int selectedRow = table.getSelectedRow();
				String code = table.getModel().getValueAt(selectedRow, 0).toString();
				Class cls = classController.getClass(code);
				dispose();
				classFrame = new NewClassDecorator(new ClassForm());
				classFrame.buildScreen(classFrame, null);
				classFrame.setVisible(true);
			}
		};
		ButtonColumn buttonColumn2 = new ButtonColumn(tableOfClasses, editClass, 6);
		Action showStudentsClass = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int selectedRow = table.getSelectedRow();
				String code = table.getModel().getValueAt(selectedRow, 0).toString();
				Class enrolledClass = classController.getClass(code);
				dispose();
				classFrame = new ShowStudentsClassDecorator(new ClassShowForm(), SearchClass.this);
				classFrame.buildScreen(classFrame, enrolledClass);
				classFrame.setVisible(true);
			}
		};
		ButtonColumn buttonColumn3 = new ButtonColumn(tableOfClasses, showStudentsClass, 7);
		Action enrollStudents = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int selectedRow = table.getSelectedRow();
				String code = table.getModel().getValueAt(selectedRow, 0).toString();
				Class enrollClass = classController.getClass(code);
				dispose();
				EnrollStudentToClass enrollFrame;
				try {
					enrollFrame = new EnrollStudentToClass(enrollClass, SearchClass.this);
					enrollFrame.setVisible(true);
				} catch (CourseException | SQLException | StudentException e1) {
					showInfoMessage(e1.getMessage());
					SearchClass.this.setVisible(true);
				}
			}
		};
		ButtonColumn buttonColumn4 = new ButtonColumn(tableOfClasses, enrollStudents, 8);
		return tableOfClasses;
	}

	/**
	 * Used to reset the table of result.
	 */
	public void resetTable() {
		DefaultTableModel model = (DefaultTableModel) jTable.getModel();
		model.setRowCount(0);
	}
	
}
