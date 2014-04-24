package com.ahmed.tinystartapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility class provides methods for file processing.
 * 
 * 
 * @author Ahmed
 * 
 */
public final class FileUtil {

	/**
	 * Internal constructor; not to be called as this class provides static
	 * utilities only.
	 */
	private FileUtil() {
		throw new UnsupportedOperationException("No instances permitted");
	}

	/**
	 * Copy the file from from to to
	 * 
	 * @param from
	 *            source
	 * @param to
	 *            destination
	 * @throws IOException
	 */
	public static void copy(InputStream from, OutputStream to)
			throws IOException {
		byte[] buffer = new byte[1024];
		int length;
		while ((length = from.read(buffer)) > 0) {
			to.write(buffer, 0, length);
		}
		to.flush();
		to.close();
		from.close();
	}

}
