<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
</head>
<body>
<label>記事検索</label>
 
 <form action="./SearchServlet" method="post">
  <p><label>記事を文字から検索：</label><br><textarea name="body" rows="5" cols="40" placeholder="文字を入力してください．"></textarea></p>
  <p><input type="submit" value="検索"></p>
 </form>
</body>
</html>