<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
<!-- スタイルシートの読み込み （src/main/webapp/css/style.css を参照）-->
<link rel="stylesheet" type="text/css" href="./css/style.css" media="all" />
</head>
<body>
<!--  LoginServletにデータを送信するformを宣言する. 登録処理なので，methodはpostを指定．
	後はentryArticle.jspと同様なので，そちらのコメントを参考にするとよい．-->
	<div id="form">
	<p class="form-title" >Tiny BBS ログインページ</p>
	<form action="./LoginServlet" method="post">
		<p class="mail"><label>ログインID：<input type="text" name="id" size="30" maxlength="20"></label></p>
		<p class="pass"><label>パスワード：<input type="password" name="password" size="20" maxlength="20"></label></p>
		<p class="submit"><input type="submit" value="ログイン"></p>
	</form>
	<a href=./EntryUserPageServlet>ユーザ登録</a>
	</div>
		<!-- EntryUserPageServletにハイパーリンク．
	ハイパーリンクによるアクセスは，GETアクセスとなり，サーブレットのdoGet()が呼ばれる． -->
	
	<br>
</body>
</html>