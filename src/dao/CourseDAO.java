package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exception.CourseException;
import model.Course;

public class CourseDAO extends DAO {

	// Error messages
	private static final String COULDNT_UPDATE_COURSE = "Não foi possível alterar o curso";
	private static final String COULDNT_SAVE_COURSE = "Não foi possível cadastrar o curso";

	// Course table on database constants
	private static final String TABLE_NAME = "Course";
	public static final String ID_COLUMN = "id_course";
	public static final String NAME_COLUMN = "course_name";
	public static final String DESCRIPTION_COLUMN = "description";
	public static final String DURATION_COLUMN = "duration";
	public static final String VALUE_COLUMN = "value";
	public static final String STATUS_COLUMN = "status";

	
	/**
	 * Save the informed course into the database
	 * @param course - a Course object with the course information to be saved
	 * @param hasId - inform if the Course object has an specific ID 
	 * @return TRUE if the course was saved on the database, or FALSE if it does not
	 * @throws CourseException 
	 */
	public void save(Course course, boolean hasId) throws CourseException{
		
		String query = query(course, hasId);
		try{
			
			this.execute(query);
		}
		catch(SQLException caughtException){
			throw new CourseException(COULDNT_SAVE_COURSE);
		}
	}

	private String query(Course course, boolean hasId) {
		String courseName = course.getName();
		String courseDescription = course.getCourseDescription();
		Integer courseDuration = course.getDuration();
		Integer courseValue = course.getValue();
		String query = "";
		if (hasId) {
			int courseId = course.getId();
			query = "INSERT INTO " + TABLE_NAME + "(" + ID_COLUMN + ", " + NAME_COLUMN + ", " + DESCRIPTION_COLUMN
					+ ", " + DURATION_COLUMN + ", " + VALUE_COLUMN + ")";
			query += "VALUES('" + courseId + "', '" + courseName + "','" + courseDescription + "', '" + courseDuration
					+ "', '" + courseValue + "')";
		} else {
			query = "INSERT INTO " + TABLE_NAME + "(" + NAME_COLUMN + ", " + DESCRIPTION_COLUMN + ", " + DURATION_COLUMN
					+ ", " + VALUE_COLUMN + ")";
			query += "VALUES('" + courseName + "','" + courseDescription + "', '" + courseDuration + "', '"
					+ courseValue + "')";
		}
		return query;
	}
	
	/**
	 * Update a given course on the database
	 * @param courseId - The course to be updated
	 * @param course - A Course object with the course new data
	 * @return TRUE if the course was updated on database or FALSE if it does not
	 * @throws CourseException 
	 */
	public void update(Integer courseId, Course course) throws CourseException{
		
		String courseDescription = course.getCourseDescription();
		Integer courseDuration = course.getDuration();  
		Integer courseValue = course.getValue();
		
		String query = "UPDATE "+ TABLE_NAME + " SET "
					   + DESCRIPTION_COLUMN + "='" + courseDescription + "', "
					   + DURATION_COLUMN + "='" + courseDuration + "', "
					   + VALUE_COLUMN + "='" + courseValue + "' "
					   + "WHERE " + ID_COLUMN + "='" + courseId + "'";
			
		try{
			
			this.execute(query);
		}
		catch(SQLException caughtException){
			throw new CourseException(COULDNT_UPDATE_COURSE);
		}
	}
	
	/**
	 * Searches the informations of a course 
	 * @param course - Course object with the course to be searched 
	 * @return the found course
	 * @throws CourseException 
	 */
	public Course get(Course course, boolean hasId) throws CourseException{
		
		ResultSet resultOfTheSearch = null;
		String query = null;
				
		if(hasId){
			int courseId = course.getId();
			query = ("SELECT * FROM "+ TABLE_NAME + " WHERE " + ID_COLUMN + " = " + courseId);

			try{
				resultOfTheSearch = this.search(query);
				
				while(resultOfTheSearch.next()){
					courseId = resultOfTheSearch.getInt(ID_COLUMN);
					String courseName = resultOfTheSearch.getString(NAME_COLUMN);
					String courseDescription = resultOfTheSearch.getString(DESCRIPTION_COLUMN);
					Integer courseValue = resultOfTheSearch.getInt(VALUE_COLUMN);
					Integer courseDuration = resultOfTheSearch.getInt(DURATION_COLUMN);
					Integer courseStatus = 	resultOfTheSearch.getInt(STATUS_COLUMN);
					
					course = new Course(courseId, courseName, courseDescription, courseDuration, courseValue, courseStatus);
				}
			}
			catch(SQLException caughtException){
				
				course = null;
			}
		}
		else{
			// Nothing to do
		}
		return course;
	}
	
