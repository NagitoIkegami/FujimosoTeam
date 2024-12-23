package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.Dao;

/**
 * Servlet implementation class UpdateUserPageServlet
 */
@WebServlet("/UpdateUserPageServletAns")
public class UpdateUserPageServletAns extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserPageServletAns() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionを取得
		HttpSession session = request.getSession(false);
		//ログイン時にsessionに保存したuserIdを取得
		String userId = (String) session.getAttribute("userId");
		
		//Daoオブジェクトを作成
		Dao dao = new Dao();
		//DBから自身のIdのuserを取得
		User user = dao.getUserById(userId);
		//requestにuserをセット
		request.setAttribute("user", user);
			
		//下記2行は決まり文句．./WEB-INF/jsp/updateUser.jspのページを表示する．
		RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/updateUserAns.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
