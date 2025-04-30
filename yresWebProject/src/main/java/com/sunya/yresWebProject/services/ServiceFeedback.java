package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.daos.DaoFeedback;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataFeedback;
import com.sunya.yresWebProject.models.FormFeedback;
import com.sunya.yresWebProject.models.ModelFeedback;
import com.sunya.yresWebProject.restrictions.RestrictionsFeedback;

@Service
public class ServiceFeedback
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoFeedback dao;
	@Autowired
	private RestrictionsFeedback restriction;


	/**
	 * What this method does:<br>
	 * <br>
	 * 1. Validate the data from <strong>Feedback form</strong>.<br>
	 * <br>
	 * 2. If the data is valid, add the data to <strong>feedbackinfo</strong>
	 * table.<br>
	 * <br>
	 * 3. If data is added successfully, generate a code for accessing
	 * <strong>Redirecting page</strong>. This code will be appended to the end of
	 * the URL.
	 * 
	 * @param formFb
	 * @param dataFeedback
	 * @param codeFeedback
	 * @return <strong>String of Redirecting page URL</strong> ~ if data is
	 *         added successfully.<br>
	 *         <strong>String of Feedback page URL</strong> ~ if
	 *         otherwise.
	 */
	public String sFeedback(FormFeedback formFb, DataFeedback dataFeedback, String codeFeedback)
	{
		dataFeedback.setTitlePreTyped(formFb.getFeedbackTitle());
		dataFeedback.setDetailPreTyped(formFb.getFeedbackDetail());

		if (restriction.checkRestriction(formFb, dataFeedback))
		{
			String username;
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				username = sm.getSessionLogin().getUsernameUnescaped();
			}

			ModelFeedback model = new ModelFeedback();
			model.setUsername(username);
			model.setFeedbackTitile(formFb.getFeedbackTitle());
			model.setFeedbackDetail(formFb.getFeedbackDetail());
			model.setFeedbackErrorMessage(formFb.getFeedbackErrorMessage());

			try
			{
				String refNumber = dao.addFeedback(model);
				dataFeedback.setRefNumber(refNumber);
			}
			catch (SomethingWentWrongException e)
			{
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("")
																	.path(Url.feedback)
																	.queryParam("code", codeFeedback);
				if (!formFb.getFeedbackErrorMessage().isBlank())
					builder.queryParam(SessionManager.FEEDBACK_ERRORMESSAGE_PRETYPED, formFb.getFeedbackErrorMessage());
				
				String feedbackUrl = builder.encode().build().toUriString();
				return feedbackUrl;
			}

			String codeRedirecting = sm.getSessionRedirecting().generateCode();
			
			String redirectingUrl = UriComponentsBuilder.fromUriString("")
														.path(Url.redirecting)
														.queryParam("message", "Thank you for reaching out!")
														.queryParam("destinationPage", "Feedback summary page")
														.queryParam("destinationUrl", "/feedback/summary?codeSummary="+codeFeedback)
														.queryParam("code", codeRedirecting)
														.encode().build().toUriString();
			return redirectingUrl;
		}
		else
		{
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("")
																.path(Url.feedback)
																.queryParam("code", codeFeedback);
			if (!formFb.getFeedbackErrorMessage().isBlank())
				builder.queryParam(SessionManager.FEEDBACK_ERRORMESSAGE_PRETYPED, formFb.getFeedbackErrorMessage());
			
			String feedbackUrl = builder.encode().build().toUriString();
			return feedbackUrl;
		}
	}


	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}
