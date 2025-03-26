package com.sunya.yresWebProject.restrictions;

/**
 * Presets of err messages.
 */
public enum ErrMsg
{
	// CreateAccount, Username error
	
	/**
	 * For: <strong>account creation</strong> form.<br>
	 * Used when: <strong>Username</strong>'s length is out of range.<br>
	 * Message: "<strong>Must be 4 - 10 characters.</strong>"
	 */
	CREATEACCOUNT_UNAME_LENGTH(ErrMsg.MESSAGE_CREATEACCOUNT_UNAME_LENGTH),
	
	/**
	 * For: <strong>account creation</strong> form.<br>
	 * Used when: <strong>Username</strong> field contains space.<br>
	 * Message: "<strong>Remove the space!</strong>"
	 */
	CREATEACCOUNT_UNAME_SPACE(ErrMsg.MESSAGE_CREATEACCOUNT_UNAME_SPACE),
	
	/**
	 * For: <strong>account creation</strong> form.<br>
	 * Used when: <strong>Username</strong> is duplicate in the database.<br>
	 * Message: "<strong>This username already exists.</strong>"
	 */
	CREATEACCOUNT_UNAME_DUPLICATE(ErrMsg.MESSAGE_CREATEACCOUNT_UNAME_DUPLICATE),

	// CreateAccount, Password error
	
	/**
	 * For: <strong>account creation</strong> form.<br>
	 * Used when: <strong>Password</strong>'s length is out of range.<br>
	 * Message: "<strong>Must be 4 - 20 characters.</strong>"
	 */
	CREATEACCOUNT_PASS_LENGTH(ErrMsg.MESSAGE_CREATEACCOUNT_PASS_LENGTH),
	
	/**
	 * For: <strong>account creation</strong> form.<br>
	 * Used when: <strong>Password</strong> field contains space.<br>
	 * Message: "<strong>There's a space in your password!</strong>"
	 */
	CREATEACCOUNT_PASS_SPACE(ErrMsg.MESSAGE_CREATEACCOUNT_PASS_SPACE),
	
	/**
	 * For: <strong>account creation</strong> form.<br>
	 * Used when: <strong>Password</strong> and <strong>Confirm password</strong> fields are not the same.<br>
	 * Message: "<strong>Passwords don't match.</strong>"
	 */
	CREATEACCOUNT_PASS_CONFIRM(ErrMsg.MESSAGE_CREATEACCOUNT_PASS_CONFIRM),
	
	
	// Feedback, title error
	// Feedback, detail error
	
	/**
	 * For: <strong>feedback/bug report</strong> form.<br>
	 * Used when: <strong>Title</strong> or <strong>Detail</strong> field is empty.<br>
	 * Message: "<strong>This field is required!</strong>"
	 */
	FEEDBACK_FIELD_EMPTY(ErrMsg.MESSAGE_FEEDBACK_FIELD_EMPTY),
	
	/**
	 * For: <strong>feedback/bug report</strong> form.<br>
	 * Used when: <strong>Title</strong> exceeds char length limit of 200.<br>
	 * Message: "<strong>Must not exceed 200 characters.</strong>"
	 */
	FEEDBACK_TITLE_LENGTH(ErrMsg.MESSAGE_FEEDBACK_TITLE_LENGTH),
	
	/**
	 * For: <strong>feedback/bug report</strong> form.<br>
	 * Used when: <strong>Detail</strong> exceeds char length limit of 4000.<br>
	 * Message: "<strong>Must not exceed 4000 characters.</strong>"
	 */
	FEEDBACK_DETAIL_LENGTH(ErrMsg.MESSAGE_FEEDBACK_DETAIL_LENGTH),
	
	/**
	 * For: <strong>feedback/bug report</strong> form.<br>
	 * Used when: <strong>Error message</strong> exceeds char length limit of 2000.<br>
	 * Message: "<strong>Must not exceed 2000 characters.</strong>"
	 */
	FEEDBACK_ERRORMESSAGE_LENGTH(ErrMsg.MESSAGE_FEEDBACK_ERRORMESSAGE_LENGTH);

	
	private final static String MESSAGE_CREATEACCOUNT_UNAME_LENGTH = "Must be 4 - 10 characters.";
	private final static String MESSAGE_CREATEACCOUNT_UNAME_SPACE = "Remove the space!";
	private final static String MESSAGE_CREATEACCOUNT_UNAME_DUPLICATE = "This username already exists.";

	private final static String MESSAGE_CREATEACCOUNT_PASS_LENGTH = "Must be 4 - 20 characters.";
	private final static String MESSAGE_CREATEACCOUNT_PASS_SPACE = "There's a space in your password!";
	private final static String MESSAGE_CREATEACCOUNT_PASS_CONFIRM = "Passwords don't match.";
	
	private final static String MESSAGE_FEEDBACK_FIELD_EMPTY = "This field is required!";
	private final static String MESSAGE_FEEDBACK_TITLE_LENGTH = "Must not exceed 200 characters.";
	private final static String MESSAGE_FEEDBACK_DETAIL_LENGTH = "Must not exceed 4000 characters.";
	private final static String MESSAGE_FEEDBACK_ERRORMESSAGE_LENGTH = "Must not exceed 2000 characters.";

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
