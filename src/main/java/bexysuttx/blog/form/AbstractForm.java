package bexysuttx.blog.form;

import java.util.Locale;

import bexysuttx.blog.exception.ValidateException;
import bexysuttx.blog.model.AbstractModel;
import bexysuttx.blog.service.I18nService;

public abstract class AbstractForm extends AbstractModel {
	protected Locale locale;

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void validate(I18nService i18nService) throws ValidateException {

	}

}
