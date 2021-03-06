package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import datatype.Address;
import datatype.CPF;
import datatype.Date;
import datatype.Phone;
import datatype.RG;
import exception.AddressException;
import exception.CPFException;
import exception.DateException;
import exception.PersonException;
import exception.PhoneException;
import exception.RGException;
import exception.StudentException;
import model.Course;
import model.Person;
import model.Student;

public class StudentDAO extends DAO {

	private static final String STUDENT_TABLE_NAME = "Student";
	private static final String NAME_COLUMN = "name_student";
	private static final String CPF_COLUMN = "cpf";
	private static final String	BIRTHDATE_COLUMN = "birthdate";
	private static final String EMAIL_COLUMN	= "email";
	private static final String MOTHER_COLUMN	= "mother";
	private static final String FATHER_COLUMN	= "father";
	private static final String UF_COLUMN	= "uf";
	private static final String ISSUING_INSTITUTION_COLUMN	= "issuing_institution";
	private static final String RG_NUMBER_COLUMN	= "rg_number";
	private static final String SECONDARY_PHONE_COLUMN	= "secondary_phone";
	private static final String PRINCIPAL_PHONE_COLUMN	= "principal_phone";
	private static final String NUMBER_COLUMN	= "number";
	private static final String COMPLEMENT_COLUMN	= "complement";
	private static final String CITY_COLUMN	= "city";
	private static final String CEP_COLUMN	= "cep";
	private static final String ADDRESS_COLUMN	= "address_info";
	private static final String STATUS_COLUMN	= "status";
	private static final String SERVICE_TABLE_NAME = "Service";
	private static final String SERVICE_COURSE_TABLE_NAME = "ServiceCourse";
	private static final String PACKAGE_COURSE_TABLE_NAME = "PackageCourse";
	private static final String SERVICE_PACKAGE_TABLE_NAME = "ServicePackage";
	
	private static final String CANT_SAVE_STUDENT = "Não foi possível salvar os dados do estudante informado.";
	private static final String CPF_ALREADY_EXISTS = "O CPF informado já está cadastrado.";
	private static final String COULDNT_CHECK_STUDENT = "Não foi possível checar se o estudante está cadastrado. Tente novamente.";
	private static final String COULDNT_LOAD_STUDENTS_OF_COURSE = "Não foi possível carregar os estudantes do curso informado.";
	private static final String COULDNT_FIND_STUDENTS = "Não foi possível encontrar os estudantes";

	public void save(Student student) throws StudentException, PersonException{
		
		try{
			Student previousStudent = get(student.getCpf());
			
			if(previousStudent == null){
				try{
					String secondaryPhone;
					if(student.getSecondaryPhone() != null){
						secondaryPhone = student.getSecondaryPhone().getWholePhone();
					}
					else{
						secondaryPhone = "";
					}
					
					String query = "INSERT INTO "+ STUDENT_TABLE_NAME +" ("+ CPF_COLUMN +", "+ NAME_COLUMN +", "
							   + BIRTHDATE_COLUMN +", "+ EMAIL_COLUMN +", "+ MOTHER_COLUMN +", "
							   + FATHER_COLUMN +", "+ UF_COLUMN +", "+ ISSUING_INSTITUTION_COLUMN +", "
							   + RG_NUMBER_COLUMN +", "+ PRINCIPAL_PHONE_COLUMN +", "+ SECONDARY_PHONE_COLUMN +", "
							   + COMPLEMENT_COLUMN +", "+ NUMBER_COLUMN +", "+ CITY_COLUMN +", "+ CEP_COLUMN +", "
							   + ADDRESS_COLUMN +")"
							   + " VALUES ('"+ student.getCpf().getCpf() +"', '"+ student.getName() +"', '"
							   + student.getBirthdate().getHyphenFormattedDate() +"', '"+ student.getEmail() +"', '"
							   + student.getMotherName() +"', '"+ student.getFatherName() +"', '"
							   + student.getRg().getUf() +"', '"
							   + student.getRg().getIssuingInstitution() +"', '"+ student.getRg().getRgNumber() +"', '"
							   + student.getPrincipalPhone().getWholePhone() +"', '"
							   + secondaryPhone +"', '"+ student.getAddress().getComplement() +"','"
							   + student.getAddress().getNumber() +"', '"+ student.getAddress().getCity() +"', '"
							   + student.getAddress().getCep() +"', '"+ student.getAddress().getAddressInfo() +"')";

					this.execute(query);
				}
				catch(SQLException e){
					throw new StudentException(CANT_SAVE_STUDENT);
				}
			}
			else{
				throw new StudentException(CPF_ALREADY_EXISTS);
			}
		}
		catch(PhoneException | CPFException | DateException |
			  AddressException | RGException e1){
			
			throw new StudentException(COULDNT_CHECK_STUDENT );
		}
	}
	
