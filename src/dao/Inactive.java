package dao;


import model.Student;

/**
 * @see model.Person#INACTIVE
 */
public class Inactive extends StudentStatus {
	public int newStatus(int newStatus, Student student) {
		newStatus = student.ACTIVE;
		return newStatus;
	}
}