package view.decorator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

import controller.CourseController;
import controller.EnrollController;
import controller.PackageController;
import datatype.Address;
import datatype.CPF;
import datatype.Date;
import datatype.Phone;
import datatype.RG;
import exception.AddressException;
import exception.CPFException;
import exception.CourseException;
import exception.DateException;
import exception.PackageException;
import exception.PaymentException;
import exception.PhoneException;
import exception.RGException;
import exception.ServiceException;
import exception.StudentException;
import model.Course;
import model.Package;
import model.Person;
import view.PersonView;
import view.forms.StudentForm;

public class EnrollStudentDecorator extends PersonDecorator {
	
	// The "-1" means that doesn't have a row selected
	private static final int NONE_ROW_SELECTED = -1;
	
	// The max length of a value (0000.00)
	private static final int MAX_VALUE_LENGTH = 6;
	
	private JTextField paymentValueField;
	private JTextField paymentInstallmentsField;
	private DefaultTableModel courseTableModel;
	private DefaultTableModel packageTableModel;
	private JComboBox<String> paymentForms;
	private JComboBox<String> paymentTypes;
	private JComboBox<String> packages;
	private DefaultComboBoxModel<String> availableCourses;
	private DefaultComboBoxModel<String> availablePackages;
	private ArrayList<String> coursesId = new ArrayList<String>(); 
	private ArrayList<String> coursesName = new ArrayList<String>();
	private ArrayList<String> packagesId = new ArrayList<String>();
	private ArrayList<String> packagesName = new ArrayList<String>();
	private ArrayList<String> addedCoursesValue = new ArrayList<String>(); 
	private ArrayList<String> addedPackagesValue = new ArrayList<String>(); 
	private JTable tableOfAddedCourses;
	private JTable tableOfAddedPackages;
	private ArrayList<String> addedCoursesId = new ArrayList<String>(); 
	private ArrayList<String> addedPackagesId = new ArrayList<String>();
	private JLabel paymentTypeLbl;
	private Person student;
	private JComboBox<String> courses;
	
	public EnrollStudentDecorator(PersonView viewToDecorate) {
		super(viewToDecorate);
	}

	@Override
	public void createLabelsAndFields(JFrame frame, Person student){
		
		super.createLabelsAndFields(frame,student);
		this.student = student;
		JLabel requiredFieldsLbl = new JLabel("Os campos com * s??o obrigat??rios");
		requiredFieldsLbl.setFont(new Font("DejaVu Sans Condensed", Font.BOLD | Font.ITALIC,12));
		requiredFieldsLbl.setBounds(407, 32, 370, 17);
        frame.getContentPane().add(requiredFieldsLbl);
        
		registerPersonLbl.setText("Matricular novo aluno");
		registerPersonLbl.setBounds(407, 12, 475, 31);
		registerPersonLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		frame.getContentPane().add(registerPersonLbl);
		
        nameField.setBounds(115, 55, 434, 27);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);
        
        rgField.setBounds(327, 92, 100, 27);
        frame.getContentPane().add(rgField);
        rgField.setColumns(10);
        
		issuingInstitutionField.setColumns(10);
		issuingInstitutionField.setBounds(203, 131, 85, 27);
		frame.getContentPane().add(issuingInstitutionField);
		
		ufField.setColumns(10);
		ufField.setBounds(417, 132, 100, 27);
		frame.getContentPane().add(ufField);
		
        dddCellField.setBounds(379, 166, 40, 27);
        frame.getContentPane().add(dddCellField);
        dddCellField.setColumns(10);
        
        cellField.setBounds(429, 166, 100, 27);
        frame.getContentPane().add(cellField);
        cellField.setColumns(10);
        
        dddPhoneField.setBounds(379, 200, 40, 27);
        frame.getContentPane().add(dddPhoneField);
        dddPhoneField.setColumns(10);
        
        phoneField.setBounds(429, 200, 100, 27);
        frame.getContentPane().add(phoneField);
        phoneField.setColumns(10);
               
        emailField.setBounds(115, 243, 334, 27);
        frame.getContentPane().add(emailField);

        addressField.setBounds(145, 277, 344, 27);
        frame.getContentPane().add(addressField);

        cepField.setBounds(535, 316, 84, 27);
        frame.getContentPane().add(cepField);
        
        cityField.setBounds(385, 316, 105, 27);
        frame.getContentPane().add(cityField);

		numberField.setBounds(522, 277, 57, 27);
		frame.getContentPane().add(numberField);
		
