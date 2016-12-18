package io.downgoon.jresty.investigation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BanWordUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BanWordUtils.class);
	
	private static String banWordFilePath = "banWord.properties";
	
	/** 禁词-行号 （不写文件，只加载，则用HashMap，否则用LinkedHashMap）*/
//	private static transient Map<String,Integer> banWordHash = new HashMap<String,Integer>();
	private static transient Map<String,Integer> banWordHash = new LinkedHashMap<String,Integer>();
	private static BufferedWriter writer = null;
	private static BufferedReader reader = null;
	private static File file = null;
	static {
		load();
	}

	public static Map<String,Integer> index() {
		return banWordHash;
	}
	
	public static void load() {
		try {
			LOGGER.info("(Re)Load BanWord From "+banWordFilePath+" ...");
			long tmStart = System.currentTimeMillis();
			long tmEnd = tmStart;
			reader = new BufferedReader(new FileReader(BanWordUtils.class.getClassLoader().getResource(banWordFilePath).getFile()));
			int lineNum = 0;
			String lineText = null;
			while ((lineText=reader.readLine()) != null) {
				lineNum ++;
				if (isNotEmpty(lineText)) {
					banWordHash.put(lineText.trim(), lineNum);
				}
			}
			tmEnd = System.currentTimeMillis();
			LOGGER.info("(Re)Load BanWord From "+banWordFilePath+" ...OK items:"+lineNum+",cost:"+((tmEnd-tmStart)/1000L)+"s,"+(tmEnd-tmStart)+"ms");
			
		} catch (FileNotFoundException e) {
			LOGGER.warn("(Re)Load BanWord FileNotFoundException: "+e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.warn("(Re)Load BanWord Read IOException: "+e.getMessage(), e);
		} catch(Exception e) {
			if(reader==null) {
				LOGGER.warn("BanWord File: "+banWordFilePath+" Not Found in classpath", e);
				return;
			}
		} 
		finally {
			try {
				if(reader!=null) {
					reader.close();
				}
				
			} catch (IOException e) {
				LOGGER.warn("(Re)Load BanWord Close Exception: "+e.getMessage(), e);
			}
		}
	}

	public static void reload() {
		banWordHash.clear();
		load();
	}
	
	protected static void save() {
		LOGGER.info("Save BanWord into "+banWordFilePath+" ...");
		file = new File(BanWordUtils.class.getClassLoader().getResource(banWordFilePath).getFile());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				LOGGER.warn("Save BanWord File Create IOException: "+e.getMessage(), e);
			} catch(Exception e) {
				LOGGER.warn("Save BanWord File Create Exception: "+e.getMessage(), e);
			} 
		}
		try {
			writer = new BufferedWriter(new FileWriter(file));
			Collection<String> c = banWordHash.keySet();
			Iterator<String> banWords = c.iterator();
			String banItem = "";
			while (banWords.hasNext()) {
				banItem = banWords.next();
				if (isNotEmpty(banItem)) {
					writer.write(banItem);
					writer.newLine();
					writer.flush();
				}
			}
		} catch (IOException e) {
			LOGGER.warn("Save BanWord IOEception: "+e.getMessage(),  e);
		} finally {
			try {
				if(writer!=null) {
					writer.close();
				}
				
			} catch (IOException e) {
				LOGGER.warn("Save BanWord File Close Exception: "+e.getMessage(),  e);
			}
		}
	}
	

	public static String hit(String text) {
		if (banWordHash==null || banWordHash.isEmpty() || isEmpty(text)) {
			return null;
		}
		Collection<String> c = banWordHash.keySet();
		Iterator<String> banWords = c.iterator();
		String banItem = null;
		int lineNum = 0;
		while (banWords.hasNext()) {
			lineNum ++;
			banItem = banWords.next();
			if (isNotEmpty(banItem) && text.indexOf(banItem) != -1) {
				LOGGER.info("BanWordHit: ban="+banItem+",line="+lineNum);
				return banItem;
			}
		}
		return null;
	}

	public static void append(String banWord, boolean refresh) {
		if(isNotEmpty(banWord)) {
			banWordHash.put(banWord.trim(), banWordHash.size()+1);
		}
		if(refresh) {
			save();
			reload();
		}
	}

	public static void remove(String banWord, boolean refresh) {
		if(isNotEmpty(banWord)) {
			banWordHash.remove(banWord);
		}
		if(refresh) {
			save();
			reload();
		}
	}
	
	public static void refresh() {
		save();
		reload();
	}
	
	public static void main(String[] args) {
		String ban = BanWordUtils.hit("001工程");
		LOGGER.info("命中："+ban);
	}
	
	private static boolean isNotEmpty(String text) {
		return text != null && text.length() > 1;
	}
	
	private static boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}
}

