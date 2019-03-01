package localhost.products.web.servlets;

import localhost.products.web.beans.UserAccount;
import localhost.products.web.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userInfo"})
public class UserInfoServlet extends HttpServlet {

    public UserInfoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Проверить, вошел ли пользователь в систему или нет.
        UserAccount loginedUser = MyUtils.getLoginedUser(session);

        // Если еще не вошел в систему (login).
        if (loginedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Сохранить информацию в request attribute перед тем как forward (перенаправить).
        req.setAttribute("user", loginedUser);

        // Если пользователь уже вошел в систему (login), то forward (перенаправить) к странице
        // /WEB-INF/views/userInfoView.jsp
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/userInfoView.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
