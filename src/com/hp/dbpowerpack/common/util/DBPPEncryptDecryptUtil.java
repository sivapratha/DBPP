package com.hp.dbpowerpack.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.hp.dbpowerpack.common.exception.DBPPConfigException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * The Class DBPPEncryptDecryptUtil.
 */
public class DBPPEncryptDecryptUtil {

	/** The Constant ALGO. */
	private static final String ALGO = "AES";

	/** The Constant keyValue. */
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B',
			'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

	/** The Constant ITERATION_NUMBER. */
	private final static int ITERATION_NUMBER = 1000;

	/**
	 * Encrypt.
	 *
	 * @param Data the data
	 * @return the string
	 * @throws DBPPConfigException the dBPP config exception
	 */
	public static String encrypt(String Data) throws DBPPConfigException {
		Key key = generateKey();
		Cipher c;
		String encryptedValue = null;
		try {
			c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);

			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (NoSuchAlgorithmException e) {
			throw new DBPPConfigException(e);
		} catch (NoSuchPaddingException e) {
			throw new DBPPConfigException(e);
		} catch (InvalidKeyException e) {
			throw new DBPPConfigException(e);
		} catch (IllegalBlockSizeException e) {
			throw new DBPPConfigException(e);
		} catch (BadPaddingException e) {
			throw new DBPPConfigException(e);
		}

		return encryptedValue;
	}

	/**
	 * Decrypt.
	 *
	 * @param encryptedData the encrypted data
	 * @return the string
	 * @throws DBPPConfigException the dBPP config exception
	 */
	public static String decrypt(String encryptedData)
			throws DBPPConfigException {
		Key key = generateKey();
		Cipher c;
		String decryptedValue = null;
		try {
			c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder()
					.decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} catch (NoSuchAlgorithmException e) {
			throw new DBPPConfigException(e);
		} catch (NoSuchPaddingException e) {
			throw new DBPPConfigException(e);
		} catch (InvalidKeyException e) {
			throw new DBPPConfigException(e);
		} catch (IOException e) {
			throw new DBPPConfigException(e);
		} catch (IllegalBlockSizeException e) {
			throw new DBPPConfigException(e);
		} catch (BadPaddingException e) {
			throw new DBPPConfigException(e);
		}

		return decryptedValue;
	}

	/**
	 * Generate key.
	 *
	 * @return the key
	 */
	private static Key generateKey() {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

	/**
	 * Hash password.
	 *
	 * @param password the password
	 * @return the map
	 * @throws DBPPConfigException the dBPP config exception
	 */
	public static Map<String, String> hashPassword(String password)
			throws DBPPConfigException {
		// Uses a secure Random not a simple Random
		SecureRandom random;
		Map<String, String> returnMap = new HashMap<String, String>();
		String sDigest = "";
		String sSalt = "";
		try {
			random = SecureRandom.getInstance("SHA1PRNG");

			// Salt generation 64 bits long
			byte[] bSalt = new byte[8];
			random.nextBytes(bSalt);
			// Digest computation
			byte[] bDigest = getHash(ITERATION_NUMBER, password, bSalt);
			sDigest = byteToBase64(bDigest);
			sSalt = byteToBase64(bSalt);

		} catch (NoSuchAlgorithmException e) {
			throw new DBPPConfigException(e);
		}
		returnMap.put("PASSWORD", sDigest);
		returnMap.put("SALT", sSalt);

		return returnMap;
	}

	/**
	 * From a password, a number of iterations and a salt, returns the
	 * corresponding digest.
	 * 
	 * @param iterationNb
	 *            int The number of iterations of the algorithm
	 * @param password
	 *            String The password to encrypt
	 * @param salt
	 *            byte[] The salt
	 * @return byte[] The digested password
	 * @throws NoSuchAlgorithmException
	 *             If the algorithm doesn't exist
	 */
	public static byte[] getHash(int iterationNb, String password, byte[] salt)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.reset();
		digest.update(salt);
		byte[] input;
		try {
			input = digest.digest(password.getBytes("UTF-8"));
			for (int i = 0; i < iterationNb; i++) {
				digest.reset();
				input = digest.digest(input);
			}

		} catch (UnsupportedEncodingException e) {
			input = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * From a base 64 representation, returns the corresponding byte[].
	 * 
	 * @param data
	 *            String The base64 representation
	 * @return byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] base64ToByte(String data) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(data);
	}

	/**
	 * From a byte[] returns a base 64 representation.
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 */
	public static String byteToBase64(byte[] data) {
		BASE64Encoder endecoder = new BASE64Encoder();
		return endecoder.encode(data);
	}

	/**
	 * Authenticates the user with a given login and password If password and/or
	 * login is null then always returns false. If the user does not exist in
	 * the database returns false.
	 *
	 * @param password String The password of the user
	 * @param digest the digest
	 * @param salt the salt
	 * @return boolean Returns true if the user is authenticated, false
	 * otherwise
	 * @throws DBPPConfigException the dBPP config exception
	 */
	public static boolean authenticate(String password, String digest,
			String salt) throws DBPPConfigException {
		boolean authenticated = false;
		try {
			
			if(digest!=null && salt!=null){
				byte[] bDigest = base64ToByte(digest);
				byte[] bSalt = base64ToByte(salt);
				// Compute the new DIGEST
				byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);

				authenticated = Arrays.equals(proposedDigest, bDigest);
			} else {
				if("".equals(password)){
					authenticated = true;
				}
			}

			
		} catch (NoSuchAlgorithmException ex) {	
			throw new DBPPConfigException(ex);
		} catch (IOException io) {
			throw new DBPPConfigException(io);
		}

		return authenticated;
	}

}
