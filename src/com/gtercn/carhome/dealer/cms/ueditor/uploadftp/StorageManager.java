package com.gtercn.carhome.dealer.cms.ueditor.uploadftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.gtercn.carhome.dealer.cms.ApplicationConfig;

/**
 * 内容反编译百度原有内容,加入几个FTP相关函数
 * 
 * @author ken 2017-2-24 上午11:18:06
 */
public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);
		State state = valid(file);
		if (!state.isSuccess()) {
			return state;
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, 4);
		}
		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo("size", data.length);
		state.putInfo("title", file.getName());
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		State state = null;
		File tmpFile = getTmpFile();
		byte[] dataBuf = new byte[2048];
		BufferedInputStream bis = new BufferedInputStream(is, 8192);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), 8192);
			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();
			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, 1);
			}
			state = saveTmpFile(tmpFile, path);
			if (!state.isSuccess()) {
				tmpFile.delete();
			}
			return state;
		} catch (IOException localIOException) {
		}
		return new BaseState(false, 4);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;
		File tmpFile = getTmpFile();
		byte[] dataBuf = new byte[2048];
		BufferedInputStream bis = new BufferedInputStream(is, 8192);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), 8192);
			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();
			state = saveTmpFile(tmpFile, path);
			if (!state.isSuccess()) {
				tmpFile.delete();
			}
			return state;
		} catch (IOException localIOException) {
		}
		return new BaseState(false, 4);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		double d = Math.random() * 10000.0D;
		String tmpFileName = String.valueOf(d).replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);
		if (targetFile.canWrite())
			return new BaseState(false, 2);
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, 4);
		}
		state = new BaseState(true);
		state.putInfo("size", targetFile.length());
		state.putInfo("title", targetFile.getName());
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();
		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, 3);
		}
		if (!parentPath.canWrite()) {
			return new BaseState(false, 2);
		}
		return new BaseState(true);
	}

	/**
	 * 上传FTP文件
	 * 
	 * @param is
	 * @param path
	 * @param maxSize
	 * @return
	 */
	public static State saveFtpFileByInputStream(InputStream is,
			String remoteDir, String path, long maxSize, boolean keepLocalFile) {
		State state = null;
		File tmpFile = getTmpFile();
		byte[] dataBuf = new byte[2048];
		BufferedInputStream bis = new BufferedInputStream(is, 8192);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), 8192);
			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();
			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, 1);
			}
			state = saveFtpTmpFile(tmpFile, remoteDir, path, keepLocalFile);
			if (!state.isSuccess()) {
				tmpFile.delete();
			}
			return state;
		} catch (IOException localIOException) {
		}
		return new BaseState(false, 4);
	}

	static String ip = ApplicationConfig.FTP_IP;
	static int port = ApplicationConfig.FTP_PORT;
	static String userName = ApplicationConfig.FTP_USERNAME;
	static String passWord = ApplicationConfig.FTP_PASSWORD;

	private static State saveFtpTmpFile(File tmpFile, String remoteDir,
			String path, boolean keepLocalFile) {
		State state = null;
		File targetFile = new File(path);
		if (targetFile.canWrite())
			return new BaseState(false, 2);
		try {
			FileUtils.moveFile(tmpFile, targetFile);
			FTPClient ftp = new FTPClient();
			ftp.connect(ip, port);
			ftp.login(userName, passWord);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return new BaseState(false, 4);
			}else{
				if(remoteDir!=null && !remoteDir.equals("")){
					List<String> listPath=Arrays.asList(remoteDir.split("/"));
					for (String pa:listPath) {
						ftp.makeDirectory(pa);
						ftp.changeWorkingDirectory(pa);
					}
				}
				ftp.setFileType(2);
				System.out.println(targetFile.getName());
				InputStream is=new FileInputStream(new File(path));
				boolean boo=ftp.storeFile(targetFile.getName(), is);
				if (!boo) {
					return new BaseState(false, 4);
				}
			}
			
		} catch (Exception e) {
			return new BaseState(false, 4);
		}
		try {
			if (!keepLocalFile)
				targetFile.delete();
		} catch (Exception e) {

		}
		state = new BaseState(true);
		state.putInfo("size", targetFile.length());
		state.putInfo("title", targetFile.getName());
		return state;
	}
}
