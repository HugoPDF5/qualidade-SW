package dao;


import model.Package;
import exception.PackageException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ServiceItem;

public class PackageDAOProduct {
	/**
	* Saves the data of package without the courses
	* @param packageInstance  - a Package object with the package information to be saved
	* @return  TRUE if the package was saved or FALSE if it does not
	* @throws PackageException  
	*/
	public void save(Package packageInstance, PackageDAO packageDAO) throws PackageException {
		String packageName = packageInstance.getName();
		Integer packageValue = packageInstance.getValue();
		Integer packageDuration = packageInstance.getDuration();
		String query = "INSERT INTO " + PackageDAO.TABLE_NAME + "(" + PackageDAO.NAME_COLUMN + ", "
				+ PackageDAO.VALUE_COLUMN + ", " + PackageDAO.DURATION_COLUMN + ")";
		query += "VALUES('" + packageName + "','" + packageValue + "','" + packageDuration + "')";
		try {
			packageDAO.execute(query);
			try {
				saveDataOfPackageCourse(packageInstance, packageDAO);
			} catch (SQLException caughtException) {
				throw new PackageException(PackageDAO.PACKAGE_COURSES_WASNT_SAVED);
			}
		} catch (SQLException caughtException) {
			throw new PackageException(PackageDAO.PACKAGE_WASNT_SAVED);
		}
	}

	/**
	* Update a package on the database 
	* @param packageId  - Id of the package to be updated
	* @param newPackage  - Package object with the data of the new package
	* @throws PackageException
	*/
	public void update(Package newPackage, PackageDAO packageDAO) throws PackageException {
		Integer packageId = newPackage.getId();
		String newPackageName = newPackage.getName();
		Integer newPackageValue = newPackage.getValue();
		Integer newPackageDuration = newPackage.getDuration();
		String query = "UPDATE " + PackageDAO.TABLE_NAME + " SET " + PackageDAO.NAME_COLUMN + "='" + newPackageName
				+ "', " + PackageDAO.VALUE_COLUMN + "='" + newPackageValue + "', " + PackageDAO.DURATION_COLUMN + "='"
				+ newPackageDuration + "' " + "WHERE " + PackageDAO.ID_COLUMN + "='" + packageId + "'";
		try {
			packageDAO.execute(query);
			try {
				updatePackageCourses(newPackage, packageDAO);
			} catch (SQLException caughtException) {
				throw new PackageException(PackageDAO.PACKAGE_COURSES_WASNT_SAVED);
			} catch (PackageException caughtException) {
				throw caughtException;
			}
		} catch (SQLException caughtException) {
			throw new PackageException(PackageDAO.PACKAGE_WASNT_UPDATE);
		}
	}

	/**
	* Saves the data of the association between package and courses
	* @param packageInstance  - a Package object with the association between package and courses information to be saved
	* @throws SQLException  
	*/
	public void saveDataOfPackageCourse(Package packageInstance, PackageDAO packageDAO) throws SQLException {
		Integer packageId = packageInstance.getId();
		ArrayList<ServiceItem> itens = new ArrayList<ServiceItem>();
		itens = packageInstance.getServiceItens();
		for (ServiceItem item : itens) {
			String query = "INSERT INTO " + PackageDAO.ASSOCIATION_TABLE_NAME + "(" + PackageDAO.ID_COLUMN + ", "
					+ PackageDAO.ID_COURSE_COLUMN + ")";
			Integer courseId = item.getId();
			query += "VALUES('" + packageId + "','" + courseId + "')";
			packageDAO.execute(query);
		}
	}

	/**
	* Update the courses of a package on the database
	* @param packageCourses  - Array with the courses to be associated with the package
	* @throws SQLException
	* @throws PackageException
	*/
	public void updatePackageCourses(Package newPackage, PackageDAO packageDAO) throws SQLException, PackageException {
		Integer packageId = newPackage.getId();
		boolean wasDisassociated = disassociateAllCoursesOfPackage(packageId, packageDAO);
		if (wasDisassociated) {
			ArrayList<ServiceItem> itens = newPackage.getServiceItens();
			for (ServiceItem item : itens) {
				Integer courseId = item.getId();
				String associateCoursesToPackage = "INSERT INTO " + PackageDAO.ASSOCIATION_TABLE_NAME + "("
						+ PackageDAO.ID_COLUMN + ", " + PackageDAO.ID_COURSE_COLUMN + ")";
				associateCoursesToPackage += "VALUES('" + packageId + "','" + courseId + "')";
				packageDAO.execute(associateCoursesToPackage);
			}
		} else {
			throw new PackageException(PackageDAO.COULD_NOT_DISASSOCIATE_PACKAGE_COURSES);
		}
	}

	/**
	* Disassociate all the courses of a package
	* @param packageId  - The package to disassociate all courses
	* @return
	*/
	public boolean disassociateAllCoursesOfPackage(Integer packageId, PackageDAO packageDAO) {
		String deleteAllPreviousAssociations = "DELETE FROM " + PackageDAO.ASSOCIATION_TABLE_NAME + " WHERE "
				+ PackageDAO.ID_COLUMN + "= " + packageId;
		boolean disassociated = false;
		try {
			packageDAO.execute(deleteAllPreviousAssociations);
			disassociated = true;
		} catch (SQLException caughtException) {
			disassociated = false;
		}
		return disassociated;
	}

	public void update(int packageId, int status, PackageDAO packageDAO) throws PackageException {
		String query = "UPDATE " + PackageDAO.TABLE_NAME + " SET " + PackageDAO.STATUS_COLUMN + "='" + status + "'"
				+ "WHERE " + PackageDAO.ID_COLUMN + "='" + packageId + "'";
		try {
			packageDAO.execute(query);
		} catch (SQLException caughtException) {
			throw new PackageException(PackageDAO.CANT_UPDATE_STATUS);
		}
	}
}