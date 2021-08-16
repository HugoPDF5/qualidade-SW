package view.decorator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.Payment;
import model.PaymentDescription;
import model.Person;
import model.Service;
import model.ServiceItem;
import model.Student;
import view.EditStudents;
import view.EnrollStudentInMoreCourses;
import view.PersonView;
import view.SearchStudent;
import view.forms.StudentForm;
import controller.ServiceController;
import controller.StudentController;
import datatype.Address;
import datatype.CPF;
import datatype.Date;
import datatype.Phone;
import datatype.RG;
import exception.ServiceException;
import exception.StudentException;

public class ShowStudentDecorator extends PersonDecorator {

	private JButton editStudentBtn;
	private JButton backBtn;
	private Student student;
	private DefaultTableModel tableModel;
	private JInternalFrame internalFrame;
	private JLabel dateLabel;
	private JTextField paymentFormField;
	private JTextField paymentValueField;
	private JTextField installmentsValueField;
	private JTextField paymentInstallmentsField;
	private JButton deactivateOrActivateButton;
	private JButton addMoreCoursesButton;
	private int status;
	private String action;
	private DefaultTableModel courseTableModel;
	private JTable tableOfAddedCourses;
	private DefaultTableModel packageTableModel;
	private JTable tableOfAddedPackages;

	public ShowStudentDecorator(PersonView viewToDecorate) {
		super(viewToDecorate);
	}

	@Override
	public void createLabelsAndFields(JFrame viewToDecorate, Person student) {
		
		this.frame = viewToDecorate;
		super.createLabelsAndFields(viewToDecorate, student);
		courseTableModel(student);
		registerPersonLbl.setText(student.getName());
		registerPersonLbl.setBounds(407, 12, 475, 31);
		registerPersonLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		frame.getContentPane().add(registerPersonLbl);
		
        frame.getContentPane().add(nameField);
        frame.getContentPane().add(rgField);
        frame.getContentPane().add(issuingInstitutionField);
		
		frame.getContentPane().add(ufField);
		
        frame.getContentPane().add(dddCellField);
        frame.getContentPane().add(cellField);
        frame.getContentPane().add(dddPhoneField);
        frame.getContentPane().add(phoneField);
        frame.getContentPane().add(emailField);

        frame.getContentPane().add(addressField);

        frame.getContentPane().add(cepField);
        
        frame.getContentPane().add(cityField);

		frame.getContentPane().add(numberField);
		
		frame.getContentPane().add(complementField);
		        
        frame.getContentPane().add(motherField);

        frame.getContentPane().add(fatherField);

		frame.getContentPane().add(birthdateField);

		frame.getContentPane().add(cpfField);
		
		JScrollPane scrollPaneAddedCourses = new JScrollPane();
		scrollPaneAddedCourses.setBounds(583, 92, 251, 169);
		frame.getContentPane().add(scrollPaneAddedCourses);
		scrollPaneAddedCourses.setBackground(Color.WHITE);
        
		JScrollPane scrollPaneAddedPackages = new JScrollPane();
		scrollPaneAddedPackages.setBounds(583, 384, 251, 169);
		frame.getContentPane().add(scrollPaneAddedPackages);
		scrollPaneAddedPackages.setBackground(Color.WHITE);
					
		tableOfAddedCourses = new JTable(courseTableModel);
		scrollPaneAddedCourses.setViewportView(tableOfAddedCourses);
			
		tableOfAddedPackages = new JTable(packageTableModel);
		scrollPaneAddedPackages.setViewportView(tableOfAddedPackages);
		
		disposeColumns(tableOfAddedCourses);
		disposeColumns(tableOfAddedPackages);

		JLabel lblDataDaMatrcula = new JLabel("Data da matrícula");
		lblDataDaMatrcula.setBounds(576, 55, 140, 17);
		frame.getContentPane().add(lblDataDaMatrcula);
		
		frame.getContentPane().add(dateLabel);
		

        JLabel paymentValue = new JLabel("Valor total");
        paymentValue.setBounds(30, 511, 200, 17);
        frame.getContentPane().add(paymentValue);
                       
        JLabel paymentInstallments = new JLabel("Quantidade de Parcelas");
        paymentInstallments.setBounds(229, 511, 190, 17);
        frame.getContentPane().add(paymentInstallments);

        JLabel paymentForm = new JLabel("Forma de pagamento");
        paymentForm.setBounds(30, 472, 200, 17);
        frame.getContentPane().add(paymentForm);
       
		frame.getContentPane().add(paymentFormField);
		frame.getContentPane().add(paymentValueField);
		frame.getContentPane().add(paymentInstallmentsField);
		JLabel valueInstallments = new JLabel("Valor das Parcelas");
		valueInstallments.setBounds(30, 570, 165, 17);
		frame.getContentPane().add(valueInstallments);
		
		frame.getContentPane().add(installmentsValueField);

	}

