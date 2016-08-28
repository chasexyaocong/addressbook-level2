package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String EXAMPLE = "a/123, Clementi Ave 3, #12-34, 231534";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Person addresses should have block, "
    															+ "street, unitno, postal code "
    															+ "in the respectively format serpated by ','";
    public static final String ADDRESS_VALIDATION_REGEX = ".+";
    public static final String ADDRESS_SPLITER = ",";
    public static final String POSTALCODE_VALIDATION_REGEX = "\\d+";
    public static final int BLOCK = 0;
    public static final int STREET = 1;
    public static final int UNITNO = 2;
    public static final int POSTALCODE = 3;
    public static final String COMMASPACE = ", ";

    public final String value;
    private Block addressBlock;
    private Street addressStreet;
    private UnitNo addressUnitNo;
    private PostalCode addressPostalCode;
    private boolean isPrivate;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address, boolean isPrivate) throws IllegalValueException {
        this.isPrivate = isPrivate;
        if (!isValidAddress(address)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.addressBlock = new Block((addressSpliter(address))[BLOCK]);
        this.addressStreet = new Street((addressSpliter(address))[STREET]);
        this.addressUnitNo = new UnitNo((addressSpliter(address))[UNITNO]);
        this.addressPostalCode = new PostalCode((addressSpliter(address))[POSTALCODE]);
        this.value = this.toString();
    }
    
    /**
     * Remove white space among the string 
     * 
     */
    
    public static void removeWhiteSpace(String [] address){
    	for(int i = 0; i < address.length; i ++){
    		address[i] = address[i].trim();
    	}
    }
    
    public static String[] addressSpliter(String address){
    	String [] splitAddress = address.split(ADDRESS_SPLITER);
    	removeWhiteSpace(splitAddress);
    	return splitAddress;
    }

    /**
     * Returns true if a given string is a valid person address.
     */
    public static boolean isValidAddress(String test){
    	String [] splitAddress = addressSpliter(test);
    	if(splitAddress.length != 4){
    		return false;
    	} 
    	else{
    		Block addressBlock = new Block(splitAddress[BLOCK]);
    		Street addressStreet = new Street(splitAddress[STREET]);
    		UnitNo addressUnitNo = new UnitNo(splitAddress[UNITNO]);
    		String addressPostalCode = splitAddress[POSTALCODE];
    		return addressBlock.isValidBlock() &&
    				addressStreet.isValidStreet() &&
    				addressUnitNo.isValidUnitNo() &&
    				isValidPostalCode(addressPostalCode);
    	}
    }

    /**
     * Returns true if a given string is a valid postal code.
     */
    public static boolean isValidPostalCode(String postalCode){
    	return postalCode.matches(POSTALCODE_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
    	return addressBlock.toString() + COMMASPACE 
    			+ addressStreet.toString() + COMMASPACE 
    			+ addressUnitNo.toString() + COMMASPACE 
    			+ addressPostalCode.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}