package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//SampleBBS/EntryUserPageServletにアクセスすると動作
@WebServlet("/EntryUserPageServlet")
public class EntryUserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//コンストラクタ(省略可能)
    public EntryUserPageServlet() {
        super();
    }

    //GETアクセス時に動作
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//entryUser.jspを開く
		RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/entryUser.jsp");
		dispatcher.forward(request, response);
	}
	//POSTアクセス時はdoGetに丸投げ
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
