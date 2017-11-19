package com.s3s.ssm.license;

import java.security.NoSuchAlgorithmException;

public class LicenseValidator {
  public static boolean validate(License license, String serialNumber) throws NoSuchAlgorithmException {
    return serialNumber.equals(LicenseGenerator.generateSerialNumber(license));
  }
}
