package localhost.products.web.servlets;

import localhost.products.web.beans.UserAccount;
import localhost.products.web.utils.DBUtils;
import localhost.products.web.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    public LoginServlet() {
        super();
    }

    // Показать страницу Login.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward (перенаправить) к странице /WEB-INF/pages/loginView.jsp
        // (Пользователь не может прямо получить доступ
        // к страницам JSP расположенные в папке WEB-INF).
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/loginView.jsp");
        dispatcher.forward(req, resp);
    }

    // Когда пользователь вводит userName & password & email, и нажимает Submit.
    // Этот метод будет выполнен.
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String rememberMeStr = req.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);

        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;

        if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password";
        } else {
            Connection conn = MyUtils.getStoredConnection(req);
            try {
                // Найти user в DB.
                user = DBUtils.findUser(conn, userName, password);

                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        // В случае, если есть ошибка,
        // forward (перенаправить) к /WEB-INF/pages/login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);

            // Сохранить информацию в request attribute перед forward.
            req.setAttribute("errorString", errorString);
            req.setAttribute("user", user);

            // Forward (перенаправить) к странице /WEB-INF/pages/login.jsp
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/loginView.jsp");
            dispatcher.forward(req, resp);

            // В случае, если нет ошибки.
            // Сохранить информацию пользователя в Session.
            // И перенаправить к странице userInfo.
        } else {
            HttpSession session = req.getSession();
            MyUtils.storeLoginedUser(session, user);

            // Если пользователь выбирает функцию "Remember me".
            if  (remember){
                MyUtils.storeUserCookie(resp, user);

            } else { // Наоборот, удалить Cookie
                MyUtils.deleteUserCookie(resp);
            }
            // Redirect на страницу /userInfo.
            resp.sendRedirect(req.getContextPath() + "/userInfo");
        }
    }
}