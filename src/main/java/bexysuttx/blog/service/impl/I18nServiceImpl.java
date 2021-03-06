package bexysuttx.blog.service.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import bexysuttx.blog.service.I18nService;

class I18nServiceImpl implements I18nService {

	@Override
	public String getMessage(String key, Locale locale, Object... args) {
		String value = ResourceBundle.getBundle("i18n/message", locale).getString(key);
		for (int i = 0; i < args.length; i++) {
			value = value.replace("{" + i + "}", args[i].toString());
		}
		return value;
	}

}
