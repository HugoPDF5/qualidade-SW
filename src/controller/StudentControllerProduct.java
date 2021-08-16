package controller;


import dao.StudentDAO;
import model.Student;
import exception.StudentException;
import exception.PersonException;
import datatype.Date;
import datatype.Address;
import datatype.Phone;
import datatype.CPF;
import exception.PhoneException;
import exception.CPFException;
import exception.DateException;
import exception.AddressException;
import exception.RGException;
import java.util.ArrayList;
import model.Course;

public class StudentControllerProduct {
	private StudentDAO studentDAO;

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	/**
	* Try to save the given student
	* @param student
	* @throws StudentException
	*/
	public void saveStudent(Student student) throws StudentException {
		if (student != null) {
			try {
				studentDAO.save(student);
			} catch (PersonException e) {
				throw new StudentException(e.getMessage());
			}
		} else {
			throw new StudentException(StudentController.CANT_SAVE_NULL_STUDENT);
		}
	}

	public boolean updateStudent(String studentName, Date birthdate, String email, Address address,
			Phone principalPhone, Phone secondaryPhone, String motherName, String fatherName, CPF cpf)
			throws StudentException, PersonException {
		Student student = new Student(studentName, birthdate, email, address, principalPhone, secondaryPhone,
				motherName, fatherName, StudentController.ACTIVE_STATUS, cpf);
		boolean wasSaved = false;
		wasSaved = studentDAO.updateStudent(student);
		return wasSaved;
	}

	public Student getStudent(CPF cpf) throws StudentException {
		Student foundStudent;
		try {
			foundStudent = studentDAO.get(cpf);
		} catch (PhoneException | CPFException | DateException | AddressException | RGException | StudentException
				| PersonException e) {
			throw new StudentException(StudentController.STUDENT_NULL);
		}
		return foundStudent;
	}

	/**
	* Search the student with the entered name
	* @param studentName  - the entered name by user
	* @return  an arraylist with the found students
	* @throws StudentException
	* @throws CPFException
	*/
	public ArrayList<Student> searchStudent(String studentName) throws PersonException, CPFException, StudentException {
		ArrayList<Student> foundStudents = studentDAO.get(studentName);
		return foundStudents;
	}

	/**
	* Get the students of the course
	* @param course
	* @return
	* @throws StudentException
	*/
	public ArrayList<Student> getStudentsOfCourse(Course course) throws StudentException {
		ArrayList<Student> studentsOfCourse = studentDAO.get(course);
		return studentsOfCourse;
	}

	public boolean alterStatusOfTheStudent(Student student) throws StudentException {
		boolean wasAltered = false;
		if (student != null) {
			wasAltered = studentDAO.update(student);
		} else {
			throw new StudentException(StudentController.STUDENT_NULL);
		}
		return wasAltered;
	}
}