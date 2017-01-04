package com.github.downgoon.jresty.commons.security;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.PasswordFinder;

/**
 * RSA Key Import/Export ( Converters among different forms )
 */
public class RsaKeyIO {

	static {
		
		/**
		 * key file format ".PEM" not supported in JCE default.
		 * but BouncyCastleProvider support it. 
		 * */
		
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}
	
	
	/**
	 * 1024 or 512 (flash player noly support 512 )
	 * http://crypto.hurlant.com/demo/
	 * */
	public static KeyPair genKeyPair512() throws Exception {
		return genKeyPair(512);
	}
	
	public static KeyPair genKeyPair1024() throws Exception {
		return genKeyPair(1024);
	}
	
	
	private static KeyPair genKeyPair(int keyLength) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keyLength); 
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}
	

	/**
	 * import JEC private key from the two big integers in hex string
	 * 
	 * @param modulusBigIntHex
	 *            big integer in Hex string
	 * 
	 * @param privateExponentBigIntHex
	 *            big integer in Hex string
	 * 
	 * @return JCE private key
	 * 
	 */
	public static RSAPrivateKey importPrivateFrBigint(String modulusBigIntHex, String privateExponentBigIntHex)
			throws Exception {
		int radix = 16;
		BigInteger n = new BigInteger(modulusBigIntHex, radix);
		BigInteger d = new BigInteger(privateExponentBigIntHex, radix);

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(n, d);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPrivateKey prikey = (RSAPrivateKey) keyFactory.generatePrivate(priKeySpec);
		return prikey;
	}

	/**
	 * import JEC public key from the two big integers in hex string
	 * 
	 * @param modulus
	 *            big integer in Hex string
	 * @param public
	 *            exponent big integer in Hex string
	 * @return JCE public key
	 */
	public static RSAPublicKey importPublicFrBigint(String modulusBigIntHex, String publicExponentBigIntHex)
			throws Exception {
		int radix = 16;
		BigInteger n = new BigInteger(modulusBigIntHex, radix);
		BigInteger e = new BigInteger(publicExponentBigIntHex, radix);

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(n, e);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);

		return pubKey;
	}

	/**
	 * export JCE public key into the two big integers in hex string
	 * 
	 * @param RSAPublicKey
	 *            defined in JCE
	 * @return new String[] { modulusBigIntHex, publicExponentBigIntHex };
	 */
	public static String[] exportPublicToBigint(PublicKey pubKey) {
		return new String[] { ((RSAPublicKey) pubKey).getModulus().toString(16),
				((RSAPublicKey) pubKey).getPublicExponent().toString(16) };

	}

	/**
	 * export JCE private key into the two big integers in hex string
	 * 
	 * @param PrivateKey
	 *            defined in JCE
	 * @return new String[] { modulusBigIntHex, privateExponentBigIntHex };
	 */
	public static String[] exportPrivateToBigint(PrivateKey priKey) {
		return new String[] { ((RSAPrivateKey) priKey).getModulus().toString(16),
				((RSAPrivateKey) priKey).getPrivateExponent().toString(16) };
	}

	/**
	 * import JCE public key from pem file
	 * 
	 * @param pubFilePem
	 *            public key file in pem format
	 * @return JCE Public Key
	 */
	public static PublicKey importPublicFrPem(File pubFilePem) throws Exception {
		PEMReader pemReader = null;
		try {
			FileReader fileReader = new FileReader(pubFilePem);
			pemReader = new PEMReader(fileReader);

			return (PublicKey) pemReader.readObject();

		} finally {

			if (pemReader != null) {
				pemReader.close();
			}
		}

	}

	/**
	 * export JCE public key into pem file
	 */
	public static void exportPublicToPem(PublicKey pubKey, String fileName) throws Exception {
		PEMWriter pemWriter = null;
		try {
			FileWriter fileWrite = new FileWriter(new File(fileName));
			pemWriter = new PEMWriter(fileWrite);
			pemWriter.writeObject(pubKey);
		} finally {
			pemWriter.close();
		}
	}

	/**
	 * import JCE private key from pem file
	 * 
	 * @param priFilePem
	 *            private key file in pem format
	 * @return JCE Private Key
	 */
	public static PrivateKey importPrivateFrPem(File priFilePem) throws Exception {

		PEMReader pemReader = null;
		try {
			FileReader fileReader = new FileReader(priFilePem);
			pemReader = new PEMReader(fileReader, new PasswordFinder() {

				@Override
				public char[] getPassword() {
					return "".toCharArray();
				}

			});

			KeyPair keyPair = (KeyPair) pemReader.readObject();
			pemReader.close();
			PrivateKey prik = keyPair.getPrivate();
			return prik;

		} finally {
			if (pemReader != null) {
				pemReader.close();
			}
		}

	}

	/**
	 * export JCE private key into pem file
	 */
	public static void exportPrivateToPem(PrivateKey priKey, String fileName) throws Exception {
		PEMWriter pemWriter = null;
		try {
			FileWriter fileWrite = new FileWriter(new File(fileName));
			pemWriter = new PEMWriter(fileWrite);
			pemWriter.writeObject(priKey);
		} finally {
			pemWriter.close();
		}
	}

}