	/**
	 * Gets the students with the searched name
	 * @param searchedStudentName - searched name by the user
	 * @return an arraylist with the found students
	 * @throws StudentException
	 * @throws CPFException
	 * @throws PersonException 
	 */
	public ArrayList<Student> get(String searchedStudentName) throws StudentException, CPFException, PersonException {
		
		ResultSet resultOfTheSearch = null;
		String query = "SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + NAME_COLUMN + " LIKE \"%" + searchedStudentName + "%\""; 
		ArrayList<Student> foundStudents = new ArrayList<Student>();
		Student student = null;
		CPF studentCpf = null;
		
		try{
			resultOfTheSearch = this.search(query);
			
			while(resultOfTheSearch.next()){
				String studentName = (resultOfTheSearch.getString("name_student"));
				String cpf = (resultOfTheSearch.getString("cpf"));
				studentCpf = new CPF(cpf);
				student = new Student(studentName, studentCpf);
				foundStudents.add(student);
			}
			
		}
		catch(SQLException e){
			
		}

		return foundStudents;
	}
	
	/**
	 * Gets the student that has the specific 'cpf'
	 * @param studentCpf - the 'cpf' of the searched student 
	 * @return an object with the data of the student 
	 * @throws PhoneException
	 * @throws CPFException
	 * @throws DateException
	 * @throws AddressException
	 * @throws RGException
	 * @throws StudentException
	 * @throws PersonException 
	 */
	public Student get(CPF studentCpf) throws PhoneException, CPFException, DateException, AddressException,
												RGException, StudentException, PersonException {
		
		ResultSet resultOfTheSearch = null;
		String receivedCPF = studentCpf.getCpf();
		
		String query = "SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + CPF_COLUMN + "= '" + receivedCPF + "'"; 
		Student student = null;

		try{
			resultOfTheSearch = this.search(query);
			while(resultOfTheSearch.next()){
				student = getFoundStudent(resultOfTheSearch);
			}
		}
		catch(SQLException e){
			throw new StudentException(COULDNT_FIND_STUDENTS);
		}
		return student;
	}

