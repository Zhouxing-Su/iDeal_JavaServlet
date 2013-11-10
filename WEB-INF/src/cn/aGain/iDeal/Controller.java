package cn.aGain.iDeal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Controller extends HttpServlet {

	private PrintWriter out;
	private Scanner in;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
		in = new Scanner(req.getInputStream());
		resp.setCharacterEncoding("UTF-8");
		out = resp.getWriter();

//		printRawRequest(req);

		String operation = in.nextLine();
		if (operation.equals("LOGIN")) {
			login();
		} else if (operation.equals("REGISTER")) {
			register();
		} else if (operation.equals("REPORT")) {
			report();
		}

		out.close();
	}

	private String getParamValue(String key) {
		String valueString = new String();
		String line;
		if (in.nextLine().equals(key + ":")) {
			while (in.hasNext() && !(line = in.nextLine()).isEmpty()) {
				try {
					valueString += new String(Base64.decode(line), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					System.err.println("Unsupported Encoding !");
				}
			}
		}
		return valueString;
	}

	private void login() {
		String name = getParamValue("name");
		String password = getParamValue("password");

		System.out.println("name:" + name);
		System.out.println("password:" + password);

		// DatabaseManager dm = DatabaseManager.create();
		// if (dm == null) {
		// return;
		// }
		//
		// try {
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// dm.close();

		// out.print("E"); // Error
		out.print("µÇÂ½³É¹¦"); // user ID
	}

	private void register() {
		String name = getParamValue("name");
		String password = getParamValue("password");
		String email = getParamValue("email");

		System.out.println("name:" + name);
		System.out.println("password:" + password);
		System.out.println("email:" + email);

		// DatabaseManager dm = DatabaseManager.create();
		// if (dm == null) {
		// return;
		// }
		//
		// try {
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// dm.close();
		out.print("S"); // Success
		// out.print("ND"); // Name Duplicate
		// out.print("EN"); // Email Duplicate
	}

	private void report() {
//		String subject = getParamValue("subject");
		String content = getParamValue("content");

//		System.out.println("subject:" + subject);
		System.out.println("content:" + content);
	}

	/**
	 * Only be used for debugging.
	 */
	private void printRawRequest(HttpServletRequest req) {
		Enumeration<String> es = req.getHeaderNames();
		String header;
		while (es.hasMoreElements()) {
			header = es.nextElement();
			if (header != null) {
				System.out.println(header + ":" + req.getHeader(header));
			}
		}

		System.out.println("------------------------------");

		while (in.hasNextLine()) {
			System.out.println(in.nextLine());
		}
		in.close();
	}

	private static final long serialVersionUID = -2466705535775598276L;
}