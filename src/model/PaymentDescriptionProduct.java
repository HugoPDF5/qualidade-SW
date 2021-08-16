package model;


import exception.PaymentException;

public class PaymentDescriptionProduct {
	private static final int CASH_PAYMENT_TYPE = 1;
	private static final int PAYMENT_IN_INSTALLMENTS_TYPE = 2;
	private static final int CASH_FORM = 3;
	private static final int CARD_FORM = 4;
	private static final int CHECK_FORM = 5;
	private int paymentType;
	private int paymentForm;

	public int getPaymentType() {
		return paymentType;
	}

	public int getPaymentForm() {
		return paymentForm;
	}

	public void setPaymentType(int paymentType) throws PaymentException {
		switch (paymentType) {
		case CASH_PAYMENT_TYPE:
			this.paymentType = paymentType;
			break;
		case PAYMENT_IN_INSTALLMENTS_TYPE:
			this.paymentType = paymentType;
			break;
		default:
			throw new PaymentException(PaymentDescription.INVALID_PAYMENT_TYPE);
		}
	}

	public void setPaymentForm(final int paymentForm) throws PaymentException {
		switch (paymentForm) {
		case CASH_FORM:
			this.paymentForm = paymentForm;
			break;
		case CARD_FORM:
			this.paymentForm = paymentForm;
			break;
		case CHECK_FORM:
			this.paymentForm = paymentForm;
			break;
		default:
			throw new PaymentException(PaymentDescription.INVALID_PAYMENT_FORM);
		}
	}
}