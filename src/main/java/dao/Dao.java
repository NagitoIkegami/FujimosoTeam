package dao;import java.sql.Connection;import java.sql.PreparedStatement;import java.sql.ResultSet;import java.sql.SQLException;import java.util.ArrayList;import java.util.List;import beans.Article;import beans.Likes;import beans.User;//DriverAccessorを継承
public class Dao extends DriverAccessor{
	//DBとのコネクションを入れる変数
	private Connection connection;
	public static final String UTF_8 = "UTF-8";	public static final String MS932 = "MS932";	public Dao(){
	}
	//ユーザidを入力すると，そのidを持つUserを返す
	public User getUserById(String id) {		//返り値用Userを作成		User user = new User();				//コネクションを生成		this.connection = this.createConnection();				//Exceptionが発生するので，try-catch
		try{			//SQL文の定義．?には，後で値を埋める．
			String sql = "select * from user where id = ?";
			//SQL文から，PreparedStatementを生成			PreparedStatement stmt = this.connection.prepareStatement(sql);						//SQL文の1個目の?に，文字列型のidを埋め込む．			//SQLインジェクション攻撃への対策を自動で行ってくれる.
			stmt.setString(1, id);			//SQL文を実行し，結果（ResultSet型）を受け取る．			//結果があるタイプのSQL文（selectなど）はexecuteQuery()で実行し，そうでないもの(insertなど)はexecuteUpdate()で実行する．			//executeUpdate()は，戻り値を受け取る必要はない．
			ResultSet rs = stmt.executeQuery();						//rs,first() : 取得した結果の最初のレコードが存在するとtrue			if(rs.first()){				//結果rsから，"id"というカラムの値を取得し，userのIdにセット(カラム名は，mysqlからテーブル構造を参照)				user.setId( rs.getString("id") );				//rsから，"password"というカラムの値を取得し，userのpasswordにセット（以下，同様）				user.setPassword( rs.getString("password") );				user.setName( rs.getString("name") );				user.setEntryDatetime( rs.getDate("entry_datetime") );			}			else{				this.closeConnection(this.connection);				return null;			}

		}catch(SQLException e){
			this.closeConnection(this.connection);
			e.printStackTrace();			return null;

		} finally {			this.closeConnection(this.connection);
		}

		return user;
	}	//Userを受け取り，DBに格納する	public void insertUser(User user) {		this.connection= this.createConnection();		try{			//SQL文を定義			//?には後で値を入れる．now()は，現在時刻を返すmysqlの関数．			String sql = "insert into user (id, password, name, entry_datetime) values(?, ?, ?, now())";			//SQL文からPreparedStatementを生成			PreparedStatement stmt = this.connection.prepareStatement(sql);			//1個目の「?」に値をセット			stmt.setString(1, user.getId() );			//2個目の「?」に値をセット			stmt.setString(2, user.getPassword());			//3個目の「?」に値をセット			stmt.setString(3, user.getName());			//SQL文を実行			stmt.executeUpdate();						stmt.close();			this.closeConnection(connection);		}catch(SQLException e){			this.closeConnection(connection);			e.printStackTrace();		} finally {			this.closeConnection(connection);		}	}		//Likesを受け取り、DBに格納する		public void insertuserGood(Likes likes) {			this.connection = this.createConnection();						try {		        // goodカラムの値を加算するSQL文		        String sql = "insert into likes (user_id, article_id) values(?, ?)";		        PreparedStatement stmt = this.connection.prepareStatement(sql);		        		        // ?に値をセット		        stmt.setString(1, likes.getuserId()); 		        stmt.setString(2, likes.getarticle()); //		        stmt.setString(3, "false");		        		        stmt.executeUpdate();		        		        // ステートメントを閉じる		        stmt.close();				this.closeConnection(connection);			}catch(SQLException e){				this.closeConnection(connection);				e.printStackTrace();			} finally {				this.closeConnection(connection);			}		}	public Article getArticleById(int id) {		Article article = new Article();		this.connection = this.createConnection();		try{			String sql = "select * from article where id = ?";			PreparedStatement stmt = this.connection.prepareStatement(sql);			stmt.setInt(1, id);			ResultSet rs = stmt.executeQuery();			if(rs.first()){				article.setId( rs.getInt("id") );				article.setTitle( rs.getString("title") );				article.setBody( rs.getString("body") );				article.setEditorId( rs.getString("editor_id") );				article.setEntryDatetime( rs.getTimestamp("entry_datetime") );				article.setGood( rs.getInt("good"));			}			else{				this.closeConnection(this.connection);				return null;			}		}catch(SQLException e){			this.closeConnection(this.connection);			e.printStackTrace();			return null;		} finally {			this.closeConnection(this.connection);		}		return article;	}	public Article getNewestArticleByEditorId(String editorId) {		Article article = new Article();		this.connection = this.createConnection();		try{			String sql = "select * from article where editor_id = ? order by entry_datetime desc";			PreparedStatement stmt = this.connection.prepareStatement(sql);			stmt.setString(1, editorId);			ResultSet rs = stmt.executeQuery();			if(rs.first()){				article.setId( rs.getInt("id") );				article.setTitle( rs.getString("title") );				article.setBody( rs.getString("body") );				article.setEditorId( rs.getString("editor_id") );				article.setEntryDatetime( rs.getTimestamp("entry_datetime"));				article.setGood( rs.getInt("good"));			}			else{				this.closeConnection(this.connection);				return null;			}		}catch(SQLException e){			this.closeConnection(this.connection);			e.printStackTrace();			return null;		} finally {			this.closeConnection(this.connection);		}		return article;	}	public void insertArticle(Article article) {		this.connection= this.createConnection();		try{			String sql = "insert into article (title, body, editor_id, good, entry_datetime) values(?, ?, ?, ?, now())";			PreparedStatement stmt = this.connection.prepareStatement(sql);			stmt.setString(1, article.getTitle());			stmt.setString(2, article.getBody());			stmt.setString(3, article.getEditorId());			stmt.setInt(4, article.getGood());			stmt.executeUpdate();			stmt.close();			this.closeConnection(connection);		}catch(SQLException e){			this.closeConnection(connection);			e.printStackTrace();		} finally {			this.closeConnection(connection);		}	}	//すべての記事のリストを返す	public List<Article> getArticleList() {		this.connection= this.createConnection();				//返り値用のリストを作成		List<Article> articleList = new ArrayList<Article>();		try{			//SQL文：articleから全てのカラム，全てのデータを取得せよ．ただし，登録日時が降順（つまり，新しい順）になるように並べ替える			String sql = "select * from article order by entry_datetime desc";			//PreparedStatementを生成			PreparedStatement stmt = this.connection.prepareStatement(sql);			//SQL文を実行			ResultSet rs = stmt.executeQuery();			//1件以上の結果が存在するかチェック			if(rs.first()){				do{					//DBから取得した値を引数としてArticleを作成					Article s = new Article( rs.getInt("id"), rs.getString("title"), rs.getString("body"), rs.getString("editor_id"), rs.getTimestamp("entry_datetime"), rs.getInt("good") );					//返り値用リストに追加					articleList.add(s);				}while(rs.next()); //最後の1件まで繰り返す			}		}catch(SQLException e){			this.closeConnection(connection);			e.printStackTrace();		} finally {			this.closeConnection(connection);		}		return articleList;	}		//検索文字の含まれる記事を返す		public List<Article> getSearch(String search) {			this.connection= this.createConnection();						//返り値用のリストを作成			List<Article> Search = new ArrayList<Article>();			try{				//SQL文：articleから全てのカラム，全てのデータを取得せよ．ただし，登録日時が降順（つまり，新しい順）になるように並べ替える				String sql = "select * from article where body like ? order by entry_datetime desc";				//PreparedStatementを生成				PreparedStatement stmt = this.connection.prepareStatement(sql);				stmt.setString(1 ,search);				//SQL文を実行				ResultSet rs = stmt.executeQuery();				//1件以上の結果が存在するかチェック				if(rs.first()){					do{						//DBから取得した値を引数としてArticleを作成						Article s = new Article( rs.getInt("id"), rs.getString("title"), rs.getString("body"), rs.getString("editor_id"), rs.getTimestamp("entry_datetime"),rs.getInt("good") );						//返り値用リストに追加						Search.add(s);					}while(rs.next()); //最後の1件まで繰り返す				}			}catch(SQLException e){				this.closeConnection(connection);				e.printStackTrace();			} finally {				this.closeConnection(connection);			}			return Search;		}		//Userを受け取り，同一userIdのデータをアップデートする	public void updateUser(User user) {		this.connection= this.createConnection();		try{			//SQL文を定義			//?には後で値を入れる．			String sql = "update user set password=?, name=? where id=?";			//SQL文からPreparedStatementを生成			PreparedStatement stmt = this.connection.prepareStatement(sql);			//1個目の「?」に値をセット			stmt.setString(1, user.getPassword() );			//2個目の「?」に値をセット			stmt.setString(2, user.getName());			//3個目の「?」に値をセット			stmt.setString(3, user.getId());			//SQL文を実行			stmt.executeUpdate();						stmt.close();			this.closeConnection(connection);		}catch(SQLException e){			this.closeConnection(connection);			e.printStackTrace();		} finally {			this.closeConnection(connection);		}	}//	public void updateGood(String article) {//		this.connection = this.createConnection();//		//		try {//			String sql = "select good from article where id = ?";//			PreparedStatement stmt = this.connection.prepareStatement(sql);//			//			stmt.setString(1, article);//			//			stmt.executeUpdate();//			stmt.close();//			this.closeConnection(connection);//		}catch(SQLException e){//			this.closeConnection(connection);//			e.printStackTrace();//		} finally {//			this.closeConnection(connection);//		}//	}		//記事のいいね数取得	public int updateGood(String article) {	    this.connection = this.createConnection();	    int goodCount = 0; // 取得した「いいね数」を格納する変数	    try {	        // SQL文：指定されたIDの記事の「いいね数」を取得	        String sql = "SELECT good FROM article WHERE id = ?";	        PreparedStatement stmt = this.connection.prepareStatement(sql);	        	        stmt.setString(1, article);	        	        // クエリを実行して結果を取得	        ResultSet resultSet = stmt.executeQuery();	        	        // 必ず1件のデータが返ることを想定	        if (resultSet.next()) {	            goodCount = resultSet.getInt("good"); // 「いいね数」を取得	        }	        	        // リソースを閉じる	        resultSet.close();	        stmt.close();	    } catch (SQLException e) {	        e.printStackTrace();	    } finally {	        // 接続を閉じる	        this.closeConnection(connection);	    }	    	    // 取得した「いいね数」を返す	    return goodCount;	}		//記事のいいね数を増加させた値で更新	public void addGood(int add, String id) {	    this.connection = this.createConnection();	    	    try {	        // goodカラムの値を加算するSQL文	        String sql = "update article set good = ? where id = ?";	        PreparedStatement stmt = this.connection.prepareStatement(sql);	        	        // ?に値をセット	        stmt.setInt(1, add); // addを加算	        stmt.setString(2, id); // IDを指定	        	        stmt.executeUpdate();	        	        // ステートメントを閉じる	        stmt.close();	    } catch (SQLException e) {	        e.printStackTrace();	    } finally {	        // 接続を閉じる	        this.closeConnection(connection);	    }	}		public boolean getuserGood(String userId, String good) {	    this.connection = this.createConnection();	    boolean userGood = false; // 取得した「いいね数」を格納する変数	    try {	        // SQL文：指定されたIDの記事の「いいね数」を取得	        String sql = "SELECT good FROM likes WHERE user_id = ? AND article_id = ?";	        PreparedStatement stmt = this.connection.prepareStatement(sql);	        	        stmt.setString(1, userId);	        stmt.setString(2, good);	        	        // クエリを実行して結果を取得	        ResultSet resultSet = stmt.executeQuery();	        	        // 必ず1件のデータが返ることを想定	        if (resultSet.next()) {	            // 'good'が1なら、userGoodをtrueに設定（いいねがある場合）	            userGood = resultSet.getInt("good") == 1;	        } else {	            // 見つからない場合は、ユーザーがいいねをしていないと判断	            userGood = false;	        }	        	        // リソースを閉じる	        resultSet.close();	        stmt.close();	    } catch (SQLException e) {	        e.printStackTrace();	    } finally {	        // 接続を閉じる	        this.closeConnection(connection);	    }	    	    // 取得した「いいね数」を返す	    return userGood;	}		public void trueuesrGood(String userId, String good) {		this.connection = this.createConnection();		try{			//SQL文を定義			//?には後で値を入れる．			String sql = "update likes set good = true where user_id = ? and article_id = ?;";			//SQL文からPreparedStatementを生成			PreparedStatement stmt = this.connection.prepareStatement(sql);			//1個目の「?」に値をセット			stmt.setString(1, userId);			//2個目の「?」に値をセット			stmt.setString(2, good);			//3個目の「?」に値をセット			//SQL文を実行			stmt.executeUpdate();						stmt.close();			this.closeConnection(connection);		}catch(SQLException e){			this.closeConnection(connection);			e.printStackTrace();		} finally {			this.closeConnection(connection);		}			}		public void falseuesrGood(String userId, String good) {		this.connection = this.createConnection();		try{			//SQL文を定義			//?には後で値を入れる．			String sql = "update likes set good = false where user_id = ? and article_id = ?;";			//SQL文からPreparedStatementを生成			PreparedStatement stmt = this.connection.prepareStatement(sql);			//1個目の「?」に値をセット			stmt.setString(1, userId);			//2個目の「?」に値をセット			stmt.setString(2, good);			//3個目の「?」に値をセット			//SQL文を実行			stmt.executeUpdate();						stmt.close();			this.closeConnection(connection);		}catch(SQLException e){			this.closeConnection(connection);			e.printStackTrace();		} finally {			this.closeConnection(connection);		}			}		public void deleteArticle(int articleId) throws SQLException {	    String sql = "DELETE FROM article WHERE id = ?";	    try (Connection conn = createConnection();	         PreparedStatement pstmt = conn.prepareStatement(sql)) {	        pstmt.setInt(1, articleId);	        pstmt.executeUpdate();	    }	}		
}

