package com.sunya.yresWebProject.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.daos.DaoDownloadinfo;
import com.sunya.yresWebProject.daos.DaoWebdatainfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.ModelDownloadinfo;
import com.sunya.yresWebProject.models.ModelWebdatainfo;

@Service
public class ServiceDownloadResume
{
	@Autowired
	private DaoWebdatainfo daoWebinfo;
	@Autowired
	private DaoDownloadinfo daoDownload;
	@Autowired
	private Environment env;


	/**
	 * @return <strong>ResponseEntity&lt;Resource&gt;</strong> which contains the
	 *         RESUME file.
	 * @throws IOException
	 */
	public ResponseEntity<Resource> sDownloadResume()
	{
		try
		{
			Resource resource = getResource(env.getProperty("yres.resume", "Env. error!"));
			
			// Set headers and body for the response
			if (resource.exists())
			{
				ModelWebdatainfo model = daoWebinfo.getWebinfo(DaoWebdatainfo.WEB_RESUME_DATE);
				
				long contentLength = resource.contentLength();
				
				return ResponseEntity.ok()
										.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ResumeC_"+model.getValue()+".pdf\"")
										.header(HttpHeaders.CONTENT_TYPE, "application/pdf")
										.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
										.body(resource);
			}
			else
			{
				return ResponseEntity.notFound().build();
			}
		}
		catch (Exception e)
		{
			System.err.println(e);
			return ResponseEntity.internalServerError().build();
		}
	}
	
	private Resource getResource(String nameInDatabase) throws IOException, SomethingWentWrongException
	{
		ModelDownloadinfo model = daoDownload.getFile(nameInDatabase);
		try (BufferedInputStream inStream = new BufferedInputStream(model.getFiledata()))
		{
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			byte[] temp = new byte[4096];
			int length = 0;
			while ((length = inStream.read(temp))!=-1)
			{
				byteArray.write(temp, 0, length);
			}
			return new ByteArrayResource(byteArray.toByteArray());
		}
		catch (IOException e)
		{
			throw e;
		}
	}
}