	/**
	 * Gets the data of the student
	 * @param resultOfTheSearch the row from database that contains the data of the student
	 * @return an object with the data of the student
	 * @throws PhoneException
	 * @throws SQLException
	 * @throws CPFException
	 * @throws DateException
	 * @throws AddressException
	 * @throws RGException
	 * @throws StudentException
	 * @throws PersonException 
	 */
	private Student getFoundStudent(ResultSet resultOfTheSearch) throws PhoneException, SQLException, 
	CPFException, DateException, AddressException, RGException, StudentException, PersonException {

		// Get the data from database
		String studentName = resultOfTheSearch.getString(NAME_COLUMN);

		String email = resultOfTheSearch.getString(EMAIL_COLUMN);
		String motherName = resultOfTheSearch.getString(MOTHER_COLUMN);
		String fatherName = resultOfTheSearch.getString(FATHER_COLUMN);

		//CPF
		String cpf = resultOfTheSearch.getString(CPF_COLUMN);
		CPF studentCpf = new CPF(cpf);

		//RG
		String rg = resultOfTheSearch.getString(RG_NUMBER_COLUMN);
		String uf = resultOfTheSearch.getString(UF_COLUMN);
		String issuing_institution = resultOfTheSearch.getString(ISSUING_INSTITUTION_COLUMN);
		RG studentRg = new RG(rg,issuing_institution,uf);

		//Address
		String city = resultOfTheSearch.getString(CITY_COLUMN);
		String addressInfo = resultOfTheSearch.getString(ADDRESS_COLUMN);
		String complement = resultOfTheSearch.getString(COMPLEMENT_COLUMN);
		String number = resultOfTheSearch.getString(NUMBER_COLUMN);
		String cep = resultOfTheSearch.getString(CEP_COLUMN);
		Address address = new Address(addressInfo, number, complement, cep,city);

		//Phones
		String cellPhone = resultOfTheSearch.getString(PRINCIPAL_PHONE_COLUMN);
		String residencePhone = resultOfTheSearch.getString(SECONDARY_PHONE_COLUMN);
		String DDDPrincipalPhone = cellPhone.substring(0,2);
		String numberPrincipalPhone = cellPhone.substring(2,10);
		
		String DDDSecondaryPhone;
		String numberSecondaryPhone;
		Phone principalPhone;
		Phone secondaryPhone;
		
		if(!residencePhone.isEmpty()){
			
			DDDSecondaryPhone = residencePhone.substring(0,2);
			numberSecondaryPhone = residencePhone.substring(2,10);
			principalPhone = new Phone(DDDPrincipalPhone,numberPrincipalPhone);
			secondaryPhone = new Phone(DDDSecondaryPhone,numberSecondaryPhone);
		}else{
			principalPhone = new Phone(DDDPrincipalPhone,numberPrincipalPhone);
			secondaryPhone = null;
		}

		Date birthdate = birthdate(resultOfTheSearch);
		//Status
		int status = resultOfTheSearch.getInt(STATUS_COLUMN);
		
		Student student = new Student(studentName, studentCpf, studentRg, birthdate, email, address,
									 principalPhone, secondaryPhone, motherName, fatherName, status);
	
		return student;
	}

	private Date birthdate(ResultSet resultOfTheSearch) throws SQLException, NumberFormatException, DateException {
		String date = resultOfTheSearch.getString(BIRTHDATE_COLUMN);
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		Date birthdate = new Date(new Integer(day), new Integer(month), new Integer(year));
		return birthdate;
	}
	
	public boolean updateStudent(Student student){
		String birthdate = Integer.toString(student.getBirthdate().getYear()) + "-" +
				Integer.toString(student.getBirthdate().getMonth()) + "-" +
				Integer.toString(student.getBirthdate().getDay());
		
		
		String query = "UPDATE " + STUDENT_TABLE_NAME + " SET "
				+ NAME_COLUMN + " = '" + student.getName() + "', "
				+ BIRTHDATE_COLUMN + " = '" + birthdate + "', "
				+ EMAIL_COLUMN + " = '" + student.getEmail() + "', "
				+ MOTHER_COLUMN + " = '" + student.getMotherName() + "', "
				+ FATHER_COLUMN + " = '" + student.getFatherName() + "', "
				+ PRINCIPAL_PHONE_COLUMN + " = '" + student.getPrincipalPhone().getWholePhone() + "', "
				+ SECONDARY_PHONE_COLUMN + " = '" + student.getSecondaryPhone().getWholePhone() + "', "
				+ ADDRESS_COLUMN + " = '" + student.getAddress().getAddressInfo() + "', "
				+ CITY_COLUMN + " = '" + student.getAddress().getCity() + "', "
				+ CEP_COLUMN + " = '" + student.getAddress().getCep() + "'"
				+ " WHERE " + CPF_COLUMN + " = '" + student.getCpf().getCpf() + "'"
				;

		boolean wasUpdate;
		try {
			this.execute(query);
			wasUpdate = true;
		} catch (SQLException e) {
			wasUpdate = false;
		}
		
		
		return wasUpdate;

		
		
	}

	public boolean update(Student student) {
		
		int newStatus = newStatus(student);
		CPF cpf = student.getCpf();
		String studentCPF = cpf.getCpf();
		boolean wasUpdate = false;
		String query = "UPDATE "+ STUDENT_TABLE_NAME + " SET "
				   + STATUS_COLUMN + "=" + newStatus 
				   + " WHERE " + CPF_COLUMN + "='" + studentCPF + "'";
		
		try {
			this.execute(query);
			wasUpdate = true;
		} catch (SQLException e) {
			wasUpdate = false;
		}
		
		
		return wasUpdate;
	}

