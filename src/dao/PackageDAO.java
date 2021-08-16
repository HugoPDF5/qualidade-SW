package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.CourseController;
import exception.PackageException;
import model.Course;
import model.Package;
import model.ServiceItem;

public class PackageDAO extends DAO{
	
	private PackageDAOProduct packageDAOProduct = new PackageDAOProduct();
	public static final String PACKAGE_COURSES_WASNT_SAVED = "Não foi possível associar os cursos ao pacote. Tente novamente.";
	public static final String COULD_NOT_DISASSOCIATE_PACKAGE_COURSES = "Não foi possível desassociar os cursos do pacote.";
	public static final String PACKAGE_WASNT_SAVED = "Não foi possível cadastrar o pacote. Tente novamente";
	
	public static final String TABLE_NAME = "Package";
	public static final String NAME_COLUMN = "name";
	public static final String VALUE_COLUMN = "value";
	public static final String DURATION_COLUMN = "duration";
	public static final String ID_COLUMN = "id_package";
	public static final String ASSOCIATION_TABLE_NAME = "PackageCourse";
	public static final String ID_COURSE_COLUMN = "id_course";
	private static final String COURSE_TABLE = "Course";
	private static final String COURSE_NAME_COLUMN = "course_name";
	public static final String STATUS_COLUMN = "status";
	private static final String COULDNT_GET_PACKAGE_COURSES = "Não foi possível pegar os dados dos cursos do pacote.";
	public static final String CANT_UPDATE_STATUS = "Não foi possível alterar o status do pacote";
	public static final String PACKAGE_WASNT_UPDATE = "Não foi possível alterar o pacote";
	
	private CourseController courseController;
	
	public PackageDAO(){
		courseController = new CourseController();
	}
	
	/**
	 * Saves the data of package without the courses
	 * @param packageInstance - a Package object with the package information to be saved
	 * @return TRUE if the package was saved or FALSE if it does not
	 * @throws PackageException 
	 */
	public void save(Package packageInstance) throws PackageException {
		
		packageDAOProduct.save(packageInstance, this);
		
	}
	
	/**
	 * Gets the last package id from database
	 * @return the last ID
	 * @throws PackageException 
	 * @throws SQLException
	 */
	public int getTheLastId() throws PackageException{
		
		int lastId = 0;
		String query = "SELECT " + ID_COLUMN + " FROM " + TABLE_NAME + " ORDER BY ";
		query += ID_COLUMN + " DESC LIMIT 1 ";
		ResultSet result;
		
		try{
			result = this.search(query);
		}catch(SQLException caughtException){
			result = null;
		}
		
		try {
			while(result.next()){
				lastId = Integer.parseInt(result.getString(ID_COLUMN));
			}
		} 
		catch (NumberFormatException | SQLException e) {
			throw new PackageException(PACKAGE_WASNT_SAVED);
		} 
			
		return lastId;
	}
	
	/**
	 * Update a package on the database 
	 * @param packageId - Id of the package to be updated
	 * @param newPackage - Package object with the data of the new package
	 * @throws PackageException
	 */
	public void update(Package newPackage) throws PackageException{
		
		packageDAOProduct.update(newPackage, this);
	}
	
