package com.s3s.ssm.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:properties/database.properties")
public class SystemServiceImpl implements ISystemService {
  private final static String MYSQL_EXE_PATH = "";
  private final static String BACKUP_PATH = "";

  @Autowired
  Environment env;

  @Override
  public byte[] backupDatabase() {
    String username = env.getProperty("jdbc.username");
    String password = env.getProperty("jdbc.password");
    String host = "localhost";
    String port = "3306";
    String database = "ssmv0.1";
    return backupDataWithOutDatabase(MYSQL_EXE_PATH, host, port, username, password, database, BACKUP_PATH);
  }

  @Override
  public void restoreDatabase(byte[] fileInByte) {

  }

  public byte[] backupDataWithOutDatabase(String dumpExePath, String host, String port, String user,
          String password, String database, String backupPath) {
    boolean status = false;
    try {
      Process p = null;

      DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
      Date date = new Date();
      String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

      String batchCommand = "";
      if (password != "") {
        // Backup with database
        batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password="
                + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath
                + "\"";
      } else {
        batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user
                + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
      }

      Runtime runtime = Runtime.getRuntime();
      p = runtime.exec(batchCommand);
      int processComplete = p.waitFor();

      if (processComplete == 0) {
        status = true;
      } else {
        status = false;
      }
      File backupFile = new File(backupPath + filepath);
      byte[] mybytearray = new byte[(int) backupFile.length()];
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(backupFile));
      bis.read(mybytearray, 0, mybytearray.length);
      return mybytearray;
    } catch (IOException | InterruptedException ioe) {
      return null;
    }
  }
}
