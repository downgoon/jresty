package io.downgoon.jresty.commons.security000;

//import org.apache.commons.codec.DecoderException;
//import org.apache.commons.codec.binary.Hex;

public class HexCodec {
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder()).append(hexDigits[d1]).append(hexDigits[d2]).toString();
	}
	
//	/**
//	 * 十六进制编码串解码成二进制
//	 * */
//	public static byte[] hexs2bytes(String hexstrData) {
//		try {
//			return Hex.decodeHex(hexstrData.toCharArray());
//		}catch(DecoderException de) {
//			throw new IllegalArgumentException(de);
//		}
//		
//	}
	
}
