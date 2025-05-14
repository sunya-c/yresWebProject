package com.sunya.yresWebProject.managers.sessionObjects;

import java.util.ArrayList;
import java.util.Random;

import org.jetbrains.annotations.NotNull;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataAdminPanel;
import com.sunya.yresWebProject.models.DataCreateAccount;

/**
 * This class is for storing session-specific values related to
 * <strong>CreateAccount</strong>.<br>
 * <br>
 * The object of this class is meant to be session-specific, which means one
 * object per one session.
 */
public class SessionAdminPanel
{
	private ArrayList<DataObject> dataObjects = new ArrayList<>();


	/**
	 * Consume a code and return the DataCreateAccount object in the session bound
	 * to that code. Once the code is consumed, the code and the object are removed
	 * from the session, and as a result, the code cannot be consumed again. So,
	 * <strong>make sure you create a reference to that object when consuming, or
	 * you lose it forever</strong>.
	 * 
	 * @param code ~ The code to be consumed
	 * @return <strong>DataCreateAccount object</strong> bound to the consumed code
	 *         ~ if the code exists.<br>
	 *         <strong>null</strong> ~ if otherwise.
	 */
	public DataAdminPanel consumeCode(@NotNull String code)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		DataObject dataObject = null;

		synchronized (sm.getKeyHolder().getKeyAdminPanel())
		{
			for (var i : dataObjects)
			{
				if (code.equals(i.getCode()))
				{
					dataObject = i;
					break;
				}
			}

			if (dataObject!=null)
			{
				System.out.println("before removal: "+dataObjects);
				dataObjects.remove(dataObject);
				System.out.println("after removal: "+dataObjects);

				return dataObject.getDataAdminPanel();
			}
			else
				return null;
		}
	}


	/**
	 * Save a DataCreateAccount object to the session and return a 10-digit code.
	 * The code is bound to the given DataCreateAccount object and used as a
	 * one-time key to get the DataCreateAccount object back. The code can be used
	 * by calling {@code SessionCreateAccount.consumeCode('code')} method. Once the
	 * code is consumed, it returns back your object, and that particular code is
	 * destroyed and cannot be consumed again.
	 * 
	 * @param dataAdminPanel ~ This object will be saved to the session.
	 * @return <strong>String of code</strong> ~ This code is used as a one-time key
	 *         to get the saved object back.
	 */
	public String generateCode(DataAdminPanel dataAdminPanel)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		Random random = new Random();

		DataObject dataObject = new DataObject();

		String code;

		synchronized (sm.getKeyHolder().getKeyAdminPanel())
		{
			boolean loopAgain;
			do
			{
				code = "";

				code += random.nextInt(1, 10);
				for (var i = 0; i<9; i++)
				{
					code += random.nextInt(0, 10);
				}

				loopAgain = false;
				for (var i : dataObjects)
				{
					if (code.equals(i.getCode()))
						loopAgain = true;
				}
			}
			while (loopAgain);

			dataObject.setCode(code);
			dataObject.setDataAdminPanel(dataAdminPanel);

			dataObjects.add(dataObject);
		}

		return code;
	}


	/**
	 * This class is for local usage within {@code SessionCreateAccount}. It holds 2
	 * private fields with Getters and Setters, including:<br>
	 * <br>
	 * 1. String code.<br>
	 * <br>
	 * 2. DataCreateAccount dataCreateAccount.
	 */
	class DataObject
	{
		private String code = null;
		private DataAdminPanel dataAdminPanel = null;


		public String getCode()
		{
			return code;
		}


		public void setCode(String code)
		{
			this.code = code;
		}


		public DataAdminPanel getDataAdminPanel()
		{
			return dataAdminPanel;
		}


		public void setDataAdminPanel(DataAdminPanel dataCreateAccount)
		{
			this.dataAdminPanel = dataCreateAccount;
		}
	}
}