	public Package get(int packageId){
		
		String query = "SELECT "+ TABLE_NAME +".* , " + ASSOCIATION_TABLE_NAME + "." + ID_COURSE_COLUMN;
			   query += " FROM " + TABLE_NAME + " JOIN " + ASSOCIATION_TABLE_NAME;
			   query += " ON " + TABLE_NAME + "." + ID_COLUMN +" = " + ASSOCIATION_TABLE_NAME + "." + ID_COLUMN;
			   query += " WHERE " + TABLE_NAME + "." + ID_COLUMN + "=" + packageId;
		
		Package foundPackage = null;
		try{
			
			ResultSet result = this.search(query);
			if(result.first()){
				
				Integer idPackage = result.getInt(ID_COLUMN);
				String packageName = result.getString(NAME_COLUMN);
				Integer packageValue = result.getInt(VALUE_COLUMN);
				int packageStatus = result.getInt(STATUS_COLUMN);
				Integer courseId = result.getInt(ID_COURSE_COLUMN);

				Course course = courseController.get(courseId);
								
				foundPackage = new Package(idPackage, packageName, packageValue, packageStatus);
				foundPackage.addServiceItem(course);
				
				result.first();
				while(result.next()){
					
					courseId = result.getInt(ID_COURSE_COLUMN);
					course = courseController.get(courseId);
					
					foundPackage.addServiceItem(course);
				}
			}
			else{
				foundPackage = null;
			}			
		}
		catch(SQLException e){
			foundPackage = null;
		}catch(PackageException e){
			foundPackage = null;
		}
		
		return foundPackage;
	}
	
	public ArrayList<Package> get() throws PackageException{
		
		String query = "SELECT * FROM " + TABLE_NAME;
		
		ArrayList<Package> packages = new ArrayList<Package>();
		
		try{
			
			ResultSet result = this.search(query);
			
			while(result.next()){
				
				Integer packageId = result.getInt(ID_COLUMN);
				String packageName = result.getString(NAME_COLUMN);
				Integer packageValue = result.getInt(VALUE_COLUMN);
				Integer status = result.getInt(STATUS_COLUMN);
				
				ArrayList<Course> packageCourses = getPackageCourses(packageId);
								
				Package currentPackage = new Package(packageId, packageName, packageValue, status);
				
				for(Course item : packageCourses){
					currentPackage.addServiceItem(item);
				}
			
				packages.add(currentPackage);
			}
		}
		catch(SQLException e){
			throw new PackageException(COULDNT_GET_PACKAGE_COURSES);
		}
		
		return packages;
	}
	
	private ArrayList<Course> getPackageCourses(Integer packageId) throws SQLException{
		
		String query = "SELECT * FROM "+ ASSOCIATION_TABLE_NAME 
					 + " WHERE "+ ID_COLUMN +" = "+ packageId;
		
		ResultSet result = this.search(query);
		
		ArrayList<Course> packageCourses = new ArrayList<Course>();
		
		while(result.next()){
			
			Integer courseId = result.getInt(ID_COURSE_COLUMN);
			
			Course course = courseController.get(courseId);
			
			packageCourses.add(course);
		}
		
		return packageCourses;
	}
	
	/**
	 * Search and get package(s) name that contains the string parameter package_name
	 * @param packageName
	 * @return ArrayList<Package> of founded packages or null if were not founded packages
	 * @throws PackageException
	 */
	public ArrayList<Package> get(String packageName) throws PackageException{
		
		ResultSet resultSet;
		String query = "SELECT * FROM " + TABLE_NAME 
				+ " WHERE " + NAME_COLUMN 
				+ " LIKE \"%" + packageName + "%\"";
		
		ArrayList<Package> foundPackages = null;
		
		try{
			
			resultSet = this.search(query);
				
			foundPackages = new ArrayList<Package>();
			
			while(resultSet.next()){          
				
				Integer packageId = resultSet.getInt(ID_COLUMN);
				
				Package currentPackage = new Package(
					packageId, 
					resultSet.getString(NAME_COLUMN), 
					resultSet.getInt(VALUE_COLUMN), 
					resultSet.getInt(DURATION_COLUMN),
					resultSet.getInt(STATUS_COLUMN)
				);
				
				ArrayList<Course> packageCourses = new ArrayList<Course>();
				packageCourses = getPackageCourses(packageId);
				
				for(Course course : packageCourses){
					currentPackage.addServiceItem(course);
				}
				
		       foundPackages.add(currentPackage);
			}
		
		}catch(SQLException caughtException){
			
			caughtException.printStackTrace();
			foundPackages = null;
		}
		
		return foundPackages;		
	}
	
	public void update(int packageId, int status) throws PackageException {
		
		packageDAOProduct.update(packageId, status, this);
		
	}

}