	private void courseTableModel(Person student) {
		this.student = (Student) student;
		addressField(student);
		String[] columnsAddedCourses = { "Cursos adicionados", "ID" };
		courseTableModel = new DefaultTableModel(null, columnsAddedCourses);
		String[] columnsAddedPackages = { "Pacotes adicionados", "ID" };
		packageTableModel = new DefaultTableModel(null, columnsAddedPackages);
		dateLabel = new JLabel();
		dateLabel.setBounds(718, 55, 100, 17);
		paymentFormField = new JTextField();
		paymentFormField.setBounds(182, 472, 380, 27);
		paymentFormField.setEditable(false);
		paymentValueField = new JTextField();
		paymentValueField.setBounds(105, 511, 120, 27);
		paymentValueField.setEditable(false);
		paymentInstallmentsField = new JTextField();
		paymentInstallmentsField.setBounds(404, 511, 27, 27);
		paymentInstallmentsField.setEditable(false);
		installmentsValueField = new JTextField();
		installmentsValueField.setBounds(195, 570, 120, 27);
		installmentsValueField.setEditable(false);
		fillTheFields(this.student);
	}

	private void addressField(Person student) {
		nameField.setBounds(115, 55, 434, 27);
		nameField.setColumns(10);
		rgField.setBounds(327, 92, 100, 27);
		rgField.setColumns(10);
		issuingInstitutionField.setColumns(10);
		issuingInstitutionField.setBounds(203, 131, 85, 27);
		ufField.setColumns(10);
		ufField.setBounds(417, 132, 100, 27);
		dddCellField.setBounds(379, 166, 40, 27);
		dddCellField.setColumns(10);
		cellField.setBounds(429, 166, 100, 27);
		cellField.setColumns(10);
		dddPhoneField.setBounds(379, 200, 40, 27);
		dddPhoneField.setColumns(10);
		phoneField.setBounds(429, 200, 100, 27);
		phoneField.setColumns(10);
		emailField.setBounds(115, 243, 334, 27);
		addressField.setBounds(145, 277, 344, 27);
		cepField.setBounds(545, 316, 84, 27);
		cityField.setBounds(385, 316, 105, 27);
		numberField.setBounds(522, 277, 57, 27);
		complementField.setBounds(177, 316, 122, 27);
		motherField.setBounds(177, 364, 402, 27);
		fatherField.setBounds(177, 404, 402, 27);
		String birthdate = student.getBirthdate().getSlashFormattedDate();
		birthdateField = new JTextField(birthdate);
		birthdateField.setBounds(70, 195, 190, 27);
		String cpf = student.getCpf().getFormattedCpf();
		cpfField = new JTextField(cpf);
		cpfField.setBounds(102, 97, 129, 27);
		setNonEditableAllFields();
	}
	
	
	/**
	 * Dispose the id and duration columns
	 * @param table - Receives the table to dispose columns
	 */
	private void disposeColumns(JTable table) {
		
		TableColumnModel tableModel = table.getColumnModel();
		
		tableModel.getColumn(1).setMinWidth(0);     
		tableModel.getColumn(1).setPreferredWidth(0);  
		tableModel.getColumn(1).setMaxWidth(0);    
	}
	
