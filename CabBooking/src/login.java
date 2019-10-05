import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
@WebServlet("/checkLogin")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//PrintWriter out=response.getWriter();
		String email=request.getParameter("email2");
		String pwd=request.getParameter("password2");
		//System.out.println(email);
		//System.out.println(pwd);
		boolean flag=false;
		PrintWriter o=response.getWriter();
		try{
			Connection con;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","lurisan");
			
			PreparedStatement find= con.prepareStatement("select user_name, phno from User_List where email=? and password=?");
			find.setString(1,email);
			find.setString(2,pwd);
			//System.out.println("connection to database established");
			ResultSet rs=find.executeQuery();
			if(rs.next())
			{
				String name=rs.getString(1);
				System.out.println(name);
				flag=true;
			}
			rs.close();
			con.close();
			if(flag==true)
			{
				RequestDispatcher rd=request.getRequestDispatcher("2. booking_page.html");
				rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd=request.getRequestDispatcher("1. login_page.html");
				rd.include(request, response);
				o.println("<p align=center><font color=Red>Wrong Credentials</font></p>");
			}
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		o.close();
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
