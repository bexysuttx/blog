package bexysuttx.blog.service;

import bexysuttx.blog.model.SocialAccount;

public interface SocialService {
	SocialAccount getSocialAccount(String authToken);
}
