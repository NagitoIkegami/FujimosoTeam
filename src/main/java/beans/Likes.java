package beans;

import java.io.Serializable;

public class Likes implements Serializable{
	private String user_id;
	private String article;
	private boolean good;
	
	public Likes() {
	}
	
	public Likes(String user_id, String article, boolean good) {
		super();
		this.user_id = user_id;
		this.article = article;
		this.good = good;
	}
	
	public Likes(String user_id, String article) {
		this(user_id, article, false);
	}
	
	public String getuserId() {
		return user_id;
	}

	public void setuserId(String user_id) {
		this.user_id = user_id;
	}
	
	public String getarticle() {
		return article;
	}
	
	public void setarticle(String article) {
		this.article = article;
	}
	
	public boolean getgood() {
		return good;
	}
	
	public void setgood(boolean good) {
		this.good = good;
	}
}
