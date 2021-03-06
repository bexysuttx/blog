package bexysuttx.blog.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bexysuttx.blog.service.AvatarService;

import net.coobird.thumbnailator.Thumbnails;

class FileStorageAvatarService implements AvatarService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageAvatarService.class);
	private final String mediaDirParent;

	FileStorageAvatarService(ServiceManager serviceManager) {
		this.mediaDirParent = normalizeMediaDirPath(serviceManager.applicationContext.getRealPath("/"));
	}

	private String normalizeMediaDirPath(String realPath) {
		realPath = realPath.replace("\\", "/");
		if (realPath.endsWith("/")) {
			realPath = realPath.substring(0, realPath.length() - 1);
		}
		return realPath;
	}

	@Override
	public String downloadAvatar(String url) throws IOException {
		if (url != null) {
			String uid = UUID.randomUUID().toString() + ".jpg";
			String fullImgPath = mediaDirParent + MEDIA_AVATAR_PREFIX + uid;
			downloadImageFromUrl(url, fullImgPath);
			Thumbnails.of(new File(fullImgPath)).size(AVATAR_SIZE_IN_PX, AVATAR_SIZE_IN_PX)
					.toFile(new File(fullImgPath));
			return MEDIA_AVATAR_PREFIX + uid;
		} else {
			return null;
		}
	}

	private void downloadImageFromUrl(String url, String fullImgPath) throws MalformedURLException, IOException {
		File file = new File(fullImgPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
		try (InputStream in = new URL(url).openStream()) {
			Files.copy(in, Paths.get(fullImgPath));
		}

	}

	@Override
	public boolean deleteAvatarIfExists(String avatarPath) {
		if (avatarPath != null) {
			File avatar = new File(mediaDirParent + avatarPath);
			if (avatar.exists()) {
				if (avatar.delete()) {
					return true;
				} else {
					LOGGER.error("Can't delete file " + avatar.getAbsolutePath());

				}
			}
		}
		return false;
	}

}
