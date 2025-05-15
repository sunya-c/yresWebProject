package com.sunya.yresWebProject.restrictions;

import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.models.DataAccountInfo;
import com.sunya.yresWebProject.models.FormChangePassword;

@Component
public class RestrictionsChangePassword extends RestrictionsPassword
{
	public boolean checkRestriction(FormChangePassword formCP, DataAccountInfo dataAccountInfo)
	{
		boolean validInfo = true;
		
		if (checkLengthPassword(formCP))
		{
			if (!checkSpacePassword(formCP))
			{
				dataAccountInfo.setPassword1Err(ErrMsg.CREATEACCOUNT_PASS_SPACE.toString()); // pass error message
																								// here.
				validInfo = false;
			}
		}
		else
		{
			dataAccountInfo.setPassword1Err(ErrMsg.CREATEACCOUNT_PASS_LENGTH.toString()); // pass error message enum
																							// here.
			validInfo = false;
		}

		// confirm password check
		if (!confirmPassword(formCP))
		{
			dataAccountInfo.setPassword2Err(ErrMsg.CREATEACCOUNT_PASS_CONFIRM.toString());// pass error message enum
																							// here.
			validInfo = false;
		}
		
		return validInfo;
	}
}