	/**
	 * Fill the fields in screen with student data 
	 * @param student - student to be show in screen
	 */
	private void fillTheFields(Student student) {
		
		String teacherName = student.getName();		
		String email = student.getEmail();
		String motherName = student.getMotherName();
		String fatherName = student.getFatherName();

		nameField.setText(teacherName);
		emailField.setText(email);
		motherField.setText(motherName);
		fatherField.setText(fatherName);

		//CPF
		CPF cpf = student.getCpf();
		String teacherCpf  = cpf.getFormattedCpf();
		cpfField.setText(teacherCpf);
		
		//RG
		
		RG rg = student.getRg();
		rgField.setText(rg.getRgNumber());
		ufField.setText(rg.getUf());
		issuingInstitutionField.setText(rg.getIssuingInstitution());
		
		// Birthdate
		Date date = student.getBirthdate();
		String birthdate = date.getSlashFormattedDate();
		birthdateField.setText(birthdate);
		
		//Address
		Address address = student.getAddress();
		String city = address.getCity();
		String cep = address.getCep();
		
		addressField.setText(address.getAddressInfo());
		cepField.setText(cep);
		cityField.setText(city);
		numberField.setText(address.getNumber());
		complementField.setText(address.getComplement());
		
		//Phones
		Phone principalPhone = student.getPrincipalPhone();
		Phone secondaryPhone = student.getSecondaryPhone();

		dddCellField.setText(principalPhone.getDDD());
		cellField.setText(principalPhone.getNumber());
		
		if(secondaryPhone != null){
			dddPhoneField.setText(secondaryPhone.getDDD());
			phoneField.setText(secondaryPhone.getNumber());
		}
		else{
			dddPhoneField.setText("");
			phoneField.setText("");
		}
	
		try {
			ArrayList<Service> servicesOfStudent = servicesOfStudent(student);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private ArrayList<Service> servicesOfStudent(Student student) throws Throwable {
		ServiceController serviceController = new ServiceController();
		CPF studentCPF = student.getCpf();
		ArrayList<Service> servicesOfStudent;
		servicesOfStudent = serviceController.searchService(studentCPF);
		return servicesOfStudent;
	}

	/**
	 * Show the data of the services contracts by the selected student
	 * @param service
	 * @param onlyHasPackages 
	 */
	private void visualizeServicesAndPayments(Service service) {
				
		Date date = service.getContractsDate();
		String contractsDate = date.getSlashFormattedDate();
		dateLabel.setText(contractsDate);
		
		Payment payment = service.getPayment();
		PaymentDescription paymentDescription = payment.getDescription();
		String paymentFormAndType = paymentDescription.getDescription();
		paymentFormField.setText(paymentFormAndType);
		
		String totalValue = service.getTotalValueFormatted();
		paymentValueField.setText(totalValue);
		
		Integer installments = payment.getInstallments();
		paymentInstallmentsField.setText(installments.toString());
		
		String installmentsValue = service.getInstallmentsValue();
		installmentsValueField.setText(installmentsValue);
		
		/**
		 * Courses and packages of a student
		 */		
		ArrayList<ServiceItem> courses = service.getCourses();		
		String [] studentServices =  new String[2];

		if(!courses.isEmpty()){
			ArrayList<String> coursesName = new ArrayList<String>();
			ArrayList<String> coursesId = new ArrayList<String>();

			int i = 0;
			
			// Building the arraylist with the courses name and ids
			while (i < courses.size()){
				
				String courseName = courses.get(i).getName();
				coursesName.add(courseName);
				
				String courseId = courses.get(i).getId().toString();
				coursesId.add(courseId);
				
				i++;
			}
			
			// Adding the courses to the table
			i = 0;
			while (i < coursesName.size()){
				
				studentServices[0] = coursesName.get(i);
				studentServices[1] = coursesId.get(i);
				
				courseTableModel.addRow(studentServices);
				i++;
			}
		}
		ArrayList<ServiceItem> packages = service.getPackages();
		if(!packages.isEmpty()){
			
			ArrayList<String> packagesName = new ArrayList<String>();
			ArrayList<String> packagesId = new ArrayList<String>();

			int i = 0;
			
			// Building the arraylist with the courses name
			while (i < packages.size()){
				String packageName = packages.get(i).getName();
				packagesName.add(packageName);
				
				String packageId = packages.get(i).getId().toString();
				packagesId.add(packageId);
				
				i++;
			}
			
			// Adding the courses name to the list
			i = 0;
			while (i < packagesName.size()){
				studentServices[0] = packagesName.get(i);
				studentServices[1] = packagesId.get(i);
				
				packageTableModel.addRow(studentServices);
				i++;
			}
		}
		
	}
	
	private void setNonEditableAllFields() {
		nameField.setEditable(false);
        rgField.setEditable(false);
        cellField.setEditable(false);
        phoneField.setEditable(false);
        addressField.setEditable(false);
        cepField.setEditable(false);
        cityField.setEditable(false);
        emailField.setEditable(false);
        motherField.setEditable(false);
        fatherField.setEditable(false);
        dddCellField.setEditable(false);
        dddPhoneField.setEditable(false);
        issuingInstitutionField.setEditable(false);
        ufField.setEditable(false);
        numberField.setEditable(false);
        complementField.setEditable(false);
        qualificationField.setEditable(false);
		cpfField.setEditable(false);
		birthdateField.setEditable(false);
	}



	@Override
	public void createButtons(JFrame frame) {
		editStudentBtn = new JButton("Editar");
		frame.getContentPane().add(editStudentBtn);
		editStudentBtn.setBounds(322, 611, 117, 25);
		
		
		editStudentBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){			
				EditStudents studentFrame = new EditStudents(student, ShowStudentDecorator.this);
				dispose();
				studentFrame.setVisible(true);
			}
		});
		
