package com.sunya.yresWebProject.restrictions;

import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.models.DataFeedback;
import com.sunya.yresWebProject.models.FormFeedback;

@Component
public class RestrictionsFeedback
{
	/**
	 * Check whether the given feedback information comply with the restriction.
	 * 
	 * @param formFb
	 * @param dataFeedback
	 * @return <strong>true</strong> ~ if the given feedback information comply with
	 *         ALL the restriction.<br>
	 *         <strong>false</strong> ~ if at least one of them doesn't meet the
	 *         restriction. The err message will be set to {@code DataFeedback}
	 *         object.
	 */
	public boolean checkRestriction(FormFeedback formFb, DataFeedback dataFeedback)
	{
		boolean validInfo = true;

		if (!formFb.getFeedbackTitle().isBlank())
		{
			if (!(formFb.getFeedbackTitle().length()<=200))
			{
				dataFeedback.setTitleErr(ErrMsg.FEEDBACK_TITLE_LENGTH.toString());
				validInfo = false;
			}
		}
		else
		{
			dataFeedback.setTitleErr(ErrMsg.FEEDBACK_FIELD_EMPTY.toString());
			validInfo = false;
		}

		if (!formFb.getFeedbackDetail().isBlank())
		{
			if (!(formFb.getFeedbackDetail().length()<=4000))
			{
				dataFeedback.setDetailErr(ErrMsg.FEEDBACK_DETAIL_LENGTH.toString());
				validInfo = false;
			}
		}
		else
		{
			dataFeedback.setDetailErr(ErrMsg.FEEDBACK_FIELD_EMPTY.toString());
			validInfo = false;
		}

		if (!(formFb.getFeedbackErrorMessage().length()<=2000))
		{
			dataFeedback.setErrorMessageErr(ErrMsg.FEEDBACK_ERRORMESSAGE_LENGTH.toString());
			validInfo = false;
		}

		return validInfo;
	}
}
