package servlet;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.Dao;

//SampleBBS/EntryUserServletにアクセスすると動作
@WebServlet("/UpdateUserServletAns")
public class UpdateUserServletAns extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UpdateUserServletAns() {
        super();
    }


    //POSTアクセス時に動作．データ登録系の処理なので，POSTのみ受け付け．
    //
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//requestのデータの文字コードをUTF-8に設定
		request.setCharacterEncoding("UTF-8");
		
		//requestから，id,name,password1,password2の名前のデータを取得(entryUser.jspを参照)
		String name = request.getParameter("name");
		String currentPassword = request.getParameter("currentPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		
		//sessionを取得
		HttpSession session = request.getSession(false);
		//ログイン時にsessionに保存したuserIdを取得
		String userId = (String) session.getAttribute("userId");

		//DBアクセスのためのクラスをインスタンス化
		Dao dao = new Dao();
		//DBから自身のIdのuserを取得
		User user = dao.getUserById(userId);
		
		//更新用のユーザを作成
		User newUser = new User();
		//更新用ユーザにuserIdをセット
		newUser.setId(userId);

		//nameが問題ないかチェック
		if( name!=null && !name.isBlank() ) {//nameがnull, 空, もしくは空白のみではない
			newUser.setName( name );//新しいnameを更新用userに設定
		}else {
			newUser.setName( user.getName() );//現在のnameを更新用userに設定
		}
		
		//パスワードが問題ないかチェック
		if(Objects.equals(currentPassword, user.getPassword())//現在のパスワードが正しい
				&& newPassword1!=null && !newPassword1.isBlank() //新しいパスワード1がnullでなく、かつ空文字でない
				&& Objects.equals(newPassword1, newPassword2)//かつ、新しいパスワード1,2が一致
				) {
			newUser.setPassword(newPassword1); //newUserに新しいパスワード1をセット
		}
		else {
			newUser.setPassword(user.getPassword());//newUserに現在のパスワード1をセット
		}
		
		//newUserでDBを更新
		dao.updateUser(newUser);
		
		//更新後のユーザを取得
		user = dao.getUserById(userId);

		//requestにユーザをセット
		request.setAttribute("user", user);

		//ユーザ更新ページに戻す．
		RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/updateUserAns.jsp");
		dispatcher.forward(request, response);

	}

}
