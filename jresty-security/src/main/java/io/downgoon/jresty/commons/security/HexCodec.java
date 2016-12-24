package io.downgoon.jresty.commons.security;

import java.io.ByteArrayOutputStream;

/**
 * @author liwei
 * @since	2008-08-05
 * */
public class HexCodec {
	
	public static String hexUpperAscii(byte[] data) {
		return byte2HexFormat(data, 16, Configuration.byte2HexUpper,Configuration.byte2ASCII);
	}	
	
	public static String hexLowerAscii(byte[] data) {
		return byte2HexFormat(data, 16, Configuration.byte2HexLower,Configuration.byte2ASCII);
	}
	
	public static String hexUpperAscii(byte[] data,int width) {
		return byte2HexFormat(data, width, Configuration.byte2HexUpper,Configuration.byte2ASCII);
	}
	
	public static String hexLowerAscii(byte[] data,int width) {
		return byte2HexFormat(data, width, Configuration.byte2HexLower,Configuration.byte2ASCII);
	}
	
	/** 将byte[]数据以十六进制的形式显示，显示格式仿照UltraEdit*/
	private static String byte2HexFormat(byte[] data,int width,
			IByte2String hexPartitionMethod,IByte2String strPartitionMethod) 
	{
		StringBuffer sb = new StringBuffer();
		//表头
		for(int i=0;i<width;i++) {
			sb.append("-");
			if(i<10) sb.append(i);
			else sb.append((char)(i-10+'a'));
			sb.append("-");
		}
		sb.append("\r\n");
		int index = 0;
		StringBuffer hexPartionLine = new StringBuffer();
		StringBuffer strPartionLine = new StringBuffer();
		while(index < data.length) {
			int lineStart = index; 
			while(index < data.length && index < lineStart+width) {
				hexPartionLine.append(hexPartitionMethod.byte2String(data[index]))
									.append(Configuration.DELIMITER);
				strPartionLine.append(strPartitionMethod.byte2String(data[index]));
				index ++;
			}
			int placeholderNum = 0;
			while(index+placeholderNum < lineStart+width) {//对最后一行不足width的在HexPartition部分补充占位符
				hexPartionLine.append("   ");
				placeholderNum += 1;
			}
			sb.append(hexPartionLine).append(Configuration.PARTITION)
								.append(strPartionLine).append("\r\n");//完成一行显示
			hexPartionLine.delete(0, hexPartionLine.length());
			strPartionLine.delete(0, strPartionLine.length());
		}
		return sb.toString();
	}
	
	public static String b2HEX(byte[] data) {
		return byte2Hex(data, "", Configuration.byte2HexUpper);
	}
	
	public static String b2HEX(byte[] data,String delimiter) {
		return byte2Hex(data, delimiter, Configuration.byte2HexUpper);
	}
	
	public static String b2hex(byte[] data) {
		return byte2Hex(data, "", Configuration.byte2HexLower);
	}
	
	public static String b2hex(byte[] data,String delimiter) {
		return byte2Hex(data, "", Configuration.byte2HexLower);
	}
	
	private static String byte2Hex(byte[] data,String delimiter,IByte2String form) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		while(index < data.length) {
//			sb.append(form.byte2String(data[index])).append(delimiter);
			sb.append(delimiter).append(form.byte2String(data[index]));
			index ++;
		}
		return sb.toString();
	}

	public static byte[] hex2b(String hexStr) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(hexStr.length() % 2 != 0) {
			throw new IllegalArgumentException("输入参数"+hexStr+"长度不是偶数.");
		}
		int c = 0;
		while(c < hexStr.length()) {
			int high = Configuration.hexChar2Int(hexStr.charAt(c));
			int low = Configuration.hexChar2Int(hexStr.charAt(c+1));
			if(high == -1) {
				throw new IllegalArgumentException("输入参数"+hexStr+".charAt("+c+")="+hexStr.charAt(c)+"不是合法的HexChar");
			}
			if(low == -1) {
				throw new IllegalArgumentException("输入参数"+hexStr+".charAt("+(c+1)+")="+hexStr.charAt(c+1)+"不是合法的HexChar");
			}
			baos.write(Configuration.BCDPackedFormat(high,low));
			c += 2;
		}
		return baos.toByteArray();
	}
		
	static class Configuration {
		static final char[] HEX_UPPER_CASE = new char[]{
			'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
		};
	
		static final char[] HEX_LOWER_CASE = new char[]{
			'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
		};
		
		/**
		 * @return -1 表示输入参数hexChar不是个合法的HexChar. 其他情况返回的数据控制在[0,15]
		 * */
		static int hexChar2Int(char hexChar) {
			if(Character.isDigit(hexChar)) return hexChar - '0';
			else if(Character.isLowerCase(hexChar)) return hexChar - 'a' + 10;
			else if(Character.isUpperCase(hexChar)) return hexChar - 'A' + 10;
			else return -1;
		}
		
		static byte BCDPackedFormat(int high,int lower) {
			int min = Math.min(high, lower);
			int max = Math.max(high, lower);
			if(min<0 || max >15) {
				throw new IllegalArgumentException("输入参数"+high+","+lower+"都必须控制在范围[0,15]内");
			}
			return (byte)(high << 4 | lower);
		}
		
		/** 各个Byte之间的分割符号*/
		static final String DELIMITER = " ";
		
		/** Hex显示和字符显示区域分割符*/
		static final String PARTITION = ";";
		
		static IByte2String byte2HexUpper = new Byte2HexUpper();
		
		static IByte2String byte2HexLower = new Byte2HexLower();
		
		static IByte2String byte2ASCII = new Byte2ASCII();
		
	} 
	
	static interface IByte2String {
		public String byte2String(byte b); 
	}
	
	static class Byte2HexUpper implements IByte2String {
		public String byte2String(byte b) {
			int high = 0x0F & b>>4;
			int low  = 0x0F & b;
			return new String(new char[] {Configuration.HEX_UPPER_CASE[high],
								Configuration.HEX_UPPER_CASE[low]});
		}
	}
	
	static class Byte2HexLower implements IByte2String {
		public String byte2String(byte b) {
			int high = 0x0F & b>>4;
			int low  = 0x0F & b;
			return new String(new char[] {Configuration.HEX_LOWER_CASE[high],
							Configuration.HEX_LOWER_CASE[low]});
		}
	}
	
	static class Byte2ASCII implements IByte2String {
		private final String INVISIBLE_SUBSTITUTION = ".";
		
		public String byte2String(byte b) {
			return (Character.isWhitespace(b) || Character.isLetterOrDigit(b)) ? new String(new byte[]{b}) : INVISIBLE_SUBSTITUTION;
		}
	}
}
