package io.downgoon.jresty.commons.security000;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public abstract class Coder {
	
	/** 字节编码(hex或base64)*/
	public enum ByteEncodeWay {
		hex,base64
	}
	
	public static final String KEY_ALGORITHM = "RSA";

	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decode(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encode(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);
	}
	

	/**
	 * 二进制数据编码成十六进制串
	 * */
	public static String bytes2hexs(byte[] bytesData) {
		return HexCodec.byteArrayToHexString(bytesData);
	}
	
	public static void main(String argsp[]) throws Exception{
		System.out.println(new String(decryptBASE64("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIiRKkGxIfx6slAJekX0Qtfk+1/X1xjjhG492knnYfSzSP8hgPAZCfDebQMBprbBluhrsLyhhTXtKOiAE3xIUtz/E41YT8m2nrfN7rSFP/9O40H3u2jQKJTR0xV0+U9kFNTO25GEseEBu69yR2Oi15qwJyBIufl/B6Wr+10f/yZrAgMBAAECgYAiY6OnbSpowKDcRLa0nOCkKvYd2WVB0DEN7adlSs3A59Cu5knoq/Vz4R7ETgskJDWTC2My7N11ieXuPiop9rBesaqQkm1w3PcfyVfL7BVpcM7lH4+LRV6SQnJ4nFozQ+gFvZeRaQWvaymXaTirRydDNCBbrnOSzfXSejJB2geWCQJBAPyajDBAI3aa8ZlpWeK6Y7MqJEfZ8esmbOwgF8karq1w9J6bH4Px0fizRr7mmiKGUBzebJZLNEIoSwRKuFLzM28CQQCKZzlDxBWXXWiI0kaMWX+HDjPBBunvc17fZ8VNYw//S6GuBVvbkNEcx6bQohIeAey4PbzaK9yfQemlvVUEP47FAkAxcKI9k2Adh6f/ycHhAXAgEgB1269gwe2RrZpaLxG/Opt2K5BYh8z760LOfF3woe8uJ06DgAajaDV+io1XFuibAkB3eZAnfZed6NaaddrMwV2jOpFWqh87w348oJnjqgZfPey+CoY3ThYWIAtSwvWczdCJY1Ipi/+RwxZtKrm/RxZ5AkEA23a+pA6OwuWycox3/c2FNcZQKq8qwMgOg7qgB0tdpT1juzVXaD6TBAyRtnjnPk0fqrS74odK1My9NuHqtapfGQ==")));
	}
}
