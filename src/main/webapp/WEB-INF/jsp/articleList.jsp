<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ここまで， JSPを書く時の決まり文句-->

<!-- ここから，インポート宣言 -->    
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import ="beans.Article" %>
<%@page import="java.sql.Timestamp"%>
<!-- ここまで，インポート宣言 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Article list page</title>
</head>
<body>
	<label>記事一覧</label><br><br>
	<!-- UpdateUserPageServletAnsにハイパーリンク．
	ハイパーリンクによるアクセスは，GETアクセスとなり，サーブレットのdoGet()が呼ばれる． -->
	<a href = ./UpdateUserPageServletAns>ユーザ情報更新</a><br><br>
	<!-- EntryArticlePageServletにハイパーリンク．
	ハイパーリンクによるアクセスは，GETアクセスとなり，サーブレットのdoGet()が呼ばれる． -->
	<a href = ./EntryArticlePageServlet>新規記事登録</a><br><br>

	<!-- 直前のサーブレットでrequestにsetAttributeしておいたオブジェクトを取り出す．
	普通に取り出すとObject型になってしまうので，適宜キャストして変数に受け取る． -->
	<%List<Article> aList = (List<Article>)( request.getAttribute("articleList") );%>
	
	<!-- javaの文法とhtmlを組み合わせて，記事リストを表示 -->
	<%for(Article a : aList){%>
		<!-- <label><%=String.valueOf(a.getId() )%></label> <br> -->
		<!-- %の直後に=を付けると，そのまま値を表示できる．代わりに，<%out.print(a.getTitle());%>のように書くことも可能 -->
		<label>タイトル：<br><%=a.getTitle()%></label> <br>
		<label>本文：<br><%=a.getBody()%></label> <br>
		<label>登録者：<br><%=a.getEditorId()%></label> <br>
		<!-- 日時を指定のフォーマットで表示するための命令 -->
		<%SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");%>
		<label>登録日時：<br><%=sdf.format( a.getEntryDatetime() )%></label>
		<br>
		<br>
	<%} %>
</body>
</html>