package com.sunya.yresWebProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sunya.yresWebProject.Page;
import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.models.DataCreateAccount;
import com.sunya.yresWebProject.models.FormCreateAccount;
import com.sunya.yresWebProject.services.ServiceCreateAccount;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControllerCreateAccount extends Controller1
{
	/**
	 * The Controller for URL pattern 'createAccount'.
	 * 
	 * @param md
	 * @param request
	 * @return Request dispatcher to <strong>CreateAccountPage.jsp</strong>.
	 */
	@GetMapping("/createAccount")
	public String createAccountPageGet(Model md, HttpServletRequest request)
	{
		try
		{
			String code = request.getParameter("code"); // The reference code for retrieving data from the session.

			if (code!=null)
			{
				// The data stored in the session is DataCreateAccount type.
				DataCreateAccount dataCreateAccount = sm.getSessionCreateAccount().consumeCode(code); 

				if (dataCreateAccount!=null)
					md.addAttribute(dataCreateAccount); // It's used for viewing purpose.
				else
					return redirect + Url.createAccount;
			}

			return Page.createAccount;
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}


	@Autowired
	private ServiceCreateAccount sca;


	/**
	 * The Controller for URL pattern 'sCreateAccount'. This is for 'Create Account'
	 * button in the CreateAccount page.
	 * 
	 * @param formCA
	 * @return Redirect to <strong>Redirecting page</strong> if succeeded.<br>
	 *         Redirect to <strong>CreateAccount page</strong> if the input detail
	 *         is not satisfied.<br>
	 *         Redirect to <strong>Error page</strong> if an Exception is thrown.
	 */
	@PostMapping("/sCreateAccount")
	public String sCreateAccount(FormCreateAccount formCA)
	{
		try
		{
			DataCreateAccount dataCreateAccount = new DataCreateAccount();

			String codeCreateAccount = sm.getSessionCreateAccount().generateCode(dataCreateAccount);

			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				return redirect + sca.sCreateAccount(formCA, dataCreateAccount, codeCreateAccount);
			}
		}
		catch (Exception e)
		{
			return redirect + PrintError.toErrorPage(e);
		}
	}

}