	private int newStatus(Student student) {
		int studentStatus = student.getStatus();
		int newStatus = -1;
		newStatus = getStudentStatusObject(studentStatus).newStatus(newStatus, student);
		return newStatus;
	}

	public ArrayList<Student> get(Course course) throws StudentException{
		
		String query = query(course);
		ArrayList<Student> students = new ArrayList<Student>();
		
		try{
			ResultSet foundStudents = this.search(query);
			
			while(foundStudents.next()){
				
				try{
					String studentName = foundStudents.getString(NAME_COLUMN);
					String cpf = foundStudents.getString(CPF_COLUMN);
					CPF studentCpf = new CPF(cpf);
					String rgNumber = foundStudents.getString(RG_NUMBER_COLUMN);
					String rgIssuingInstitution = foundStudents.getString(ISSUING_INSTITUTION_COLUMN);
					String uf = foundStudents.getString(UF_COLUMN);
					RG studentRg = new RG(rgNumber, rgIssuingInstitution, uf);
					Date birthdate = new Date(foundStudents.getString(BIRTHDATE_COLUMN));
					String studentEmail = foundStudents.getString(EMAIL_COLUMN);
					String addressInfo = foundStudents.getString(ADDRESS_COLUMN);
					String number = foundStudents.getString(NUMBER_COLUMN);
					String complement = foundStudents.getString(COMPLEMENT_COLUMN);
					String cep = foundStudents.getString(CEP_COLUMN);
					String city = foundStudents.getString(CITY_COLUMN);
					Address address = new Address(addressInfo, number, complement, cep, city);
					String phone1 = foundStudents.getString(PRINCIPAL_PHONE_COLUMN);
					Phone principalPhone = new Phone(phone1);
					
					Phone secondaryPhone = secondaryPhone(foundStudents);
					String motherName = foundStudents.getString(MOTHER_COLUMN);
					String fatherName = foundStudents.getString(FATHER_COLUMN);
					Integer status = foundStudents.getInt(STATUS_COLUMN);
					
					Student student = new Student(studentName, studentCpf, studentRg, birthdate,
												  studentEmail, address, principalPhone, secondaryPhone,
												  motherName, fatherName, status);
					
					students.add(student);
				}
				catch(CPFException | RGException | DateException | AddressException | PhoneException | PersonException e){
					// Just don't add this student
				}
			}
		}
		catch(SQLException e){
			throw new StudentException(COULDNT_LOAD_STUDENTS_OF_COURSE);
		}
		
		return students;
	}

	private String query(Course course) {
		Integer courseId = course.getId();
		String query = "";
		query += "SELECT DISTINCT s.* ";
		query += "FROM " + STUDENT_TABLE_NAME + " s, " + SERVICE_TABLE_NAME + " sv, " + SERVICE_COURSE_TABLE_NAME
				+ " sc, " + PACKAGE_COURSE_TABLE_NAME + " pc, " + SERVICE_PACKAGE_TABLE_NAME + " sp";
		query += " WHERE (sv.cpf = s.cpf AND sc.id_service = sv.id_service AND sc.id_course = " + courseId + ") OR";
		query += " (sv.cpf = s.cpf AND sp.id_service = sv.id_service AND sp.id_package = pc.id_package AND pc.id_course = "
				+ courseId + ")";
		return query;
	}

	private Phone secondaryPhone(ResultSet foundStudents) throws SQLException, PhoneException {
		String phone2 = foundStudents.getString(SECONDARY_PHONE_COLUMN);
		Phone secondaryPhone;
		if (!phone2.isEmpty() && phone2 != "null") {
			secondaryPhone = new Phone(phone2);
		} else {
			secondaryPhone = null;
		}
		return secondaryPhone;
	}

	private StudentStatus getStudentStatusObject(int studentStatus) {
		if (studentStatus == Person.ACTIVE)
			return new Active();
		if (studentStatus == Person.INACTIVE)
			return new Inactive();
		return null;
	}
}
