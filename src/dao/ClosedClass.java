package dao;


import java.sql.SQLException;
import exception.ClassException;

/**
 * @see dao.ClassDAO#CLOSED_CLASS
 */

public class ClosedClass extends NewStatus {
	
	public void update(int newStatus, String classId, ClassDAO classDAO) {
		try {
			String query = "UPDATE " + ClassDAO.CLASS_TABLE_NAME + " SET " + ClassDAO.STATUS_COLUMN + " = " + newStatus
					+ " WHERE " + ClassDAO.ID_CLASS_COLUMN + " = '" + classId + "'";
			classDAO.execute(query);
		} catch (SQLException e) {
			System.out.println("ERROR 404 NOT FOUND");
		}
	}
}
