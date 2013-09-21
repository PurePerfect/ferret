package com.pureperfect.ferret;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pureperfect.ferret.vfs.PathElement;

public class InfoServlet extends HttpServlet
{
	private static final long serialVersionUID = 4612276238009605146L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		final PrintWriter out = response.getWriter();

		long startTime = System.currentTimeMillis();
		
		out.println("Checking WEB-INF/classes...");
		out.println();

		WebScanner scanner = new WebScanner();

		scanner.add(this.getServletContext(), "/WEB-INF/classes");

		ScanFilter allowAll = new ScanFilter()
		{
			@Override
			public boolean accept(PathElement resource)
			{
				return true;
			}
		};

		Set<PathElement> classes = scanner.scan(allowAll);

		for (PathElement found : classes)
		{
			out.println(found.getClass().getName() + " " + found);
		}

		out.println();
		out.println("WEB-INF/lib search list:");
		out.println();

		scanner = new WebScanner();

		scanner.add(this.getServletContext(), "/WEB-INF/lib");

		for(PathElement search : scanner.getScanPath())
		{
			out.println(search.getClass().getName() +" "+ search);
		}
		
		out.println();
		out.println("WEB-INF/lib result list:");
		out.println();
		
		Set<PathElement> lib = scanner.scan(allowAll);

		for (PathElement path : lib)
		{
			out.println(path.getClass().getName() + path);
		}

		scanner = new WebScanner();

		scanner.addPrivateResources(this.getServletContext());

		out.println();
		out.println("Scanning WEB-INF/ private...");
		out.println();

		Set<PathElement> priv = scanner.scan(allowAll);

		for (PathElement found : priv)
		{
			out.println(found.getClass().getName() + " " + found);
		}

		scanner = new WebScanner();

		scanner.addPublicResources(this.getServletContext());

		out.println();
		out.println("Scanning public...");
		out.println();

		Set<PathElement> publik = scanner.scan(allowAll);

		for (PathElement found : publik)
		{
			out.println(found.getClass().getName() + " " + found);
		}

		out.println();
		out.println("Finished. Elapsed time: " + (System.currentTimeMillis() - startTime));
	}
}