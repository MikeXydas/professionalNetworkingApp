package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

@WebServlet("/*")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserDAO dao = new UserDAOImpl();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("HEREEEEEEEEEEEEEEEEEEEEEEEEEEE");

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String photoUrl = "Not implemented yet";
		String phoneNumber = request.getParameter("phoneNumber");
		int isModerator = 0;
		
		int userExists =  dao.userExists(email);
		
		if((firstName.isEmpty()) || (lastName.isEmpty()) || (email.isEmpty()) || (password.isEmpty()) || (photoUrl.isEmpty()) || (phoneNumber.isEmpty()))
			response.getWriter().println("Fill all the inputs");
		if(userExists == 1) 
			response.getWriter().println("User exists");
		else {
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			user.setPhoneNumber(phoneNumber);
			user.setPhotoUrl(photoUrl);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setIsModerator(isModerator);
			
			dao.create(user);
			
			response.getWriter().println("User created");
		}
		
	}
}
