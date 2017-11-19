package com.s3s.ssm.util.export;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.jsefa.csv.CsvIOFactory;
import net.sf.jsefa.csv.CsvSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import de.javasoft.swing.DetailsDialog;
import de.javasoft.swing.ExtendedFileChooser;

@SuppressWarnings("rawtypes")
public class ExportHandler {
  private static final FileNameExtensionFilter CSV_FILTER = new FileNameExtensionFilter("CSV", "csv");
  private Class clazz;
  List<String> exportHeaders;
  List<Object> data;

  public ExportHandler(Class clazz, List<String> exportHeaders, List data) {
    this.clazz = clazz;
    this.exportHeaders = exportHeaders;
    this.data = data;
  }

  public void performExportAction() {
    ExtendedFileChooser chooser = new ExtendedFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.addChoosableFileFilter(CSV_FILTER);
    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION
            && chooser.getSelectedFile() != null) {
      OutputStreamWriter outputStreamWriter = null;
      try {
        String canonicalPath = chooser.getSelectedFile().getCanonicalPath();
        String csvExtension = CSV_FILTER.getExtensions()[0];
        String path = canonicalPath.endsWith(csvExtension) ? canonicalPath : canonicalPath + "."
                + csvExtension;
        CsvSerializer serializer = getCsvSerializer();
        File file = new File(path);
        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        serializer.open(outputStreamWriter);
        generateHeader(serializer);
        data.forEach(entity -> {
          serializer.write(entity);
        });
        serializer.close(true);
        Desktop.getDesktop().open(file);
      } catch (IOException e) {
        DetailsDialog.showDialog(SwingUtilities.getWindowAncestor(null), null, null, e);
      } finally {
        IOUtils.closeQuietly(outputStreamWriter);
      }
    }
  }

  protected CsvSerializer getCsvSerializer() {
    return CsvIOFactory.createFactory(clazz).createSerializer();
  }

  private void generateHeader(CsvSerializer serializer) {
    StringBuilder header = new StringBuilder();
    for (String field : exportHeaders) {
      if (!StringUtils.isEmpty(field)) {
        header.append(field).append(";");
      } else {
        header.append(";");
      }
    }
    serializer.getLowLevelSerializer().writeLine(StringUtils.chop(header.toString()));
  }
}
