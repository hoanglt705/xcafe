package com.s3s.ssm.license;

import java.security.NoSuchAlgorithmException;

public class LicenseGenerator {
  public static String generateSerialNumber(License license) throws NoSuchAlgorithmException {

    String serialNumberEncoded = calculateSecurityHash(license, "MD2")
            + calculateSecurityHash(license, "MD5") +
            calculateSecurityHash(license, "SHA1");
    String serialNumber = ""
            + serialNumberEncoded.charAt(32)
            + serialNumberEncoded.charAt(76)
            + serialNumberEncoded.charAt(100)
            + serialNumberEncoded.charAt(50)
            + "-"
            + serialNumberEncoded.charAt(2)
            + serialNumberEncoded.charAt(91)
            + serialNumberEncoded.charAt(73)
            + serialNumberEncoded.charAt(72)
            + serialNumberEncoded.charAt(98)
            + "-"
            + serialNumberEncoded.charAt(47)
            + serialNumberEncoded.charAt(65)
            + serialNumberEncoded.charAt(18)
            + serialNumberEncoded.charAt(85)
            + "-"
            + serialNumberEncoded.charAt(27)
            + serialNumberEncoded.charAt(53)
            + serialNumberEncoded.charAt(102)
            + serialNumberEncoded.charAt(15)
            + serialNumberEncoded.charAt(99);
    return serialNumber;
  }

  private static String calculateSecurityHash(License license, String algorithmName)
          throws java.security.NoSuchAlgorithmException {
    String hexMessageEncode = "";
    byte[] buffer = license.getData().getBytes();
    java.security.MessageDigest messageDigest =
            java.security.MessageDigest.getInstance(algorithmName);
    messageDigest.update(buffer);
    byte[] messageDigestBytes = messageDigest.digest();
    for (int index = 0; index < messageDigestBytes.length; index++) {
      int countEncode = messageDigestBytes[index] & 0xff;
      if (Integer.toHexString(countEncode).length() == 1)
        hexMessageEncode = hexMessageEncode + "0";
      hexMessageEncode = hexMessageEncode + Integer.toHexString(countEncode);
    }
    return hexMessageEncode;
  }
}
