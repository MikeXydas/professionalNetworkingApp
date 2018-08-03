package model;

public class CommentPKBean {
	
	private int idComment;
	private int article_idArticle;
	private int article_User_idUser;
	
	public int getIdComment() {
		return idComment;
	}
	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}
	public int getArticle_idArticle() {
		return article_idArticle;
	}
	public void setArticle_idArticle(int article_idArticle) {
		this.article_idArticle = article_idArticle;
	}
	public int getArticle_User_idUser() {
		return article_User_idUser;
	}
	public void setArticle_User_idUser(int article_User_idUser) {
		this.article_User_idUser = article_User_idUser;
	}

	
}
