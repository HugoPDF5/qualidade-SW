package dao;


import model.Student;

/**
 * @see model.Person#ACTIVE
 */
public class Active extends StudentStatus {
	public int newStatus(int newStatus, Student student) {
		newStatus = student.INACTIVE;
		return newStatus;
	}
}