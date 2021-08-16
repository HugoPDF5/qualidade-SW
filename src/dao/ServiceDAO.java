package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.CourseController;
import controller.PackageController;
import controller.PaymentController;
import controller.StudentController;
import datatype.CPF;
import datatype.Date;
import exception.CourseException;
import exception.DateException;
import exception.PaymentException;
import exception.ServiceException;
import model.Course;
import model.Payment;
import model.Service;
import model.ServiceItem;
import model.Student;
import model.Package;

public class ServiceDAO extends DAO {

	private ServiceDAOProduct serviceDAOProduct = new ServiceDAOProduct();
	private static final String SERVICE_TABLE_NAME = "Service";
	public static final String ID_COLUMN = "id_service";
	private static final String CPF_COLUMN = "cpf";
	public static final String DATE_COLUMN	= "contract_date";
	public static final String VALUE_COLUMN	= "value";
	public static final String TABLE_SERVICE_COURSE_NAME = "ServiceCourse";
	public static final String TABLE_SERVICE_PACKAGE_NAME = "ServicePackage";
	public static final String ID_COURSE_COLUMN = "id_course";
	public static final String ID_PACKAGE_COLUMN = "id_package";
	public static final String PAYMENT_ID_COLUMN = "id_payment";
	private static final String PAYMENT_TABLE = "Payment";
	private static final String COULDNT_SAVE_SERVICE = "Não foi possível salvar os dados do serviço informado.";
	
	private PaymentController paymentController;
	public ServiceDAO(){
		serviceDAOProduct.setCourseController(new CourseController());
		paymentController = new PaymentController();
		serviceDAOProduct.setPackageController(new PackageController());
	}
	
	/**
	 * Save a given service on the database
	 * @param service - The service to be saved
	 * @throws ServiceException
	 */
	public void save(Service service) throws ServiceException{
		
		try{
			
			Integer serviceId = this.getNextId(SERVICE_TABLE_NAME, ID_COLUMN);
			
			String query = "INSERT INTO " + SERVICE_TABLE_NAME;
			   	   query += "("+ ID_COLUMN +"," + CPF_COLUMN + ", " + PAYMENT_ID_COLUMN + ", "; 
			   	   query += VALUE_COLUMN +", "+ DATE_COLUMN +") ";
			   	   query += "VALUES('"+ serviceId +"', '"+ service.getStudent().getCpf().getCpf() +"', '";
			   	   query += service.getPayment().getPaymentId() +"', '"+ service.getTotalValue() +"', CURRENT_DATE())";
			
	   	   this.execute(query);
	   	   	   	   
	   	   saveServiceItens(serviceId, service);
		}
		catch(SQLException e){
			throw new ServiceException(COULDNT_SAVE_SERVICE); 
		}
	}
	
	/**
	 * Save the courses and packages associated with the service
	 * @param serviceId - The service to associate the courses with
	 * @param service - The service with the courses and packages to be associated
	 * @throws SQLException
	 */
	private void saveServiceItens(Integer serviceId, Service service) throws SQLException{
		
		ArrayList<ServiceItem> itens = service.getItens();
		
		for(ServiceItem item : itens){	
			
			String query = "";
			
			boolean isCourse = item.getClass().equals(Course.class);
			if(isCourse){

				Integer currentCourseId = item.getId();
				
				query = "INSERT INTO "+ TABLE_SERVICE_COURSE_NAME +" ("+ ID_COLUMN +", "+ ID_COURSE_COLUMN +") ";
				query += "VALUES ('"+ serviceId +"', '"+ currentCourseId +"')";
				
			}
			// If it is not a course, is a package
			else{
				
				Integer currentPackageId = item.getId();
				
				query = "INSERT INTO "+ TABLE_SERVICE_PACKAGE_NAME +" ("+ ID_COLUMN +", "+ ID_PACKAGE_COLUMN +") ";
			    query += "VALUES ('"+ serviceId +"', '"+ currentPackageId +"')";
			}
							   
			this.execute(query);
		}		
	}
	
	/**
	 * Gets the services of a selected student
	 * @param student - an object with the data of the selected student
	 * @return an array list with the found services
	 * @throws CourseException
	 * @throws DateException
	 * @throws ServiceException
	 * @throws PaymentException 
	 */
	public ArrayList<Service> get(Student student) throws CourseException, DateException, ServiceException, PaymentException {
		
		ResultSet services = null;

		ArrayList<Service> foundServices = new ArrayList<Service>();
		Service service = null;
		
		CPF cpf = student.getCpf();
		String studentCPF = cpf.getCpf();
		String queryForService = "SELECT * FROM " + SERVICE_TABLE_NAME + " WHERE " + CPF_COLUMN + "=\"" + studentCPF + " \""; 
		
		try {
			services = this.search(queryForService);
			
			while(services.next()){
				
				service = serviceDAOProduct.service(student, service, services, this);
				foundServices.add(service);
			}
			
		} 
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return foundServices;
	}
}