		backBtn = new JButton("Voltar");
		frame.getContentPane().add(backBtn);
		backBtn.setBounds(492, 611, 117, 25);
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){			
				dispose();
				PersonView studentFrame = new SearchStudent();
				studentFrame.buildScreen(studentFrame,null);
				studentFrame.setVisible(true);
			}
		});
					
		deactivateOrActivateButton = new JButton("Desativar matrícula");
		deactivateOrActivateButton.setBounds(370, 645, 208, 25);
		deactivateOrActivateButton.setVisible(true);
		
		status = student.getStatus();
		action = setTextToTheInactiveOrActiveButton(status);
		
		deactivateOrActivateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				
				int confirm = 0;				
				
				confirm = JOptionPane.showConfirmDialog(internalFrame, "Tem certeza que deseja que a matrícula seja " + action + "?", action, JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					
					boolean wasAltered = false;
					try {
						StudentController studentController = new StudentController();
						wasAltered = studentController.alterStatusOfTheStudent(student);
					} 
					catch(StudentException e1) {
						showInfoMessage(e1.getMessage());
					}	
					if(wasAltered){
						showInfoMessage("A matrícula do aluno está " + action + "!");	
						changeStatus();
						action = setTextToTheInactiveOrActiveButton(status);
					}
					else{
						showInfoMessage("Um erro ocorreu, a matrícula não foi " + action);
					}
				}
				else{
					// Nothing to do
				}
			
			}

			private void changeStatus() {
				
				if(status == student.ACTIVE){
					status = 0;
				}
				else{
					status = 1;
				}
				
			}
		});
		frame.getContentPane().add(deactivateOrActivateButton);

		
		addMoreCoursesButton = new JButton("Adicionar + Cursos/Pacotes");
		addMoreCoursesButton.setBounds(100, 645, 208, 25);
		frame.getContentPane().add(addMoreCoursesButton);

		addMoreCoursesButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					CPF studentCpf = null;
					String studentName = null;
					studentCpf = student.getCpf();
					studentName = student.getName();
				
					EnrollStudentInMoreCourses enrollStudentInMoreCourses;
					enrollStudentInMoreCourses = new EnrollStudentInMoreCourses(studentCpf, studentName);
					enrollStudentInMoreCourses.setVisible(true);
					dispose();	
				}
		});
		

	
	}
	
	/**
	 * Set the text to the button to inactive or active a student
	 * @param status - the current student status 
	 * @return the status to show in JOptionPane
	 */
	private String setTextToTheInactiveOrActiveButton(int status) {
		
		String enrollmentStatus = "";
		enrollmentStatus = getStatusObject(status).setTextToTheInactiveOrActiveButton(enrollmentStatus, this);
		
		return enrollmentStatus;
		
	}

	@Override
	public void createMasks(JFrame frame) {
		
	}

	private Status getStatusObject(int status) {
		if (status == Person.ACTIVE)
			return new Active();
		if (status == Person.INACTIVE)
			return new Inactive();
		return null;
	}

	public JButton getDeactivateOrActivateButton() {
		return deactivateOrActivateButton;
	}
}
