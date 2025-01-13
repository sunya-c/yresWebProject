package com.sunya;

public enum ErrMsg
{
	// Username error
	LENGTH_UNAME_ERR(ErrMsg.LENGTH_UNAME_MESSAGE),
	SPACE_UNAME_ERR(ErrMsg.SPACE_UNAME_MESSAGE),
	DUPLICATE_UNAME_ERR(ErrMsg.DUPLICATE_UNAME_MESSAGE),

	// Password error
	LENGTH_PASS_ERR(ErrMsg.LENGTH_PASS_MESSAGE),
	SPACE_PASS_ERR(ErrMsg.SPACE_PASS_MESSAGE),
	CONFIRM_PASS_ERR(ErrMsg.CONFIRM_PASS_MESSAGE),
	
	// Report title error
	// Report detail error
	EMPTY_ERR(ErrMsg.EMPTY_MESSAGE);
	

	private final static String LENGTH_UNAME_MESSAGE = "Must be 4 - 10 characters.";
	private final static String SPACE_UNAME_MESSAGE = "Remove the space!";
	private final static String DUPLICATE_UNAME_MESSAGE = "This username already exists.";

	private final static String LENGTH_PASS_MESSAGE = "Must be 4 - 20 characters.";
	private final static String SPACE_PASS_MESSAGE = "There's a space in your password!";
	private final static String CONFIRM_PASS_MESSAGE = "Passwords don't match.";
	
	private final static String EMPTY_MESSAGE = "This field is required!";

	private String errMessage;

	// Constructor
	private ErrMsg(String errMessage)
	{
		this.errMessage = errMessage;
	}
	// end -- Constructor

	@Override
	public String toString()
	{
		return errMessage;
	}
	
	/**
	 * Follow these procedures to customize the error message:<br>
	 * 1. Create an ErrMsg object.<br>
	 * 2. Set the message in the created object by setCustomErrMessage("put custom error message here") method.<br>
	 * 3. Pass the particular ErrMsg object to the setter class.
	 * <p>
	 * @param customErrMessage ~ Pass your custom error message here.
	 */
	public void setCustomErrMessage(String customErrMessage)
	{
		this.errMessage = customErrMessage;
	}
	
	
	
	

}
