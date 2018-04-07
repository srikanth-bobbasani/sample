package com.sri.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadFiles {

	private static final String RESOURCES = "./resources";
	private static final String UNIQUE = RESOURCES + "/sub/";

	public static void main(String[] args) {
		Map<String, String> allProps = new HashMap<>();

		try {
			File folder = new File(RESOURCES);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (file.isFile()) {
					loadProps(file, allProps);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void loadProps(File file, Map<String, String> allProps) {
		Properties prop = new Properties();
		InputStream input = null;
		Map<String, String> uniquePorps = new HashMap<>();
		try {

			input = new FileInputStream(file.getPath());
			// load a properties file
			prop.load(input);
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				if (!allProps.containsKey(key)) {
					uniquePorps.put(key, value);
				}
				allProps.put(key, value);
			}
			writeProps(file, uniquePorps);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	static void writeProps(File file, Map<String, String> props) {
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(UNIQUE + file.getName());
			props.forEach((k, v) -> prop.setProperty(k, v));

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