		complementField.setBounds(177, 316, 122, 27);
		frame.getContentPane().add(complementField);
		        
        motherField.setBounds(177, 364, 402, 27);
        frame.getContentPane().add(motherField);

        fatherField.setBounds(177, 404, 402, 27);
        frame.getContentPane().add(fatherField);

        JLabel paymentValue = new JLabel("*Valor total");
        paymentValue.setBounds(25, 580, 200, 17);
        frame.getContentPane().add(paymentValue);
                       
        JLabel paymentInstallments = new JLabel("Quantidade de Parcelas");
        paymentInstallments.setBounds(229, 580, 190, 17);
        frame.getContentPane().add(paymentInstallments);
        
        paymentInstallmentsField = new JTextField();
        paymentInstallmentsField.setBounds(412, 581, 27, 27);
        frame.getContentPane().add(paymentInstallmentsField);
  
        courses = new JComboBox<String>();
        courses.setBounds(583, 97, 251, 31);
        frame.getContentPane().add(courses);
        availableCourses = new DefaultComboBoxModel<String>();
		
        courses.setModel(availableCourses);
        
		JScrollPane scrollPaneAddedCourses = new JScrollPane();
		scrollPaneAddedCourses.setBounds(583, 130, 251, 169);
		frame.getContentPane().add(scrollPaneAddedCourses);
		scrollPaneAddedCourses.setBackground(Color.WHITE);
		
        packages = new JComboBox<String>();
		packages.setBounds(583, 377, 251, 31);
		frame.getContentPane().add(packages);
        
		availablePackages = new DefaultComboBoxModel<String>();
        
        packages.setModel(availablePackages);
        
        JLabel paymentForm = new JLabel("*Forma de pagamento");
        paymentForm.setBounds(68, 520, 171, 17);
        frame.getContentPane().add(paymentForm);
        
        paymentTypeLbl = new JLabel("*Tipo de pagamento");
        paymentTypeLbl.setBounds(68, 472, 150, 17);
        frame.getContentPane().add(paymentTypeLbl);
        
        paymentTypes();
		frame.getContentPane().add(paymentTypes);
        
        paymentForms = new JComboBox<String>();
        paymentForms.setBounds(221, 516, 151, 24);
        frame.getContentPane().add(paymentForms);
        
        DefaultComboBoxModel<String> paymentFormsModel = new DefaultComboBoxModel<String>();
        paymentFormsModel.addElement("Dinheiro");
        paymentFormsModel.addElement("Cart??o");
        paymentFormsModel.addElement("Cheque");
        
        paymentForms.setModel(paymentFormsModel);
	
		JScrollPane scrollPaneAddedPackages = new JScrollPane();
		scrollPaneAddedPackages.setBounds(583, 410, 251, 169);
		frame.getContentPane().add(scrollPaneAddedPackages);
		scrollPaneAddedPackages.setBackground(Color.WHITE);
					
		String [] columnsAddedCourses = {"Cursos adicionados", "ID", "Valor"};
		
		courseTableModel = new DefaultTableModel(null, columnsAddedCourses);			
		
		tableOfAddedCourses = new JTable(courseTableModel);
		scrollPaneAddedCourses.setViewportView(tableOfAddedCourses);
			
		String [] columnsAddedPackages = {"Pacotes adicionados", "ID", "Valor"};
		
		packageTableModel = new DefaultTableModel(null, columnsAddedPackages);			

		tableOfAddedPackages = new JTable(packageTableModel);
		scrollPaneAddedPackages.setViewportView(tableOfAddedPackages);
		
		disposeColumns(tableOfAddedCourses);

