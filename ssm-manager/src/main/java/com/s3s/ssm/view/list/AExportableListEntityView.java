package com.s3s.ssm.view.list;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.jsefa.common.lowlevel.filter.HeaderAndFooterFilter;
import net.sf.jsefa.csv.CsvDeserializer;
import net.sf.jsefa.csv.CsvIOFactory;
import net.sf.jsefa.csv.CsvSerializer;
import net.sf.jsefa.csv.config.CsvConfiguration;

import com.s3s.ssm.dto.IActiveObject;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.export.ExportHandler;

import de.javasoft.swing.DetailsDialog;
import de.javasoft.swing.ExtendedFileChooser;

public abstract class AExportableListEntityView<T extends IActiveObject, K> extends
        AListServiceView<T> {
  private static final long serialVersionUID = 1L;
  private static final FileNameExtensionFilter CSV_FILTER = new FileNameExtensionFilter("CSV", "csv");
  protected JButton btnExport;
  protected JButton btnImport;

  public AExportableListEntityView(Icon icon, String label) {
    super(icon, label);
  }

  public AExportableListEntityView() {
    super();
  }

  protected void setVisibleExportButton(boolean visible) {
    btnExport.setVisible(visible);
  }

  protected void setVisibleImportButton(boolean visible) {
    btnImport.setVisible(visible);
  }

  @Override
  protected JToolBar createButtonToolBar() {
    btnExport = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.EXPORT_ICON));
    btnExport.setText(getMessage("default.button.export"));
    btnExport.setName("btnExport");
    btnExport.addActionListener(e -> {
      List<Object> exportData = new ArrayList<Object>();
      loadData(0, Integer.MAX_VALUE).forEach(entity -> {
        exportData.add(transferToExportData(entity));
      });
      ExportHandler exportHandler = new ExportHandler(getExportableClass(), translateHeader(exportHeader()),
              exportData);
      exportHandler.performExportAction();
    });

    btnImport = new JButton(IziImageUtils.getSmallIcon(IziImageConstants.IMPORT_ICON));
    btnImport.setText(getMessage("default.button.import"));
    btnImport.setName("btnImport");
    btnImport.addActionListener(e -> {
      performImportAction();
    });
    JToolBar buttonToolBar = super.createButtonToolBar();
    buttonToolBar.add(btnExport);
    buttonToolBar.add(btnImport);
    return buttonToolBar;
  }

  private List<String> translateHeader(List<String> fields) {
    List<String> translatedHeaders = new ArrayList<String>();
    for (String field : fields) {
      translatedHeaders.add(getMessage("label." + getGenericClass().getSimpleName() + "." + field));
    }
    return translatedHeaders;
  }

  protected CsvSerializer getCsvSerializer() {
    return CsvIOFactory.createFactory(getExportableClass()).createSerializer();
  }

  private void performImportAction() {
    ExtendedFileChooser chooser = new ExtendedFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.addChoosableFileFilter(CSV_FILTER);
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION
            && chooser.getSelectedFile() != null) {
      CsvConfiguration config = new CsvConfiguration();
      // header of size 1, no footer, store the filtered lines
      config.setLineFilter(new HeaderAndFooterFilter(1, false, true));
      CsvDeserializer deserializer = CsvIOFactory.createFactory(config, getExportableClass())
              .createDeserializer();
      try (FileReader fileReader = new FileReader(chooser.getSelectedFile());) {
        deserializer.open(fileReader);
        while (deserializer.hasNext()) {
          T entity = transferToImportData(deserializer.next());
          if (entity != null) {
            saveOrUpdate(entity);
          }
        }
        refreshData(0);
      } catch (IOException e) {
        DetailsDialog.showDialog(SwingUtilities.getWindowAncestor(this), null, null, e);
      } finally {
        deserializer.close(true);
      }
    }
  }

  protected abstract K transferToExportData(T entity);

  protected abstract T transferToImportData(K exportDto);

  protected abstract List<String> exportHeader();

  @SuppressWarnings("unchecked")
  protected Class<K> getExportableClass() {
    Type controllerType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    return (Class<K>) controllerType;
  }

}
