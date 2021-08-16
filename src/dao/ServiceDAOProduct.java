package dao;


import controller.CourseController;
import controller.PackageController;
import model.Service;
import java.sql.SQLException;
import exception.ServiceException;
import java.sql.ResultSet;
import model.Course;
import model.Package;
import model.Student;
import exception.DateException;
import exception.PaymentException;
import datatype.Date;
import model.Payment;

public class ServiceDAOProduct {
	private CourseController courseController;
	private PackageController packageController;

	public void setCourseController(CourseController courseController) {
		this.courseController = courseController;
	}

	public void setPackageController(PackageController packageController) {
		this.packageController = packageController;
	}

	public Service service(Student student, Service service, ResultSet services, ServiceDAO serviceDAO)
			throws SQLException, NumberFormatException, DateException, PaymentException, ServiceException {
		int serviceId = services.getInt(ServiceDAO.ID_COLUMN);
		String date = services.getString(ServiceDAO.DATE_COLUMN);
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		Date contractsDate = new Date(new Integer(day), new Integer(month), new Integer(year));
		Integer value = services.getInt(ServiceDAO.VALUE_COLUMN);
		int paymentId = services.getInt(ServiceDAO.PAYMENT_ID_COLUMN);
		Payment payment = new Payment(paymentId);
		service = new Service(serviceId, student, contractsDate, payment, value);
		service = service.addServiceCourses(serviceDAO, courseController);
		service = service.addServicePackages(serviceDAO, packageController);
		return service;
	}
}