package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//EntryArticlePageServletにアクセスされた場合に動作
@WebServlet("/Serch")
public class Search extends HttpServlet {
 private static final long serialVersionUID = 1L;
       
 //コンストラクタ（省略可能）
    public Search() {
        super();
    }

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //./WEB-INF/jsp/entryArticle.jspを表示
  RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/Search.jsp");
  dispatcher.forward(request, response);
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //doGetに丸投げ
  doGet(request, response);
 }

}