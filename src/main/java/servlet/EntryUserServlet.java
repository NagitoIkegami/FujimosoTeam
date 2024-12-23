package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.Dao;

//SampleBBS/EntryUserServletにアクセスすると動作
@WebServlet("/EntryUserServlet")
public class EntryUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public EntryUserServlet() {
        super();
    }


    //POSTアクセス時に動作．データ登録系の処理なので，POSTのみ受け付け．
    //
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//requestのデータの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		
		//requestから，id,name,password1,password2の名前のデータを取得(entryUser.jspを参照)
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		//DBアクセスのためのクラスをインスタンス化
		Dao dao = new Dao();

		//ユーザ情報が問題ないかチェック
		if(		!id.isBlank() //idが空もしくは空白のみではない
				&& dao.getUserById(id)==null //idが使用されていない
				&& password1.equals(password2) //確認用パスワードが一致
				&& !password1.isBlank() //passwordが空もしくは空白のみではない
				) {
			
			//登録用のユーザオブジェクトを作成．Userはbeansパッケージに定義してある．
			User userToEntry = new User(id, password1, name);
			//ユーザをDBに登録
			dao.insertUser(userToEntry);
			//ログインページ./WEB-INF/jsp/login.jspに移動
			RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
		else {//ユーザ情報に問題があった場合
			//ユーザ登録ページに戻す．
			RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/entryUser.jsp");
			dispatcher.forward(request, response);
		}


	}

}
