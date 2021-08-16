package model;

import exception.PaymentException;

public class PaymentDescription{
	
	private PaymentDescriptionProduct paymentDescriptionProduct = new PaymentDescriptionProduct();
	// Payment types
	public static final int CASH_PAYMENT_TYPE = 1;
	public static final int PAYMENT_IN_INSTALLMENTS_TYPE = 2;
	
	// Payment forms
	public static final int CASH_FORM = 1;
	public static final int CARD_FORM = 2;
	public static final int CHECK_FORM = 3;
	
	public static final String CASH_CASH_DESCRIPTION = "Pagamento a vista em dinheiro.";
	public static final String CASH_CARD_DESCRIPTION = "Pagamento a vista no cartão.";
	public static final String CASH_CHECK_DESCRIPTION = "Pagamento a vista no cheque.";
	
	public static final String INSTALLMENT_CASH_DESCRIPTION = "Pagamento parcelado em dinheiro.";
	public static final String INSTALLMENT_CARD_DESCRIPTION = "Pagamento parcelado no cartão.";
	public static final String INSTALLMENT_CHECK_DESCRIPTION = "Pagamento parcelado no cheque.";
	
	public static final String INVALID_PAYMENT_TYPE = "O tipo de pagamento informado é inválido. Escolha entre pagamento a vista ou parcelado.";
	public static final String INVALID_PAYMENT_FORM = "A forma de pagamento informada é inválida. Escolha entre pagamento com dinheiro, cartão ou cheque.";
	
	private String description;
	private int paymentDescriptionId;
	
	public PaymentDescription(int paymentType, int paymentForm) throws PaymentException{
		
		paymentDescriptionProduct.setPaymentType(paymentType);
		paymentDescriptionProduct.setPaymentForm(paymentForm);
		setPaymentDescription();
	}

	private void setPaymentDescriptionId(int descriptionId){
		this.paymentDescriptionId = descriptionId;
	}
	
	private void setPaymentDescription() throws PaymentException{
		
		int paymentType = this.paymentDescriptionProduct.getPaymentType();
		int paymentForm = this.paymentDescriptionProduct.getPaymentForm();
		
		String description = "";
		switch(paymentType){
		
			case CASH_PAYMENT_TYPE:
				
				switch(paymentForm){
					case CASH_FORM:
						description = CASH_CASH_DESCRIPTION;
						setPaymentDescriptionId(1);
						break;
					case CARD_FORM:
						description = CASH_CARD_DESCRIPTION;
						setPaymentDescriptionId(2);
						break;
					case CHECK_FORM:
						description = CASH_CHECK_DESCRIPTION;
						setPaymentDescriptionId(3);
						break;
					default:
						throw new PaymentException(INVALID_PAYMENT_FORM);
				}
				
				break;
			
			case PAYMENT_IN_INSTALLMENTS_TYPE:
				
				switch(paymentForm){
					case CASH_FORM:
						description = INSTALLMENT_CASH_DESCRIPTION;
						setPaymentDescriptionId(4);
						break;
					case CARD_FORM:
						description = INSTALLMENT_CARD_DESCRIPTION;
						setPaymentDescriptionId(5);
						break;
					case CHECK_FORM:
						description = INSTALLMENT_CHECK_DESCRIPTION;
						setPaymentDescriptionId(6);
						break;
					default:
						throw new PaymentException(INVALID_PAYMENT_FORM);
				}
				break;
				
			default:
				throw new PaymentException(INVALID_PAYMENT_TYPE);
		}
		
		this.description = description;
	}

	public int getPaymentType(){
		return this.paymentDescriptionProduct.getPaymentType();
	}

	public int getPaymentForm(){
		return this.paymentDescriptionProduct.getPaymentForm();
	}

	public String getDescription(){
		return this.description;
	}
	
	public int getPaymentDescriptionId(){
		return this.paymentDescriptionId;
	}
}
