package com.sunya.yresWebProject.managers.sessionObjects;

import java.util.ArrayList;
import java.util.Random;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataFeedback;

/**
 * This class is for storing session-specific values related to
 * <strong>Feedback</strong>.<br>
 * <br>
 * The object of this class is meant to be session-specific, which means one
 * object per one session.
 */
public class SessionFeedback
{
	private ArrayList<DataObject> dataObjects = new ArrayList<>();


	/**
	 * Consume a code and return the DataFeedback object in the session bound to
	 * that code. Once the code is consumed, the code and the object are removed
	 * from the session, and as a result, the code cannot be consumed again. So,
	 * <strong>make sure you create a reference to that object when consuming, or
	 * you lose it forever</strong>.
	 * 
	 * @param code ~ The code to be consumed.
	 * @return <strong>DataFeedback object</strong> bound to the consumed code ~ if
	 *         the code exists.<br>
	 *         <strong>null</strong> ~ if otherwise.
	 */
	public DataFeedback consumeCode(String code)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		DataObject dataObject = null;

		synchronized (sm.getKeyHolder().getKeyFeedback())
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

				return dataObject.getDataFeedback();
			}
			else
				return null;
		}
	}


	/**
	 * Save a DataFeedback object to the session and return a 10-digit code. The
	 * code is bound to the given DataFeedback object and used as a one-time key to
	 * get the DataFeedback object back. The code can be used by calling
	 * {@code SessionFeedback.consumeCode('code')} method. Once the code is
	 * consumed, it returns back your object, and that particular code is destroyed
	 * and cannot be consumed again.
	 * 
	 * @param dataFeedback ~ This object will be saved to the session.
	 * @return <strong>String of code</strong> ~ This code is used as a one-time key
	 *         to get the saved object back.
	 */
	public String generateCode(DataFeedback dataFeedback)
	{
		SessionManager sm = YresWebProjectApplication.context.getBean(SessionManager.class);

		Random random = new Random();

		DataObject dataObject = new DataObject();

		String code;

		synchronized (sm.getKeyHolder().getKeyFeedback())
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
			dataObject.setDataFeedback(dataFeedback);

			dataObjects.add(dataObject);
		}

		return code;
	}


	/**
	 * This class is for local usage within {@code SessionFeedback}. It holds 2
	 * private fields with Getters and Setters, including:<br>
	 * <br>
	 * 1. String code.<br>
	 * <br>
	 * 2. DataFeedback dataFeedback.
	 */
	class DataObject
	{
		private String code = null;
		private DataFeedback dataFeedback = null;


		public String getCode()
		{
			return code;
		}


		public void setCode(String code)
		{
			this.code = code;
		}


		public DataFeedback getDataFeedback()
		{
			return dataFeedback;
		}


		public void setDataFeedback(DataFeedback dataFeedback)
		{
			this.dataFeedback = dataFeedback;
		}
	}
}
