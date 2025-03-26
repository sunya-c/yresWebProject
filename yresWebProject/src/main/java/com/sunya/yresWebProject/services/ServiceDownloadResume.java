package com.sunya.yresWebProject.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceDownloadResume
{
	@Autowired
	private ResourceLoader loader;


	/**
	 * @return <strong>ResponseEntity&lt;Resource&gt;</strong> which contains the
	 *         RESUME file.
	 * @throws IOException
	 */
	public ResponseEntity<Resource> sDownloadResume() throws IOException
	{
		try
		{
			// Path to the file to be downloaded
			Resource resource = loader.getResource("/resources/outBox/resumeC_pdf.pdf");

			// Set headers for the response
			if (resource.exists())
			{
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
											"attachment; filename=\"ResumeC.pdf\"").body(resource);
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
}
