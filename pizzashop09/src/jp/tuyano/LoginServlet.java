package jp.tuyano;
 
import java.io.IOException;
import java.security.Principal;
 
import javax.servlet.*;
import javax.servlet.http.*;
 
import com.google.appengine.api.users.*;
 
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        UserService us = UserServiceFactory.getUserService();
        User user = us.getCurrentUser();
        HttpSession session = req.getSession();
        if (us.isUserLoggedIn()){
            session.setAttribute("user", user);
            String loginurl = us.createLoginURL("/add.html");
            resp.sendRedirect(loginurl);
        } else {
            session.removeAttribute("user");
            String url = req.getRequestURI();
            String loginurl = us.createLoginURL(url);
            resp.sendRedirect(loginurl);
        }
    }
}


