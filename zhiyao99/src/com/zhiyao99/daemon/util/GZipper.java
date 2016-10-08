/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.util.GZipper.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:17:55</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class GZipper {

	/** Create logs for GZipper.class */
	private static Logger logger = LoggerFactory.getLogger(GZipper.class);

	/** Expected compression ratio such as 5 means 5 times*/
	private static final int EXPECTED_COMPRESSION_RATIO = 5;
	
	/** Buffer size means once read 32768Byte */
	private static final int size_32k = 32768;
	
	/** Create size=32768Byte[32k] buffer array */
	private static byte[] buf = new byte[size_32k];

	/**
	 * Unzip html content if possible
	 * @param in
	 * @return byte[] unzip html content
	 */
	public static final byte[] unzipBestEffort(byte[] in) {
		return unzipBestEffort(in, Integer.MAX_VALUE);
	}

	/**
	 * Unzip html content if possible
	 * @param in
	 * @param offset
	 * @param len
	 * @param sizeLimit
	 * @return byte[] unzip html content
	 */
	public static final byte[] unzipBestEffort(byte[] in, int offset, int len,
			int sizeLimit) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream(
					EXPECTED_COMPRESSION_RATIO * in.length);
			GZIPInputStream inStream = new GZIPInputStream(
					new ByteArrayInputStream(in, offset, len));

			int written = 0;
			int size = 0;
			while (true) {
				size = inStream.read(buf);
				if (size <= 0)
					break;

				if ((written + size) > sizeLimit) {
					outStream.write(buf, 0, sizeLimit - written);
					break;
				}

				outStream.write(buf, 0, size);
				written += size;
			}

			outStream.close();
			return outStream.toByteArray();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	/**
	 * Unzip html content if possible
	 * @param in
	 * @param sizeLimit
	 * @return byte[] unzip html content
	 */
	public static final byte[] unzipBestEffort(byte[] in, int sizeLimit) {
		return unzipBestEffort(in, 0, in.length, sizeLimit);
	}

	/**
	 * Common method to unzip html
	 * @param in
	 * @param offset
	 * @param len
	 * @return byte[] 
	 * @throws IOException
	 */
	public static final byte[] unzip(byte[] in, int offset, int len)
			throws IOException {
		int size = 0;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(
				EXPECTED_COMPRESSION_RATIO * in.length);
		GZIPInputStream inStream = new GZIPInputStream(
				new ByteArrayInputStream(in, offset, len));

		while (true) {
			size = inStream.read(buf);
			if (size <= 0)
				break;
			outStream.write(buf, 0, size);
		}
		outStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static final byte[] unzip(byte[] in) throws IOException {
		return unzip(in, 0, in.length);
	}

	public static final byte[] zip(byte[] in) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(in.length
					/ EXPECTED_COMPRESSION_RATIO);
			GZIPOutputStream outStream = new GZIPOutputStream(byteOut);

			try {
				outStream.write(in);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}

			try {
				outStream.close();
			} catch (IOException e) {
				logger.warn(e.getMessage());
			}

			return byteOut.toByteArray();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			return null;
		}
	}
}
