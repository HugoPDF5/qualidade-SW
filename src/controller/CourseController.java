package controller;

import java.util.ArrayList;

import dao.CourseDAO;
import exception.CourseException;
import model.Course;

public class CourseController {
	
	private CourseDAO courseDAO;
	
	public CourseController(){
		courseDAO = new CourseDAO();
	}

	/**
	 * Create a new course with the given information
	 * @param courseName - Name of the course
	 * @param courseDescription - Description of the course
	 * @param courseDuration - Duration of the course
	 * @param courseValue - Value of the course
	 * @return TRUE if the course was created or FALSE if it does not
	 * @throws CourseException
	 */
	public Course newCourse(String courseName, String courseDescription,
							 Integer courseDuration, Integer courseValue)
							 throws CourseException{
		
		Course course = new Course(courseName, courseDescription, courseDuration, courseValue);
		
		boolean hasId = false;
		boolean wasSaved = saveCourse(course, hasId);
		
		if(!wasSaved){
			course = null;
		}
		else{
			// Nothing to do
		}
		
		return course;
	}
	
	/**
	 * Create a new course with the given information plus an ID
	 * @param courseId - Id of the course to be created
	 * @param courseName - Name of the course
	 * @param courseDescription - Description of the course
	 * @param courseDuration - Duration of the course
	 * @param courseValue - Value of the course
	 * @return TRUE if the course was created or FALSE if it does not
	 * @throws CourseException
	 */
	public Course newCourse(int courseId, String courseName, String courseDescription,
							 Integer courseDuration, Integer courseValue)
							 throws CourseException{
		
		Course course = new Course(courseId, courseName, courseDescription, courseDuration, courseValue);
		
		boolean hasId = true;
		boolean wasSaved = saveCourse(course, hasId);
		
		if(!wasSaved){
			course = null;
		}
		else{
			// Nothing to do
		}
		
		return course;
	}
	
	/**
	 * Communicate with the DAO layer to save the course
	 * @param course - Course object with the course to be saved
	 * @param hasId - Inform if the course object has an ID associated
	 * @return a boolean with the result from the DAO layer
	 * @throws CourseException 
	 */
	private boolean saveCourse(Course course, boolean hasId) throws CourseException{
		
		boolean wasSaved =  false;
				
		try {
			courseDAO.save(course, hasId);
			wasSaved = true;
		} 
		catch (CourseException e) {
			throw new CourseException(e.getMessage());
		}
		
		return wasSaved;
	}
	
	
	
	/**
	 * Update a given course with the new information
	 * @param courseId - The course id to be updated
	 * @param courseName - The name of the course
	 * @param courseDescription - The new description of the course
	 * @param courseDuration - The new duration of the course
	 * @param courseValue - The new value of the course
	 * @return TRUE if the course was updated or FALSE if it does not
	 * @throws CourseException
	 */
	public Course updateCourse(Integer courseId, String courseName, String courseDescription,
								 Integer courseDuration, Integer courseValue)
								 throws CourseException{
		
		Course course = new Course(courseName, courseDescription, courseDuration, courseValue);
		
		try{
			courseDAO.update(courseId, course);
		}
		catch(CourseException e){
			course = null;
			throw new CourseException(e.getMessage());
		}

		return course;
	}
	
	/**
	 * Show the information of a course searched by user
	 * @param searchedCourse - The name of course to be searched
	 * @return 
	 * @throws CourseException
	 */
	public ArrayList<Course> showCourse(String searchedCourse) throws CourseException{
		
		Course course = new Course(searchedCourse);
		ArrayList<Course> courses = new ArrayList<Course>();

		courses = courseDAO.get(course);

		return courses;
	}
	
	/**
	 * Show the name and the status of all courses registered
	 * @return the data produced by the given query
	 * @throws CourseException 
	 */
	public ArrayList<Course> showCourse() throws CourseException{
		
		ArrayList<Course> courses = new ArrayList<Course>();

		courses = courseDAO.get();
		
		return courses;
	}

	public Course get(Integer courseId){
	
		Course foundCourse = null;
		
		try{
			foundCourse = showCourse(courseId);
		}
		catch (CourseException e){
			// Nothing to do
		}
		
		return foundCourse;
	}
	
	/** 
	 * Show the information of a course searched by user
	 * @param idCourse - The id of course to be searched
	 * @return the data produced by the given query
	 * @throws CourseException
	 */
	public Course showCourse(int idCourse) throws CourseException{
		
		Course course = new Course(idCourse);
		boolean hasId = true;

		course = courseDAO.get(course, hasId);
	
		return course;
	}
	
	/**
	 * Alter status of a course by id
	 * @param idCourse - id of the course that will be status altered
	 * @return boolean statusWasAltered (true if status was altered or false if else)
	 */
	public boolean alterStatusCourse(int idCourse) throws CourseException{
		
		return courseDAO.alterStatusCourse(idCourse);
		
	}

	public void setCourseDAO(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

}
