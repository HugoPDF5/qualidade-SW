package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.StudentDAO;
import datatype.Address;
import datatype.CPF;
import datatype.Date;
import datatype.Phone;
import datatype.RG;
import exception.AddressException;
import exception.CPFException;
import exception.CourseException;
import exception.DateException;
import exception.PaymentException;
import exception.PersonException;
import exception.PhoneException;
import exception.RGException;
import exception.ServiceException;
import exception.StudentException;
import model.Course;
import model.Student;
import model.Service;

public class StudentController {
	
	private StudentControllerProduct studentControllerProduct = new StudentControllerProduct();
	public static final String STUDENT_NULL = "Não foi possível encontrar o estudante";
	public static final String CANT_SAVE_NULL_STUDENT = "Não é possível salvar um estudante nulo.";
	public static final int ACTIVE_STATUS	= 1;
	private ServiceController serviceController;
	
	public StudentController(){
		studentControllerProduct.setStudentDAO(new StudentDAO());
		serviceController = new ServiceController();
	}

	public void setStudentDAO(StudentDAO studentDAO) {
		studentControllerProduct.setStudentDAO(studentDAO);
	}
	
	public void setServiceController(ServiceController serviceController) {
		this.serviceController = serviceController;
	}
	
	/**
	 * Creates a new student with the given information of the student and its service
	 * @param studentName - Name of the student
	 * @param studentCpf - CPF of the student
	 * @param studentRg - RG of the student
	 * @param birthdate - Birth date of the student
	 * @param email - Email of the student (optional)
	 * @param address - Address of the student
	 * @param principalPhone - The principal phone number of the student
	 * @param secondaryPhone - The secondary phone number of the student (optional)
	 * @param motherName - The name of the student's mother
	 * @param fatherName - The name of the student's father
	 * @throws StudentException
	 */	
	public Student newStudent(String studentName, CPF studentCpf, RG studentRg, Date birthdate, String email, Address address,
			   Phone principalPhone, Phone secondaryPhone, String motherName, String fatherName) throws StudentException{

		Student student;
		try{
			student = new Student(studentName, studentCpf, studentRg, birthdate, email, address, principalPhone, secondaryPhone, motherName, fatherName, ACTIVE_STATUS);
		}
		catch(PersonException e){
			throw new StudentException(e.getMessage());
		}
		
		return student;
	}
	
	/**
	 * Try to save the given student
	 * @param student
	 * @throws StudentException
	 */
	public void saveStudent(Student student) throws StudentException{
		
		studentControllerProduct.saveStudent(student);
	}
	
	public boolean updateStudent(String studentName, Date birthdate, String email, Address address,
			   Phone principalPhone, Phone secondaryPhone, String motherName, String fatherName, CPF cpf)
			 throws StudentException, PersonException{
		
		return studentControllerProduct.updateStudent(studentName, birthdate, email, address, principalPhone,
				secondaryPhone, motherName, fatherName, cpf);
	}
	
	public Student getStudent(CPF cpf) throws StudentException{
		
		return studentControllerProduct.getStudent(cpf);
	}
	
	/**
	 * Search the student with the entered name
	 * @param studentName - the entered name by user
	 * @return an arraylist with the found students
	 * @throws StudentException
	 * @throws CPFException
	 */
	public ArrayList<Student> searchStudent(String studentName) throws PersonException, CPFException, StudentException {
			
		return studentControllerProduct.searchStudent(studentName);
	}
	

	/**
	 * Get the students of the course
	 * @param course
	 * @return
	 * @throws StudentException
	 */
	public ArrayList<Student> getStudentsOfCourse(Course course) throws StudentException{
		
		return studentControllerProduct.getStudentsOfCourse(course);
	}

	public boolean alterStatusOfTheStudent(Student student) throws StudentException {

		return studentControllerProduct.alterStatusOfTheStudent(student);
		
	}
	
}
