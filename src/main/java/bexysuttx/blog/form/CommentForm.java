package bexysuttx.blog.form;

import org.apache.commons.lang3.StringUtils;

import bexysuttx.blog.exception.ValidateException;
import bexysuttx.blog.service.I18nService;

public class CommentForm extends AbstractForm {
	private Long idArticle;
	private String content;
	private String authToken;

	public Long getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(Long idArticle) {
		this.idArticle = idArticle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public void validate(I18nService i18nService) throws ValidateException {
		if (idArticle == null) {
			throw new ValidateException("IdArticle is required.");
		}
		if (StringUtils.isBlank(content)) {
			throw new ValidateException("Content is required.");
		}
		if (StringUtils.isBlank(authToken)) {
			throw new ValidateException("AuthToken is required.");
		}
	}

}
