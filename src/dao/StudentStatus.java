package dao;


import model.Student;

public abstract class StudentStatus {
	public abstract int newStatus(int newStatus, Student student);
}