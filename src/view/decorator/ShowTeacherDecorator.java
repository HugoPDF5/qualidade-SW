package view.decorator;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Person;
import model.Teacher;
import view.PersonView;
import view.SearchTeacher;
import view.forms.TeacherForm;
import controller.TeacherController;
import datatype.Address;
import datatype.CPF;
import datatype.Date;
import datatype.Phone;
import datatype.RG;
import exception.TeacherException;

public class ShowTeacherDecorator extends PersonDecorator {

	private JButton editTeacherBtn;
	private JButton backBtn;
	Teacher teacher;
	private JButton disableTeacherBtn;
	private int status;
	private String action;

	public ShowTeacherDecorator(PersonView viewToDecorate) {
		super(viewToDecorate);
	}

	@Override
	public void createLabelsAndFields(JFrame viewToDecorate, Person teacher) {
		this.frame = viewToDecorate;
		super.createLabelsAndFields(viewToDecorate, teacher);
		addressField(teacher);
		registerPersonLbl.setText(teacher.getName());
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
        
        frame.getContentPane().add(qualificationField);	
		
		frame.getContentPane().add(birthdateField);

		frame.getContentPane().add(cpfField);

	}

	private void addressField(Person teacher) {
		this.teacher = (Teacher) teacher;
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
		qualificationField.setBounds(177, 444, 402, 127);
		String birthdate = teacher.getBirthdate().getSlashFormattedDate();
		birthdateField = new JTextField(birthdate);
		birthdateField.setBounds(70, 195, 190, 27);
		String cpf = teacher.getCpf().getFormattedCpf();
		cpfField = new JTextField(cpf);
		cpfField.setBounds(102, 97, 129, 27);
		fillTheFields(this.teacher);
	}
	
	private void fillTheFields(Teacher teacher) {
		
		String teacherName = teacher.getName();		
		String email = teacher.getEmail();
		String motherName = teacher.getMotherName();
		String fatherName = teacher.getFatherName();

		nameField.setText(teacherName);
		emailField.setText(email);
		motherField.setText(motherName);
		fatherField.setText(fatherName);

		//CPF
		CPF cpf = teacher.getCpf();
		String teacherCpf  = cpf.getFormattedCpf();
		cpfField.setText(teacherCpf);
		
		//RG
		
		RG rg = teacher.getRg();
		rgField.setText(rg.getRgNumber());
		ufField.setText(rg.getUf());
		issuingInstitutionField.setText(rg.getIssuingInstitution());
		
		// Birthdate
		Date date = teacher.getBirthdate();
		String birthdate = date.getSlashFormattedDate();
		birthdateField.setText(birthdate);
		
		//Address
		Address address = teacher.getAddress();
		String city = address.getCity();
		String cep = address.getCep();
		
		addressField.setText(address.getAddressInfo());
		cepField.setText(cep);
		cityField.setText(city);
		numberField.setText(address.getNumber());
		complementField.setText(address.getComplement());
		
		//Phones
		Phone principalPhone = teacher.getPrincipalPhone();
		Phone secondaryPhone = teacher.getSecondaryPhone();

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
		
		String qualification = teacher.getQualification();
		qualificationField.setText(qualification);
		
		setNonEditableAllFields();
		
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
	public void createButtons(final JFrame frame) {
		editTeacherBtn = new JButton("Editar");
		frame.getContentPane().add(editTeacherBtn);
		editTeacherBtn.setBounds(322, 611, 117, 25);
		editTeacherBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){			
				PersonView teacherFrame = new EditTeacherDecorator(new TeacherForm());
				dispose();
				teacherFrame.buildScreen(teacherFrame,teacher);
				teacherFrame.setVisible(true);
			}
		});
				
		disableTeacherBtn = new JButton("Desativar professor");
		frame.getContentPane().add(disableTeacherBtn);
		disableTeacherBtn.setBounds(370, 645, 208, 25);
		
		status = teacher.getStatus();
				
		action = setTextToTheDeactiveOrActiveButton(status);
		
		disableTeacherBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){			
				
				int confirm = 0;				
				
				confirm = JOptionPane.showConfirmDialog(frame.getContentPane(), "Tem certeza que deseja que o professor seja " + action + "?", "Status do professor", JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					
					TeacherController teacherController = new TeacherController();
					try{
						
						switch(status){
						
							case Teacher.ACTIVE:
								teacherController.disableTeacher(teacher);
								changeStatus();
								showInfoMessage("O professor foi " + action + "!");
								action = setTextToTheDeactiveOrActiveButton(status);
								break;
								
							case Teacher.INACTIVE:
								teacherController.activateTeacher(teacher);
								changeStatus();
								showInfoMessage("O professor foi " + action + "!");
								action = setTextToTheDeactiveOrActiveButton(status);
								break;
							
							default:
								break;
						}
					}
					catch(TeacherException e1){
						showInfoMessage(e1.getMessage() + ". O professor não foi " + action + ".");
					}
						
				}
				else{
					// Nothing to do
				}
			}
			
			private void changeStatus() {
				
				if(status == Teacher.ACTIVE){
					status = 0;
				}
				else{
					status = 1;
				}
				
			}
		});
		
		backBtn = new JButton("Voltar");
		frame.getContentPane().add(backBtn);
		backBtn.setBounds(492, 611, 117, 25);
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){			
				dispose();
				PersonView teacherFrame = new SearchTeacher();
				teacherFrame.buildScreen(teacherFrame,null);
				teacherFrame.setVisible(true);
			}
		});
	}

	@Override
	public void createMasks(JFrame frame) {

	}
	
	private String setTextToTheDeactiveOrActiveButton(int status) {
				
		String statusState = "";
		statusState = getStatussObject(status).setTextToTheDeactiveOrActiveButton(statusState, this);
		
		return statusState;
	}

	private Statuss getStatussObject(int status) {
		if (status == Person.ACTIVE)
			return new Activee();
		if (status == Person.INACTIVE)
			return new Inactivee();
		return null;
	}

	public JButton getDisableTeacherBtn() {
		return disableTeacherBtn;
	}
}
