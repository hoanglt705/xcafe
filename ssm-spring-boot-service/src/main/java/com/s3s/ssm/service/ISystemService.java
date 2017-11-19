package com.s3s.ssm.service;

public interface ISystemService {

  byte[] backupDatabase();

  void restoreDatabase(byte[] fileInByte);

}
