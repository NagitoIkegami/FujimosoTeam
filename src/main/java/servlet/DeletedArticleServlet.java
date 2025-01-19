package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;

@WebServlet("/DeletedArticleServlet")
public class DeletedArticleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeletedArticleServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
     System.out.print("テスト");
        // リクエストの文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // フォームから送信された記事IDを取得
        String articleIdParam = request.getParameter("articleId");

        // 入力データのチェック
        if (articleIdParam == null || articleIdParam.isEmpty()) {
            // エラーが発生した場合も記事一覧ページにリダイレクト
            response.sendRedirect(request.getContextPath() + "/ArticleListServlet");
            return;
        }

        // 記事IDを整数に変換
        int articleId;
        try {
            articleId = Integer.parseInt(articleIdParam);
        } catch (NumberFormatException e) {
            // エラーが発生した場合も記事一覧ページにリダイレクト
            response.sendRedirect(request.getContextPath() + "/ArticleListServlet");
            return;
        }

        // DBアクセス用のDAOインスタンスを生成
        Dao dao = new Dao();

        try {
            // 記事を削除
            dao.deleteArticle(articleId);

            // 削除後、記事一覧ページにリダイレクトできる
            response.sendRedirect(request.getContextPath() + "/ArticleListServlet");
        } catch (SQLException e) {
            // エラー時の処理
            e.printStackTrace();

            // エラーが発生した場合も記事一覧ページにリダイレクトできる
            response.sendRedirect(request.getContextPath() + "/ArticleListServlet");
        }
    }
}