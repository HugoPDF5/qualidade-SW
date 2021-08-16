package model;


import java.util.ArrayList;
import exception.ServiceException;

public class ServiceProduct2 {
	private ArrayList<ServiceItem> itens = new ArrayList<ServiceItem>();

	public ArrayList<ServiceItem> getItens() {
		return itens;
	}

	public void addItem(ServiceItem item) throws ServiceException {
		if (item != null) {
			this.itens.add(item);
		} else {
			throw new ServiceException(Service.CANT_ADD_NULL_ITEM_TO_SERVICE);
		}
	}

	/**
	* Get the service itens which is courses
	* @return  An Array of ServiceItem with the courses only
	*/
	public ArrayList<ServiceItem> getCourses() {
		ArrayList<ServiceItem> items = itens;
		ArrayList<ServiceItem> courses = new ArrayList<ServiceItem>();
		for (ServiceItem item : items) {
			boolean isCourse = item.getClass().equals(Course.class);
			if (isCourse) {
				courses.add(item);
			}
		}
		return courses;
	}

	/**
	* Get the service itens which is packages
	* @return  An Array of ServiceItem with the packages only
	*/
	public ArrayList<ServiceItem> getPackages() {
		ArrayList<ServiceItem> items = itens;
		ArrayList<ServiceItem> packages = new ArrayList<ServiceItem>();
		for (ServiceItem item : items) {
			boolean isPackage = item.getClass().equals(Package.class);
			if (isPackage) {
				packages.add(item);
			}
		}
		return packages;
	}
}