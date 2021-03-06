package bexysuttx.blog.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import bexysuttx.blog.exception.ApplicationException;
import bexysuttx.blog.model.SocialAccount;
import bexysuttx.blog.service.SocialService;

class GooglePlusSocialService implements SocialService {
	private final String googlePlusClientId;
	private final List<String> issuers;

	GooglePlusSocialService(ServiceManager serviceManager) {
		super();
		this.googlePlusClientId = serviceManager.getApplicationProperties("social.googleplus.clientId");
		this.issuers = Arrays.asList(new String[] { "https://accounts.google.com", "accounts.google.com" });
	}

	@Override
	public SocialAccount getSocialAccount(String authToken) {
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
					JacksonFactory.getDefaultInstance()).setAudience(Collections.singleton(googlePlusClientId))
							.setIssuers(issuers).build();
			GoogleIdToken idToken = verifier.verify(authToken);
			if (idToken != null) {
				Payload payload = idToken.getPayload();
				String email = (String) ((com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload) payload)
						.getEmail();
				return new SocialAccount((String) payload.get("given_name"), email, (String) payload.get("picture"));
			} else {
				throw new ApplicationException("Can't get account by authToken: " + authToken);
			}
		} catch (GeneralSecurityException | IOException e) {
			throw new ApplicationException(e);
		}
	}

}
