package dao;

import exception.ClassException;

public abstract class NewStatus {
	public abstract void update(int newStatus, String classId, ClassDAO classDAO) throws ClassException;
}