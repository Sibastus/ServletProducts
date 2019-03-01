package localhost.products.web.servlets;

import localhost.products.web.beans.Book;
import localhost.products.web.utils.DBUtils;
import localhost.products.web.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/productList"})
public class ProductListServlet extends HttpServlet {

    public ProductListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorString = null;
        List<Book> list = null;
        Connection conn = MyUtils.getStoredConnection(req);

        try {
            list = DBUtils.queryProduct(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }



        // Сохранить информацию в request attribute перед тем как перейти к views.
        req.setAttribute("errorString", errorString);
        req.setAttribute("productList", list);

        // Forward к /WEB-INF/pages/productView.jsp
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/pages/productView.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
