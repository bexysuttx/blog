package bexysuttx.blog.entity;

import java.sql.Timestamp;

public class Comment extends AbstractEntity<Long> {
	private static final long serialVersionUID = 412249605665759589L;
	private Account account;
	private Long idArticle;
	private String content;

	public Comment(Account account, Long idArticle, String content, Timestamp created) {
		super();
		this.account = account;
		this.idArticle = idArticle;
		this.content = content;
		this.created = created;
	}

	public Comment() {
		super();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public long getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(long idArticle) {
		this.idArticle = idArticle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	private Timestamp created;

}
