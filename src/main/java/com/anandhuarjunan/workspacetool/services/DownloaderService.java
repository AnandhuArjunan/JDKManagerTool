package com.anandhuarjunan.workspacetool.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javafx.util.Pair;

public class DownloaderService {

	private String userFolder = System.getProperty("user.home");

	public File downloadToFolder(String assetURL, File folderLocation) throws IOException {
		File filePath = new File(folderLocation.getPath() + File.separator + FilenameUtils.getName(assetURL));
		return downloadWithIO(assetURL, filePath);
	}

	private File downloadWithIO(String assetURL, File fileLocation) throws IOException {
		URL url = new URL(assetURL);
		FileUtils.copyURLToFile(url, fileLocation);
		return fileLocation;
	}

	public File download(String assetURL, File fileLocation) throws IOException {
		return downloadWithIO(assetURL, fileLocation);
	}

	public File downloadToUserFolder(String assetURL, String appFolder) throws IOException {
		if (Objects.nonNull(appFolder)) {
			return downloadWithIO(assetURL, new File(userFolder + File.pathSeparator + appFolder));
		} else {
			return downloadWithIO(assetURL, new File(userFolder));
		}
	}

	public File downloadAndUpdateProgress(String url,File folder,Consumer<Pair<Long, Long>> fileSizes) throws IOException {

		HttpURLConnection httpConnection = (HttpURLConnection) (new URL(url).openConnection());
		File downloadFile = new File(folder.getPath() + File.separator + FilenameUtils.getName(url));
		try (BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
				FileOutputStream fos = new java.io.FileOutputStream(downloadFile);
				BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);) {
			long completeFileSize = httpConnection.getContentLength();
			byte[] data = new byte[1024];
			long downloadedFileSize = 0;
			int x = 0;
			while ((x = in.read(data, 0, 1024)) >= 0) {
				downloadedFileSize += x;
				fileSizes.accept(new Pair<>(downloadedFileSize, completeFileSize));
				bout.write(data, 0, x);
			}
		}
		return downloadFile;
	}

}
