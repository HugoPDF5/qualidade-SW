package datatype;

import exception.AddressException;
import model.Model;

public class Address{
	
	private static final String ADDRESS_INFO_CANT_BE_EMPTY = "O logradouro não pode estar em branco.";
	private static final String ADDRESS_NUMBER_CANT_BE_EMPTY = "O número do endereço não pode estar em branco.";
	private static final String CEP_MUST_BE_8_NUMBERS = "O CEP deve conter apenas 8 números.";
	private static final String ADDRESS_CITY_MUST_BE_ONLY_LETTERS_AND_SPACES = "A cidade do endereço deve conter apenas caracteres alfabéticos.";
	
	private static final int CEP_LENGTH = 8;
	
	private String addressInfo;
	private String number;
	private String complement;
	private String cep;
	private String city;
	
	
	public Address(String addressInfo, String number, String complement,
			String cep, String city) throws AddressException {

		setAddressInfo(addressInfo);
		setNumber(number);
		setComplement(complement);
		setCep(cep);
		setCity(city);
	}
	
	public Address(String addressInfo, String cep, String city) throws AddressException {

		setAddressInfo(addressInfo);
		setCep(cep);
		setCity(city);
	}
	
	private void setAddressInfo(String addressInfo) throws AddressException{
		
		if(addressInfo != ""){
			this.addressInfo = addressInfo;
		}
		else{
			throw new AddressException(ADDRESS_INFO_CANT_BE_EMPTY);
		}
	}
	
	private void setNumber(String number) throws AddressException{
		
		if(number != ""){
			this.number = number;
		}
		else{
			throw new AddressException(ADDRESS_NUMBER_CANT_BE_EMPTY);
		}
	}
	
	private void setComplement(String complement){
		
		if(complement != ""){
			this.complement = complement;
		}
		else{
			this.complement = "";
		}
	}
	
	private void setCep(String cep) throws AddressException{
		
		boolean cepIsValid = false;
		
		if(cep.matches("[0-9]+") && cep.length() == CEP_LENGTH){
			cepIsValid = true;
		}
		
		if(cepIsValid){
			this.cep = cep;
		}
		else{
			throw new AddressException(CEP_MUST_BE_8_NUMBERS);
		}
	}
	
	private void setCity(String city) throws AddressException{
		
		boolean containsOnlyLetters = false;
		
		if(city.matches("[a-zA-Zà-úÀ-Ú\\s]+")){
		containsOnlyLetters = true;
		}else{
			containsOnlyLetters = false;
		}  
		
		if(containsOnlyLetters){
			this.city = city;
		}else{
			throw new AddressException(ADDRESS_CITY_MUST_BE_ONLY_LETTERS_AND_SPACES);
		}
	}

	public String getCompleteAddress(){
		
		String addressInfo = getAddressInfo();
		String complement = getComplement();
		String number = getNumber();
		
		String completeAddress = addressInfo + " " + complement + " " + number;

		return completeAddress;
	}
	
	public String getAddressInfo() {
		return addressInfo;
	}

	public String getNumber() {
		return number;
	}

	public String getComplement() {
		return complement;
	}

	public String getCep() {
		return cep;
	}

	public String getCity() {
		return city;
	}
}