	/**
	 * Searches the informations of a course 
	 * @param course - Course object with the course to be searched 
	 * @return an array with the found courses
	 * @throws CourseException 
	 */
	public ArrayList<Course> get(Course course) throws CourseException{
	
		ResultSet resultOfTheSearch = null;
		String query = null;
		String courseName = course.getName();
		ArrayList<Course> courses = new ArrayList<Course>();
		
		query = ("SELECT * FROM "+ TABLE_NAME + " WHERE " + NAME_COLUMN + " LIKE \"%" + courseName + "%\"");
		try{
			resultOfTheSearch = this.search(query);
			
			while(resultOfTheSearch.next()){
				int courseId = resultOfTheSearch.getInt(ID_COLUMN);
				courseName = resultOfTheSearch.getString(NAME_COLUMN);
				String courseDescription = resultOfTheSearch.getString(DESCRIPTION_COLUMN);
				Integer courseValue = resultOfTheSearch.getInt(VALUE_COLUMN);
				Integer courseDuration = resultOfTheSearch.getInt(DURATION_COLUMN);
				Integer courseStatus = 	resultOfTheSearch.getInt(STATUS_COLUMN);
				
				course = new Course(courseId, courseName, courseDescription, courseDuration, courseValue, courseStatus);
				courses.add(course);		
			}
		}
		catch(SQLException caughtException){
			System.out.println(caughtException.getMessage());
			courses = null;
		}
		
		return courses;
		
		
	}

	/**
	 * Gets all courses from database
	 * @return the data produced by the given query
	 * @throws CourseException 
	 */
	public ArrayList<Course> get() throws CourseException{
		
		ResultSet resultOfTheSearch = null;
		String query = null;
		Course course = new Course();
		ArrayList<Course> courses = new ArrayList<Course>();

		query = ("SELECT * FROM "+ TABLE_NAME + " WHERE " + STATUS_COLUMN + "=" + 1);
		try{
			resultOfTheSearch = this.search(query);
			while(resultOfTheSearch.next()){
				int courseStatus = resultOfTheSearch.getInt(STATUS_COLUMN);
				int courseId = resultOfTheSearch.getInt(ID_COLUMN);
				String courseName = resultOfTheSearch.getString(NAME_COLUMN);
				String courseDescription = resultOfTheSearch.getString(DESCRIPTION_COLUMN);
				Integer courseValue = resultOfTheSearch.getInt(VALUE_COLUMN);
				Integer courseDuration = resultOfTheSearch.getInt(DURATION_COLUMN);
				
				course = new Course(courseId, courseName, courseDescription, courseDuration, courseValue, courseStatus);
				courses.add(course);		
			}
		}
		catch(SQLException caughtException){
			
			courses = null;
		}		

		return courses;
	}

	/**
	 * Change course's status into the database
	 * @param course - Course object that will be activated or deactivated
	 * @return TRUE if the course's status was changed
	 * @throws CourseException 
	 */
	public boolean updateStatus(int courseId, int newCourseStatus) throws CourseException{

		boolean statusWasAltered;
		
		String query = "UPDATE " + TABLE_NAME + 
				" SET " + STATUS_COLUMN + "=" + newCourseStatus + 
				" WHERE " + ID_COLUMN + "=" + courseId;
		
		try{
			this.execute(query);
			statusWasAltered = true;
		} catch (SQLException caughtException){
			statusWasAltered = false;
		}
		
		return statusWasAltered;
	}
	
	/**
	 * Returns course by Id
	 * @param courseId - id of the course that wants to be return
	 * @return course if the course could be select in the database or null if not
	 * @throws CourseException 
	 */
	public Course get(int courseId) throws CourseException {
		String query = "SELECT * FROM " + TABLE_NAME + "WHERE " + ID_COLUMN + "=" + courseId;
		
		Course course;
		
		ResultSet result;
		
		try {
			result = search(query);
			result.next();
			course = new Course(result.getInt("id_course"),result.getString("course_name"),
					result.getString("description"), result.getInt("duration") , result.getInt("value"), 
					result.getInt("status"));
			return course;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	/**
	 * Returns status of a course by Id
	 * @param courseId - id of the course that wants to be return status
	 * @return status of the course or throws an error
	 * @throws CourseException 
	 */
	public int getStatus(int courseId) {
		String query = "SELECT " + STATUS_COLUMN + " FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "=" + courseId;
		ResultSet result;
		
		try {
			result = search(query);
			result.next();
			return result.getInt(STATUS_COLUMN);
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			return -1;
		}
	}

	/**
	 * Alter status of a course by id
	 * @param idCourse  - id of the course that will be status altered
	 * @return  boolean statusWasAltered (true if status was altered or false if else)
	 */
	public boolean alterStatusCourse(int idCourse) throws CourseException {
		boolean statusWasAltered;
		int teste = getStatus(idCourse);
		if (teste == 1) {
			statusWasAltered = updateStatus(idCourse, 0);
		} else {
			statusWasAltered = updateStatus(idCourse, 1);
		}
		return statusWasAltered;
	}
}
