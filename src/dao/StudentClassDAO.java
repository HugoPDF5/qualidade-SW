package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datatype.CPF;
import exception.AddressException;
import exception.CPFException;
import exception.DateException;
import exception.PersonException;
import exception.PhoneException;
import exception.RGException;
import exception.StudentClassException;
import exception.StudentException;
import model.Class;
import model.Student;
import model.StudentClass;

public class StudentClassDAO extends DAO {

	private static final String COULDNT_ENROLL_STUDENT_TO_CLASS = "Não foi possível associar este aluno para esta turma.";
	private static final String COULDNT_FIND_STUDENT_OF_CLASS = "Não foi possível encontrar os estudantes dessa turma.";
	private static final String COULDNT_SAVE_THE_SITUATION = "Não foi possível salvar as notas e faltas dos alunos.";

	private static final String STUDENT_CLASS_TABLE = "StudentClass";
	private static final String ID_CLASS_COLUMN = "id_class";
	private static final String STUDENT_CPF_COLUMN = "cpf";
	private static final String ABSENCE_COLUMN = "absences";
	private static final String GRADE_COLUMN = "grade";
	private static final String SITUATION_COLUMN = "situation";
	
	/**
	 * Enroll a student in a class
	 * @param studentClass
	 * @throws StudentClassException
	 */
	public void enrollStudentInClass(StudentClass studentClass) throws StudentClassException{
		
		String query = query(studentClass);
		try{
			this.execute(query);
		}
		catch(SQLException e){
			throw new StudentClassException(COULDNT_ENROLL_STUDENT_TO_CLASS);
		}
	}

	private String query(StudentClass studentClass) {
		String classId = studentClass.getEnrolledClass().getClassId();
		String studentCpf = studentClass.getStudent().getCpf().getCpf();
		String query = "";
		query += "INSERT INTO " + STUDENT_CLASS_TABLE + " (" + ID_CLASS_COLUMN + ", " + STUDENT_CPF_COLUMN + ") ";
		query += "VALUES('" + classId + "', '" + studentCpf + "')";
		return query;
	}
	
	/**
	 * Get the students of a class
	 * @param enrolledClass
	 * @return An array with the students of the class
	 * @throws StudentClassException
	 * @throws CPFException
	 */
	public ArrayList<Student> get(Class enrolledClass) throws StudentClassException, CPFException {
		
		String enrolledClassId = enrolledClass.getClassId();
				
		ArrayList<Student> students = new ArrayList<Student>();
		
		String query = "";
		query += "SELECT " + STUDENT_CPF_COLUMN + " FROM " + STUDENT_CLASS_TABLE;
		query += " WHERE " + ID_CLASS_COLUMN + " = '" + enrolledClassId + "'";
		
		try{
			ResultSet resultSelectStudentClass = this.search(query);
			
			while(resultSelectStudentClass.next()){
				String cpf = resultSelectStudentClass.getString(STUDENT_CPF_COLUMN);
				CPF studentCpf = new CPF(cpf);
				
				StudentDAO studentDao = new StudentDAO();
				Student student = null;
				try {
					student = studentDao.get(studentCpf);
				} 
				catch (PhoneException | DateException | AddressException
						| RGException | StudentException | PersonException e) {
					throw new StudentClassException(COULDNT_FIND_STUDENT_OF_CLASS);
				}
				
				students.add(student);
			}			
		}
		catch(SQLException e){
			throw new StudentClassException(COULDNT_FIND_STUDENT_OF_CLASS);
		}

		return students;
	
	}
	
	/**
	 * Get the situation of students of a class
	 * @param enrolledClass
	 * @return An array with the situation of students of the class
	 * @throws StudentClassException
	 * @throws CPFException
	 */
	public ArrayList<StudentClass> get(Class enrolledClass, boolean selectStudentClass) throws StudentClassException, CPFException {
		
		String enrolledClassId = enrolledClass.getClassId();
				
		ArrayList<StudentClass> students = new ArrayList<StudentClass>();

		if(selectStudentClass){
			String query = "";
			query = "SELECT * FROM " + STUDENT_CLASS_TABLE;
			query += " WHERE " + ID_CLASS_COLUMN + " = '" + enrolledClassId + "'";
			
			try{			
	
				ResultSet resultSelectStudentClass = this.search(query);
				
				while(resultSelectStudentClass.next()){

					StudentClass studentClass = studentClass(enrolledClass, resultSelectStudentClass);
					students.add(studentClass);
			
				}
			}
			catch(SQLException e){
				throw new StudentClassException(COULDNT_FIND_STUDENT_OF_CLASS);
			}
		}
		

		return students;
	
	}

	private StudentClass studentClass(Class enrolledClass, ResultSet resultSelectStudentClass)
			throws SQLException, CPFException, StudentClassException {
		String cpf = resultSelectStudentClass.getString(STUDENT_CPF_COLUMN);
		CPF studentCPF = new CPF(cpf);
		StudentDAO studentDao = new StudentDAO();
		Student student = null;
		try {
			student = studentDao.get(studentCPF);
		} catch (PhoneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CPFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StudentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer absences = resultSelectStudentClass.getInt(ABSENCE_COLUMN);
		Integer grade = resultSelectStudentClass.getInt(GRADE_COLUMN);
		String situation = resultSelectStudentClass.getString(SITUATION_COLUMN);
		StudentClass studentClass = new StudentClass(student, enrolledClass, absences, grade, situation);
		return studentClass;
	}
	
	/**
	 * Updates a student in a class
	 * @param studentClass
	 * @throws StudentClassException
	 */
	public void update(StudentClass studentClass) throws StudentClassException {
		
		String classId = studentClass.getEnrolledClass().getClassId();
		String studentCpf = studentClass.getStudent().getCpf().getCpf();
		Integer absence = studentClass.getAbsences();
		Integer grade = studentClass.getGrade();
		String situation = studentClass.getStudentSituation();
		
		String query = "";
		query += "UPDATE " + STUDENT_CLASS_TABLE + " SET ";
		query += ABSENCE_COLUMN + " = " + absence + " , ";
		query += GRADE_COLUMN + " = " + grade + " , ";
		query += SITUATION_COLUMN + " = '" + situation + "' ";
		query += " WHERE " + ID_CLASS_COLUMN + " = '" + classId + "'";
		query += " AND " + STUDENT_CPF_COLUMN + " = '"  + studentCpf + "'";
		
		try{
			this.execute(query);
		}
		catch(SQLException e){
			throw new StudentClassException(COULDNT_SAVE_THE_SITUATION);
		}
		
		
	}
	
}
