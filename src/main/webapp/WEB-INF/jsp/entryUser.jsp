<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>
<label>Tiny BBS ユーザ登録</label>
	<!--  EntryUserServletにデータを送信するformを宣言する. 登録処理なので，methodはpostを指定．
	後はentryArticle.jspと同様なので，そちらのコメントを参考にするとよい．-->
	
	<form action="./EntryUserServlet" method="post">
		<p><label>ログインID：<input type="text" name="id" size="40" maxlength="20"></label></p>
		<p><label>名前：<input type="text" name="name" size="40" maxlength="20"></label></p>
		<!-- typeをパスワードにすると，文字が目隠しされる．他にも，数値や日付，メールアドレスなどを入力するtypeが指定できるので，調べてみるとよい．-->
		<p><label>パスワード：<input type="password" name="password1" size="40" maxlength="20"></label></p>
		<p><label>確認用パスワード：<input type="password" name="password2" size="40" maxlength="20"></label></p>
		<p><input type="submit" value="登録"></p>
	</form>
</body>
</html>