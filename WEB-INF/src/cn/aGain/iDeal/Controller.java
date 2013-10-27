package cn.aGain.iDeal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
	
	private HttpServletRequest request;
	private PrintWriter out;
	
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		request = req;
		//resp.setContentType("text/html;charset=utf-8");
		out = resp.getWriter();
		
		int operation = Integer.parseInt(request.getParameter("op"));
		
		out.println("Hello, World!");
		out.close();
	}

	private static final long serialVersionUID = -2466705535775598276L;
}