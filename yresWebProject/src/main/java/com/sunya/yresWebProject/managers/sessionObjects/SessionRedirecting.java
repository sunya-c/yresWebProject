package com.sunya.yresWebProject.managers.sessionObjects;

import java.util.ArrayList;
import java.util.Random;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.managers.SessionManager;

/**
 * This class is for storing session-specific values related to
 * <strong>Redirecting</strong>.<br>
 * <br>
 * The object of this class is meant to be session-specific, which means one
 * object per one session.
 */
public class SessionRedirecting
{
	private ArrayList<String> codes = new ArrayList<>();


	/**
	 * Consume a code and return <strong>true</strong> if the code is valid. Once
	 * the code is consumed, the code is removed from the session, and as a result,
	 * the code cannot be consumed again. This is meant to be used as a one-time key
	 * to access <strong>Redirecting page</strong>, preventing users from manually
	 * entering the URL without code or with invalid code.<br>
	 * <br>
	 * For example, if a user requests for <strong>Redirecting page</strong> without
	 * the code or with invalid code, you can then write a logic to redirect them to
	 * any other pages.
	 * 
	 * @param code ~ The code to be consumed.
	 * @return <strong>true</strong> ~ if the code is valid.<br>
	 *         <strong>false</strong> ~ if otherwise.
	 */
	public boolean consumeCode(String code)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		synchronized (sm.getKeyHolder().getKeyRedirecting())
		{
			return codes.remove(code);
		}
	}


	/**
	 * Return a 10-digit code. The code is used as a one-time key to access
	 * <strong>Redirecting page</strong>. The code is meant to be appended to the
	 * end of <strong>Redirecting page URL</strong>. The code can be validated in
	 * <strong>Redirecting page Controller</strong> by calling
	 * {@code SessionRedirecting.consumeCode('code')} method. This prevent users
	 * from entering <strong>Redirecting page</strong> by manually entering the URL.
	 * 
	 * @return <strong>String of code</strong> ~ This code is used as a one-time key
	 *         to get access to the <strong>Redirecting page</strong>.
	 */
	public String generateCode()
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		Random random = new Random();

		String code;

		synchronized (sm.getKeyHolder().getKeyRedirecting())
		{
			do
			{
				code = "";

				code += random.nextInt(1, 10);
				for (var i = 0; i<9; i++)
				{
					code += random.nextInt(0, 10);
				}
			}
			while (codes.contains(code));

			codes.add(code);
		}

		return code;
	}
}
