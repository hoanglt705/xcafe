package com.s3s.ssm.view.printer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.s3s.ssm.service.AbstractViewService;

public class PrinterServiceImpl extends AbstractViewService<PrinterDto> {

  private static final String LOCAL_SETTING = "C:\\Users\\HoangLe\\address.ser";

  @Override
  public void saveOrUpdate(PrinterDto dto) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LOCAL_SETTING));) {
      oos.writeObject(dto);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  @Override
  public PrinterDto findOne(Long id) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(LOCAL_SETTING))) {
      return (PrinterDto) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      return null;
    }
  }
}
