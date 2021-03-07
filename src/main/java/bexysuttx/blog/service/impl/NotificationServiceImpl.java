package bexysuttx.blog.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bexysuttx.blog.model.AbstractModel;
import bexysuttx.blog.service.NotificationService;

class NotificationServiceImpl implements NotificationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
	private final ServiceManager serviceManager;
	private final ExecutorService executorService;
	private final int tryCount;

	public NotificationServiceImpl(ServiceManager serviceManager) {
		super();
		this.serviceManager = serviceManager;
		this.executorService = Executors.newCachedThreadPool();
		this.tryCount = Integer.parseInt(serviceManager.getApplicationProperties("email.sendTryCount"));
	}

	@Override
	public void sendNotification(String title, String content) {
		executorService.submit(new EmailItem(title, content, tryCount));
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	private class EmailItem extends AbstractModel implements Runnable {
		private final String title;
		private final String content;
		private int tryCount;

		public EmailItem(String title, String content, int tryCount) {
			super();
			this.title = title;
			this.content = content;
			this.tryCount = tryCount;
		}

		private boolean isValidTryCount() {
			return tryCount > 0;
		}

		@Override
		public void run() {
			try {
				String notificationEmail = serviceManager.getApplicationProperties("email.notificationEmail");
				SimpleEmail email = new SimpleEmail();
				email.setCharset("utf-8");
				email.setHostName(serviceManager.getApplicationProperties("email.smtp.server"));
				email.setSSLOnConnect(true);
				email.setSslSmtpPort(serviceManager.getApplicationProperties("email.smtp.port"));
				email.setAuthenticator(
						new DefaultAuthenticator(serviceManager.getApplicationProperties("email.smtp.username"),
								serviceManager.getApplicationProperties("email.smtp.password")));
				email.setFrom(serviceManager.getApplicationProperties("email.fromEmail"));
				email.setSubject(title);
				email.setMsg(content);
				email.addTo(notificationEmail);
				email.send();
			} catch (EmailException e) {
				LOGGER.error("Can't send email: " + e.getMessage(), e);
				tryCount--;
				if (isValidTryCount()) {
					LOGGER.info("Resend email: " + this.toString());
					executorService.submit(this);
				} else {
					LOGGER.error("Email was not send: limit of try count");
				}
			} catch (Exception e) {
				LOGGER.error("Error during send email: " + e.getMessage(), e);
			}
		}

	}

}