		disposeColumns(tableOfAddedPackages);
 
	
		try {
			getAllCoursesToSelect();
			getAllPackagesToSelect();
		} catch (CourseException | PackageException e1) {
			e1.printStackTrace();
		}
		
	}

	private void paymentTypes() {
		paymentTypes = new JComboBox<String>();
		paymentTypes.setBounds(221, 472, 151, 24);
		DefaultComboBoxModel<String> paymentTypesModel = new DefaultComboBoxModel<String>();
		paymentTypesModel.addElement("?? vista");
		paymentTypesModel.addElement("Parcelado");
		paymentTypes.setModel(paymentTypesModel);
	}
	
	@Override
	public void createMasks(JFrame frame){
		MaskFormatter birthdateMask = null;
        MaskFormatter cpfMask = null;
		try{
	        // Mask for cpf
	        cpfMask = new MaskFormatter("###.###.###-##");
	        cpfMask.setValidCharacters("0123456789");
	        cpfMask.setValueContainsLiteralCharacters(false);

	        cpfField = new JFormattedTextField(cpfMask);
	        cpfField.setBounds(102, 97, 129, 27);
	        frame.getContentPane().add(cpfField);
	        cpfField.setColumns(10);
	        
			// Mask for birthdate
			birthdateMask = new MaskFormatter("##/##/####");
			birthdateMask.setValidCharacters("0123456789");
			birthdateMask.setValueContainsLiteralCharacters(true);
	        
	        birthdateField = new JFormattedTextField(birthdateMask);
	        birthdateField.setBounds(70, 195, 190, 27);
	        frame.getContentPane().add(birthdateField);
	        birthdateField.setColumns(10);
	        
	        // Mask for value
 			MaskFormatter valueMask = new MaskFormatter("R$####,##");
 			valueMask.setValidCharacters("0123456789");
 			valueMask.setValueContainsLiteralCharacters(false);
	        
	        paymentValueField = new JFormattedTextField(valueMask);
	        paymentValueField.setBounds(105, 580, 120, 27);
	        frame.getContentPane().add(paymentValueField);

		}
		catch(ParseException e2){
			e2.printStackTrace();
		}
	}
	
	@Override
	public void createButtons(JFrame frame){
	       
        JButton enrollBtn = new JButton("Matricular");
		enrollBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e){
				
				prepareData();
				
			}

		});
		enrollBtn.setBounds(422, 631, 117, 25);
		frame.getContentPane().add(enrollBtn);
		
		JButton addCourseBtn = new JButton("Adicionar Curso");
		addCourseBtn.setBounds(835, 97, 151, 31);
		frame.getContentPane().add(addCourseBtn);
		addCourseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int indexOfSelectedCourse = courses.getSelectedIndex();
				addCourseToAddedCourses(indexOfSelectedCourse);
				
				String value = calculateValue();
				paymentValueField.setText(value);
				
				availableCourses.removeElementAt(indexOfSelectedCourse);
				addedCoursesId.add(coursesId.get(indexOfSelectedCourse));
				coursesId.remove(indexOfSelectedCourse);
				coursesName.remove(indexOfSelectedCourse);
				addedCoursesValue.remove(indexOfSelectedCourse);

			}
			
			private void addCourseToAddedCourses(int indexOfSelectedCourse) {
				
				String courseId = coursesId.get(indexOfSelectedCourse);
				String courseName = coursesName.get(indexOfSelectedCourse);
				String courseValue = addedCoursesValue.get(indexOfSelectedCourse);
					
				String[] allCourses = new String[3];
		
				allCourses[0] = (courseName);
				allCourses[1] = (courseId);
				allCourses[2] = (courseValue);
				
				courseTableModel.addRow(allCourses);
			}
		});
		
		JButton removeCourseBtn = new JButton("Remover Curso");
		removeCourseBtn.setBounds(835, 137, 151, 31);
		frame.getContentPane().add(removeCourseBtn);
		removeCourseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int selectedRow = tableOfAddedCourses.getSelectedRow();

				if(selectedRow != NONE_ROW_SELECTED){
					String courseName = (String) courseTableModel.getValueAt(selectedRow, 0);
					String courseId = (String) courseTableModel.getValueAt(selectedRow, 1);
					String courseValue = (String) courseTableModel.getValueAt(selectedRow, 2);				
					
					coursesId.add(courseId);
					coursesName.add(courseName);
					addedCoursesValue.add(courseValue);
					
					String value = calculateValue();
					paymentValueField.setText(value);
					
					addedCoursesId.remove(selectedRow);
					
					availableCourses.addElement(courseName);
					
					courseTableModel.removeRow(selectedRow);
				}
				else{
					showInfoMessage("Selecione um curso da lista de cursos adicionados");
				}
				
			}
			
		});
		
		JButton addPackageBtn = new JButton("Adicionar Pacote");
		addPackageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int indexOfSelectedPackage = packages.getSelectedIndex();
				
				addPackageToAddedCourses(indexOfSelectedPackage);
				
				String value = calculateValue();
				paymentValueField.setText(value);
				
				availablePackages.removeElementAt(indexOfSelectedPackage);
				addedPackagesId.add(packagesId.get(indexOfSelectedPackage));
				packagesId.remove(indexOfSelectedPackage);
				packagesName.remove(indexOfSelectedPackage);
				addedPackagesValue.remove(indexOfSelectedPackage);

			}
			
			private void addPackageToAddedCourses(int indexOfSelectedPackage){
				
				String packageId = packagesId.get(indexOfSelectedPackage);
				String packageName = packagesName.get(indexOfSelectedPackage);
				String packageValue = addedPackagesValue.get(indexOfSelectedPackage);

				String[] allPackages = new String[3];
		
				allPackages[0] = (packageName);
				allPackages[1] = (packageId);
				allPackages[2] = (packageValue);
				
				packageTableModel.addRow(allPackages);
			}
		});
		addPackageBtn.setBounds(835, 410, 151, 31);
		frame.getContentPane().add(addPackageBtn);
		
		JButton removePackageBtn = new JButton("Remover Pacote");
		removePackageBtn.setBounds(835, 450, 151, 31);
		removePackageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int selectedRow = tableOfAddedPackages.getSelectedRow();

				if(selectedRow != NONE_ROW_SELECTED){
					String packageName = (String) packageTableModel.getValueAt(selectedRow, 0);
					String packageId = (String) packageTableModel.getValueAt(selectedRow, 1);
					String packageValue = (String) packageTableModel.getValueAt(selectedRow, 2);
									
					packagesId.add(packageId);
					packagesName.add(packageName);
					addedPackagesValue.add(packageValue);
					
					addedPackagesId.remove(selectedRow);
					
					availablePackages.addElement(packageName);
						
					packageTableModel.removeRow(selectedRow);
					String value = calculateValue();
					paymentValueField.setText(value);
				}	
				else{
					showInfoMessage("Selecione um curso da lista de pacotes adicionados");
				}
			}
			
		});
		frame.getContentPane().add(removePackageBtn);
	}
	
	
	private String calculateValue() {
		
		String value = "";
		Integer serviceValue = 0;
		
		ArrayList<String> addedCoursesValue = new ArrayList<String>();
		ArrayList<String> addedPackagesValue = new ArrayList<String>();
		
		addedCoursesValue = getAddedItemsValue(tableOfAddedCourses);
		addedPackagesValue = getAddedItemsValue(tableOfAddedPackages);
		
		for(int i = 0; i < addedCoursesValue.size(); i++){
			String courseValue = addedCoursesValue.get(i);
			serviceValue += Integer.parseInt((courseValue));
		}
		
		for(int i = 0; i < addedPackagesValue.size(); i++){
			String packageValue = addedPackagesValue.get(i);
			serviceValue += Integer.parseInt((packageValue));
		}
		
		value = serviceValue.toString();
		if(value.length() != MAX_VALUE_LENGTH){
			value = "0" + value;
		}
		else{
			// Nothing to do
		}		
		
		return value;
	}

	private ArrayList<String> getAddedItemsValue(JTable tableOfItems) {
		
		ArrayList<String> values = new ArrayList<String>();
		
		for (int i = 0; i < tableOfItems.getRowCount(); i++) {
			String value = (String) tableOfItems.getValueAt(i, 2);
			values.add(value);
		}
		
		return values;
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
		
		tableModel.getColumn(2).setMinWidth(0);     
		tableModel.getColumn(2).setPreferredWidth(0);  
		tableModel.getColumn(2).setMaxWidth(0);    
	}
	
	/**
	 * Method used to get the available packages
	 * @throws CourseException 
	 * @throws PackageException 
	 */
	private void getAllPackagesToSelect() throws CourseException, PackageException  {
		
		PackageController packageController = new PackageController();
		ArrayList<Package> packages = packageController.getPackages();		
		int indexOfPackages = 0;
		
		while(indexOfPackages < packages.size()){
			
			Package currentPackage = packages.get(indexOfPackages);
			Integer packageId = currentPackage.getId();
			String packageName = (currentPackage.getName());
			Integer packageValue = currentPackage.getValue();
			
			packagesId.add(packageId.toString());
			packagesName.add(packageName);
			addedPackagesValue.add(packageValue.toString());
			
			availablePackages.addElement(packageName);
			
			indexOfPackages++;
		}
	}
	
	/**
	 *  Method used to show all available courses 
	 * @throws SQLException
	 * @throws CourseException 
	 */
	private void getAllCoursesToSelect() throws CourseException {
		
		CourseController courseController = new CourseController();
		ArrayList<Course> courses = courseController.showCourse();		
		int indexOfCourses = 0;
		
		while(indexOfCourses < courses.size()){
			
			availableCourses(courses, indexOfCourses);
			Course course = courses.get(indexOfCourses);
			Integer courseId = course.getId();
			String courseName = (course.getName());
			Integer courseValue = course.getValue();
			
			coursesId.add(courseId.toString());
			coursesName.add(courseName);
			addedCoursesValue.add(courseValue.toString());
			
			indexOfCourses++;
			
		}
	}

	private void availableCourses(ArrayList<Course> courses, int indexOfCourses) {
		Course course = courses.get(indexOfCourses);
		String courseName = (course.getName());
		availableCourses.addElement(courseName);
	}
	
	private void prepareData(){
		
		String message = "";
		try{
		
			String studentName = nameField.getText();
			
			String cpf = cpfField.getText();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			CPF studentCpf = new CPF(cpf);
			
			String rgNumber = rgField.getText();
			String rgIssuingInstitution = issuingInstitutionField.getText();
			String rgUf = ufField.getText();
			
			RG studentRg = new RG(rgNumber, rgIssuingInstitution, rgUf);					
			
			String date = birthdateField.getText();
			String day = date.substring(0, 2);
			String month = date.substring(3, 5);
			String year = date.substring(6, 10);
			
			Date birthdate = new Date(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
			
			String email = emailField.getText();
			
			String addressInfo = addressField.getText();
			String addressNumber = numberField.getText();
			String addressComplement = complementField.getText();
			String addressCity = cityField.getText();
			String addressCep = cepField.getText();
			
			Address address = new Address(addressInfo, addressNumber, addressComplement, addressCep, addressCity);
			
			String ddCell = dddCellField.getText();
			String cellNumber = cellField.getText();
								
			String ddPhone = dddPhoneField.getText();
			String phoneNumber = phoneField.getText();
			
			Phone principalPhone;
			Phone secondaryPhone;
			if(!phoneNumber.isEmpty() && !ddPhone.isEmpty()){
				
				principalPhone = new Phone(ddCell, cellNumber);
				secondaryPhone = new Phone(ddPhone, phoneNumber);
			}
			else{
				principalPhone = new Phone(ddCell, cellNumber);
				secondaryPhone = null;
			}
			
			String motherName = motherField.getText();
			String fatherName = fatherField.getText();
			
			int paymentType = paymentTypes.getSelectedIndex();
			
			switch(paymentType){
				case 0:
					paymentType = 1;
					break;
				case 1:
					paymentType = 2;
					break;
				default:
					showInfoMessage("Tipo de pagamento inv??lido.");
					break;
			}
			
			int paymentForm = paymentForms.getSelectedIndex();
			
			switch(paymentForm){
				case 0:
					paymentForm = 1;
					break;
				case 1:
					paymentForm = 2;
					break;
				case 2:
					paymentForm = 3;
					break;
				default:
					showInfoMessage("Forma de pagamento inv??lida.");
					break;
			}
			
			String paymentInstallments = paymentInstallmentsField.getText(); 
			
			Integer installments;
			if(!paymentInstallments.isEmpty()){
				installments = new Integer(paymentInstallments);
			}
			else{
				installments = 0;
			}
			
			String value = paymentValueField.getText();
			value = value.replace("R$", "");
			value = value.replace(",", "");
			Integer serviceValue = new Integer(value);
			
			EnrollController enroll = new EnrollController();
			student = enroll.enrollStudent(studentName, studentCpf, studentRg, birthdate, email, address,
											 principalPhone, secondaryPhone, motherName, fatherName,
											 addedCoursesId, addedPackagesId, paymentType, paymentForm, installments, serviceValue);
			
			message = "Aluno matriculado com sucesso.";
		}
		catch(CPFException e1){
			message = e1.getMessage();
		}
		catch(RGException e1){
			message = e1.getMessage();
		}
		catch(DateException e1){
			message = e1.getMessage();
		}
		catch(AddressException e1){
			message = e1.getMessage();
		}
		catch(PhoneException e1){
			message = e1.getMessage();
		} catch (StudentException e1) {
			message = e1.getMessage();
		}
		catch(ServiceException e1){
			message = e1.getMessage();
		}
		catch(PaymentException e1){
			message = e1.getMessage();
		}
		finally{
			showInfoMessage(message);
			if(student != null){
				dispose();
				PersonView showStudentFrame = new ShowStudentDecorator(new StudentForm());
				showStudentFrame.buildScreen(showStudentFrame, student);
				showStudentFrame.setVisible(true);
			}
		}
	}
}