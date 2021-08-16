package model;


import datatype.Date;
import exception.ServiceException;
import exception.PaymentException;

public class ServiceProduct {
	private Student student;
	private Payment payment;
	private Date contractsDate;
	private Integer totalValue;

	public Student getStudent() {
		return student;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Date getContractsDate() {
		return contractsDate;
	}

	public Integer getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Integer totalValue) {
		this.totalValue = totalValue;
	}

	public void setStudent(Student student) throws ServiceException {
		if (student != null) {
			this.student = student;
		} else {
			throw new ServiceException(Service.STUDENT_OF_SERVICE_CANT_BE_NULL);
		}
	}

	/**
	* Adds a payment to the service
	* @param payment  - the payment to be added
	* @throws PaymentException
	*/
	public void addPayment(Payment payment, Service service) throws PaymentException {
		if (payment != null) {
			service.setPayment(payment);
		} else {
			throw new PaymentException(Service.PAYMENT_CANT_BE_NULL);
		}
	}

	public String getInstallmentsValue() {
		String formattedInstallmentsValue = null;
		Integer value = totalValue;
		int installments = payment.getInstallments();
		Integer installmentsValue = value / installments;
		if (installmentsValue != 0) {
			formattedInstallmentsValue = installmentsValue.toString();
			int lastIndex = formattedInstallmentsValue.length();
			String entireValue = formattedInstallmentsValue.substring(0, (lastIndex - 2));
			String decimalValue = formattedInstallmentsValue.substring((lastIndex - 2), lastIndex);
			formattedInstallmentsValue = "R$ " + entireValue + "," + decimalValue;
		} else {
			formattedInstallmentsValue = "R$ 0,00";
		}
		return formattedInstallmentsValue;
	}

	public String getTotalValueFormatted() {
		String formattedValue = null;
		Integer value = totalValue;
		if (value != 0) {
			formattedValue = value.toString();
			int lastIndex = formattedValue.length();
			String entireValue = formattedValue.substring(0, (lastIndex - 2));
			String decimalValue = formattedValue.substring((lastIndex - 2), lastIndex);
			formattedValue = "R$ " + entireValue + "," + decimalValue;
		} else {
			formattedValue = "R$ 0,00";
		}
		return formattedValue;
	}

	public void setContractsDate(Date contractsDate) throws ServiceException {
		if (contractsDate != null) {
			this.contractsDate = contractsDate;
		} else {
			throw new ServiceException(Service.DATE_CANT_BE_NULL);
		}
	}
}