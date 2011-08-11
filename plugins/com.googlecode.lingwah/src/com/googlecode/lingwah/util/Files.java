package com.googlecode.lingwah.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public final class Files
{
	private Files()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static String readAsString(final String filePath) throws java.io.IOException
	{
		FileInputStream inputStream= new FileInputStream(filePath);
		try {
			return readAsString(inputStream);
		}
		finally {
			try { inputStream.close(); } catch (Throwable t) { }
		}
	}

	public static String readAsString(final URL url) throws java.io.IOException
	{
		InputStream inputStream= url.openStream();
		try {
			return readAsString(inputStream);
		}
		finally {
			try { inputStream.close(); } catch (Throwable t) { }
		}
	}

	public static String readAsString(final InputStream inputStream) 
	throws java.io.IOException
	{
		final StringBuffer fileData = new StringBuffer(1000);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1)
		{
			final String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		return fileData.toString();
	}
}
