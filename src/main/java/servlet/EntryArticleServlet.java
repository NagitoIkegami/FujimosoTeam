package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Article;
import dao.Dao;

//SampleBBS/EntryArticleServletにアクセスされると動作
@WebServlet("/EntryArticleServlet")
public class EntryArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EntryArticleServlet() {
        super();
    }

    //POSTアクセス時に動作
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//requestのデータの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		
		//requestから，title, bodyの値を取得
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		//request.getSession()でsessionを取得し，getAttribute("userId")でuserIdという名前の値を取得.
		//ログイン時に設定した自分のuserIdが取れる．
		//ただし，そのままではObject型になってしまうため，Stringにキャストして変数に受け取る．
		String editorId = (String) request.getSession().getAttribute("userId");

		//DBアクセス用のインスタンスを作成
		Dao dao = new Dao();

		//入力データから記事オブジェクトを作成
		Article articleToEntry = new Article(title, body, editorId);
		
		//記事をDBに登録
		dao.insertArticle(articleToEntry);

//		//自身が登録した最新の記事を取り出す場合は下記を使用
//		Article article = dao.getNewestArticleByEditorId(editorId);

		//記事リストに戻る
		RequestDispatcher dispatcher = request.getRequestDispatcher("./ArticleListServlet");
		dispatcher.forward(request, response);
	}

}
