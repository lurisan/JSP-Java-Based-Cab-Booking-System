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
 * Servlet implementation class signup
 */
@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter o=response.getWriter();
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String phone=request.getParameter("phoneno");
		String pwd=request.getParameter("password2");
		boolean flag=false;
		boolean flage=false;
		boolean flagp=false;
		try{
			Connection con;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","lurisan");
			
			PreparedStatement insert= con.prepareStatement("insert into User_List values(?, ?, ?, ?)");
			PreparedStatement email_verify= con.prepareStatement("select user_name from User_List where email=?");
			PreparedStatement phone_verify= con.prepareStatement("select user_name from User_List where phno=?");
			email_verify.setString(1,email);
			phone_verify.setString(1,phone);
			ResultSet rs_email=email_verify.executeQuery();
			ResultSet rs_phone=phone_verify.executeQuery();
			if(rs_email.next())
			{	
				flag=false;
				flage=true;
			}
			if(rs_phone.next())
			{
				flag=false;
				flagp=true;
			}	
			else
			{
				insert.setString(1,email);
				insert.setString(2,pwd);
				insert.setString(3,phone);
				insert.setString(4,name);
				//System.out.println("connection to database established");
				int nor=insert.executeUpdate();
				if(nor>0)
					flag=true; 
			}
			con.close();
			if(flag==true)
			{
				RequestDispatcher rd=request.getRequestDispatcher("2. booking_page.html");
				rd.forward(request, response);
				o.println("<p align=center><font color=Red><h2>New User Added</h2></font></p>");
			}
			else
			{
				RequestDispatcher rd=request.getRequestDispatcher("4. sign_up.html");
				rd.include(request, response);
				if(flage)
					o.println("<p align=center><font color=Red><h2>Email already exists, try another one</h2></font></p>");
				else if(flagp)
					o.println("<p align=center><font color=Red><h2>Phone no already exists, try another one</h2></font></p>");
				else
					o.print("<p align=center><font color=Red><h2>Error, in adding new user, try again</h2></font></p>");
			}
		}
		catch(Exception e)
		{
			o.println("<p align=center><font color=Red><h2>Error, in adding new user, try again</h2></font></p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
