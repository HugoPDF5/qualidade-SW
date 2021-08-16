package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controller.CourseController;
import controller.PackageController;
import dao.ServiceDAO;
import datatype.Date;
import model.Package;
import exception.PaymentException;
import exception.ServiceException;

public class Service{
	
	private ServiceProduct2 serviceProduct2 = new ServiceProduct2();
	private ServiceProduct serviceProduct = new ServiceProduct();
	public static final String STUDENT_OF_SERVICE_CANT_BE_NULL = "O serviço deve ser vinculado a um estudante. Informe um estudante válido.";
	public static final String PAYMENT_CANT_BE_NULL = "O pagamento do serviço não pode estar em branco.";
	public static final String DATE_CANT_BE_NULL = "A data da matrícula não pode estar em branco.";
	private static final String SERVICE_ID_CANT_BE_ZERO = "O ID do serviço deve ser maior que zero.";
	public static final String CANT_ADD_NULL_ITEM_TO_SERVICE = "Não é possível adicionar um item nulo ao serviço.";
	
	private Integer serviceId; 
	public Service(Student student, Integer value) throws ServiceException{

		serviceProduct.setStudent(student);
		setTotalValue(value);
	}
	
	public Service(Integer serviceId, Student student) throws ServiceException{
		
		setServiceId(serviceId);
		serviceProduct.setStudent(student);
	}
	
	public Service(Integer serviceId, Student student, Date contractsDate, Payment payment, Integer value) throws ServiceException{
		
		setServiceId(serviceId);
		serviceProduct.setContractsDate(contractsDate);
		serviceProduct.setStudent(student);
		setTotalValue(value);
		
		try{
			serviceProduct.addPayment(payment, this);
		}
		catch(PaymentException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Service(Student student) throws ServiceException {
		serviceProduct.setStudent(student);
	}

	public void addItem(ServiceItem item) throws ServiceException{
		
		serviceProduct2.addItem(item);
	}
	
	/**
	 * Adds a payment to the service
	 * @param payment - the payment to be added
	 * @throws PaymentException
	 */
	public void addPayment(Payment payment) throws PaymentException{
		
		serviceProduct.addPayment(payment, this);
	}
	
	public void setPayment(Payment payment){
		serviceProduct.setPayment(payment);
	}
	
	private void setServiceId(Integer serviceId) throws ServiceException{
		
		if(serviceId != null && serviceId > 0){
			this.serviceId = serviceId;
		}
		else{
			throw new ServiceException(SERVICE_ID_CANT_BE_ZERO);
		}
	}
	
	private void setTotalValue(Integer value) {
		serviceProduct.setTotalValue(value);
	}
	
	public Integer getTotalValue(){
		return this.serviceProduct.getTotalValue();
	}
	
	public Integer getServiceId(){
		return this.serviceId;
	}

	public Student getStudent(){
		return this.serviceProduct.getStudent();
	}
	
	public Payment getPayment(){
		return this.serviceProduct.getPayment();
	}
	
	public Date getContractsDate(){
		return this.serviceProduct.getContractsDate();
	}
	
	public ArrayList<ServiceItem> getItens(){
		return this.serviceProduct2.getItens();
	}

	public String getTotalValueFormatted() {
				
		return serviceProduct.getTotalValueFormatted();
	}
	
	public String getInstallmentsValue() {
		
		return serviceProduct.getInstallmentsValue();
	}
	
	/**
	 * Get the service itens which is courses
	 * @return An Array of ServiceItem with the courses only
	 */
	public ArrayList<ServiceItem> getCourses(){
		
		return serviceProduct2.getCourses();
	}
	
	/**
	 * Get the service itens which is packages
	 * @return An Array of ServiceItem with the packages only
	 */
	public ArrayList<ServiceItem> getPackages(){
		
		return serviceProduct2.getPackages();
	}

	/**
	 * Adds the courses of a service to it
	 * @param courseController
	 * @return   the service with the added courses
	 * @throws SQLException
	 * @throws ServiceException
	 */
	public Service addServiceCourses(ServiceDAO serviceDAO, CourseController courseController)
			throws SQLException, ServiceException {
		Integer serviceId = getServiceId();
		String queryForCourses = "SELECT * FROM " + ServiceDAO.TABLE_SERVICE_COURSE_NAME + " WHERE "
				+ ServiceDAO.ID_COLUMN + "=" + serviceId;
		ResultSet coursesOfService = serviceDAO.search(queryForCourses);
		while (coursesOfService.next()) {
			Integer courseId = coursesOfService.getInt(ServiceDAO.ID_COURSE_COLUMN);
			Course foundCourse = courseController.get(courseId);
			if (foundCourse != null) {
				addItem(foundCourse);
			}
		}
		return this;
	}

	/**
	 * Adds the packages of a service to it
	 * @param packageController
	 * @return   the service with the added packages
	 * @throws SQLException
	 * @throws ServiceException
	 */
	public Service addServicePackages(ServiceDAO serviceDAO, PackageController packageController)
			throws SQLException, ServiceException {
		Integer serviceId = getServiceId();
		String queryForPackages = "SELECT * FROM " + ServiceDAO.TABLE_SERVICE_PACKAGE_NAME + " WHERE "
				+ ServiceDAO.ID_COLUMN + "=" + serviceId;
		ResultSet packagesOfService = serviceDAO.search(queryForPackages);
		while (packagesOfService.next()) {
			Integer packageId = packagesOfService.getInt(ServiceDAO.ID_PACKAGE_COLUMN);
			Package foundPackage = packageController.getPackage(packageId);
			if (foundPackage != null) {
				addItem(foundPackage);
			} else {
			}
		}
		return this;
	}
}
