package bexysuttx.blog.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import bexysuttx.blog.exception.ValidateException;
import bexysuttx.blog.service.I18nService;

public class ContactForm extends AbstractForm {
	private String email;
	private String name;
	private String text;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void validate(I18nService i18nService) throws ValidateException {
		if (!EmailValidator.getInstance().isValid(email)) {
			throw new ValidateException("Email is invalid");
		}
		if (StringUtils.isBlank(name)) {
			throw new ValidateException("Name is required");
		}
		if (StringUtils.isBlank(text)) {
			throw new ValidateException("Text is required");
		}
	}
}
