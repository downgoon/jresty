package io.downgoon.jresty.commons.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtils {

	public static boolean appendStringToFile(File file, String str) {
		boolean ret = false;
		if (file == null || !file.exists() || str == null)
			return ret;
		BufferedWriter bw = null; // Don't use PrinterWriter
		try{
			bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(str);
			bw.newLine();
			bw.flush();
			ret = true;
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}finally{
			if(bw != null)
			try {
				bw.close();
			} catch (IOException ioe2) {}
		}
		return ret;
	}
	
	public static String readFileContent(String file) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(FileUtils.class.getClassLoader()
                    .getResourceAsStream(file)));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }

	public static String getLastFile(String path) {
		File file = new File(path);
		if (file == null || !file.exists() || !file.isDirectory())
			return null;
		String files[] = file.list();
		if (files == null || files.length == 0)
			return null;
		Arrays.sort(files, 0, files.length, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1 == o2) {
					return 0;
				} else if (o1 == null || "".equals(o1)) {
					return -1;
				} else if (o2 == null || "".equals(o2)) {
					return 1;
				}
				int i1 = Integer.parseInt(o1);
				int i2 = Integer.parseInt(o2);
				if (i1 == i2)
					return 0;
				return i1 > i2 ? -1 : 1;
			}
		});
		return files[0];
	}
	

}
