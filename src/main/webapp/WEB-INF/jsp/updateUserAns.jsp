<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- beansのクラスが使えるようにインポート -->
<%@ page import ="beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>
<label>Sample BBS ユーザ情報アップデート</label>
	<%//requestからuserを取得 %>
	<% User user = (User)request.getAttribute("user"); %>

	<!--  EntryUserServletにデータを送信するformを宣言する. 登録処理なので，methodはpostを指定．
	後はentryArticle.jspと同様なので，そちらのコメントを参考にするとよい．-->
	<form action="./UpdateUserServletAns" method="post">
		<p><label>ログインID：<%=user.getId()%></label></p><!--userIdを埋め込み-->
		<p><label>名前：<input type="text" name="name" size="40" maxlength="20" value=<%=user.getName()%>></label></p>
		<!-- typeをパスワードにすると，文字が目隠しされる．他にも，数値や日付，メールアドレスなどを入力するtypeが指定できるので，調べてみるとよい．-->
		<p><label>現在のパスワード：<input type="password" name="currentPassword" size="40" maxlength="20"></label></p>
		<p><label>新しいパスワード：<input type="password" name="newPassword1" size="40" maxlength="20"></label></p>
		<p><label>確認用パスワード：<input type="password" name="newPassword2" size="40" maxlength="20"></label></p>
		<p><input type="submit" value="更新"></p>
	</form>
</body>
</html>