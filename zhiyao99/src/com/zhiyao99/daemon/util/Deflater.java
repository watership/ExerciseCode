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
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.DeflaterOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.util.Deflater.java</p>
 * <p>描述:</p>
 * <p>日期:2013-4-23 下午04:17:41</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class Deflater {

	/** Create logs for Deflater.class */
	private static Logger logger = LoggerFactory.getLogger(Deflater.class);

	/** Expected compression ratio such as 5 means 5 times */
	private static final int EXPECTED_COMPRESSION_RATIO = 5;
	
	/** Buffer size means once read 8192Byte */
	private static final int size_8k = 8192;
	
	/** Create size=8191Byte[8k] buffer array */
	private static byte[] buf = new byte[size_8k];

	/**
	 * Infalte html content into big byte[]
	 * @param in
	 * @return byte[] which which has been Inflated
	 */
	public static final byte[] inflateBestEffort(byte[] in) {
		return inflateBestEffort(in, Integer.MAX_VALUE);
	}

	/**
	 * Infalte html content into big byte[] and use maxSize to limit it
	 * @param in
	 * @param sizeLimit
	 * @return byte[] which which has been Inflated
	 */
	public static final byte[] inflateBestEffort(byte[] in, int sizeLimit) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(
				EXPECTED_COMPRESSION_RATIO * in.length);

		// "true" because HTTP does not provide zlib headers
		Inflater inflater = new Inflater(true);
		InflaterInputStream inStream = new InflaterInputStream(
				new ByteArrayInputStream(in), inflater);

		int written = 0;
		int size = 0;
		while (true) {
			try {
				size = inStream.read(buf);
				if (size <= 0)
					break;

				if ((written + size) > sizeLimit) {
					outStream.write(buf, 0, sizeLimit - written);
					break;
				}

				outStream.write(buf, 0, size);
				written += size;
			} catch (Exception e) {
				logger.warn(e.getMessage());
				break;
			}
		}

		try {
			outStream.close();
		} catch (IOException e) {
			;
		}

		return outStream.toByteArray();
	}

	/**
	 * Infalte html content into big byte[] and use maxSize to limit it
	 * @param in
	 * @param offset
	 * @param len
	 * @return byte[] which which has been Inflated
	 * @throws IOException
	 */
	public static final byte[] inflate(byte[] in, int offset, int len)
			throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(
				EXPECTED_COMPRESSION_RATIO * in.length);
		InflaterInputStream inStream = new InflaterInputStream(
				new ByteArrayInputStream(in, offset, len));

		int size = 0;
		while (true) {
			size = inStream.read(buf);
			if (size <= 0)
				break;

			outStream.write(buf, 0, size);
		}

		outStream.close();
		return outStream.toByteArray();
	}

	public static final byte[] inflate(byte[] in) throws IOException {
		return inflate(in, 0, in.length);
	}

	/**
	 * Deflate html content into small byte[]
	 * @param in
	 * @return byte[] which which has been Deflated
	 */
	public static final byte[] deflate(byte[] in) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(in.length
				/ EXPECTED_COMPRESSION_RATIO);
		DeflaterOutputStream outStream = new DeflaterOutputStream(byteOut);

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
	}
}
