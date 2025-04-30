package com.sunya.yresWebProject.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunya.yresWebProject.PrintError;
import com.sunya.yresWebProject.daos.DaoLoginInfo;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.models.ModelLoginInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterAdmin extends OncePerRequestFilter
{
	DaoLoginInfo dao;

	public FilterAdmin(DaoLoginInfo dao)
	{
		this.dao = dao;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException
	{
		System.out.println("Order: 1, in Filter Admin (/saveBotstodatabase)");
		
		ModelLoginInfo model = new ModelLoginInfo();
		model.setUsername("adminCode");
		model.setPassword(request.getHeader("adminCode"));
		if (dao.doesExistPasswordCaseSen(model))
		{
			System.out.println("filter Admin passed");
			filterChain.doFilter(request, response);
			return;
		}
		
		System.out.println("filter Admin failed");
		PrintError.toErrorPage(response, new SomethingWentWrongException("You don't have permission access this page"));
		return;
	}

}
