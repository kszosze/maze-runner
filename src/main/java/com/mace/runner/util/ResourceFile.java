package com.mace.runner.util;

import java.io.*;
import java.net.URL;

public class ResourceFile {

	private String fileName;

	public ResourceFile(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {

		String line;
		StringBuffer sb = new StringBuffer();
		try (FileReader reader = new FileReader(openFile());
		     BufferedReader br = new BufferedReader(reader)) {

			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			return sb.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private File openFile() {

		URL resource = ClassLoader.getSystemClassLoader().getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			return new File(resource.getFile());
		}

	}
}
