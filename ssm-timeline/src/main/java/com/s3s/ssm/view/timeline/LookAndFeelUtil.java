package com.s3s.ssm.view.timeline;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


/**
 * Allows to install available look and feels.
 * 
 * @author kvg
 * @since 29.08.2006
 */
public final class LookAndFeelUtil {
  /** The Xpert.ivy Logger */
  /** Marker if licence has been installed */
  private static boolean fSyntheticaLicenceIsInstalled = false;

  /**
   * Enumeration of all 3rd-party look and feel vendors.
   * 
   * @author kvg
   */
  public static enum Vendor {
    /** JGoodies */
    JGOODIES("JGoodies"),
    /** JGoodies */
    JAVA("Sun Java"),
    /** Synthetica */
    SYNTHETICA("Synthetica"),

    XPERTLINE("XpertLine");

    /** The vendor name */
    private String fName;

    /**
     * Create a new vendor constant
     * 
     * @param name
     *          the vendor name
     */
    private Vendor(String name) {
      fName = name;
    }

    /** @return the name of the vendor */
    @Override
    public String toString() {
      return fName;
    }
  }

  /**
   * Enumeration of all supported 3rd-party look and feels.
   * 
   * @author kvg
   */
  public static enum LookAndFeel {
    /** JGoodies plastic look and feel */
    JAVA_WINDOWS(Vendor.JAVA, "Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
    /** JGoodies plastic look and feel */
    JAVA_WINDOWS_CLASSIC(Vendor.JAVA, "Windows Classic",
            "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"),
    /** JGoodies plastic look and feel */
    JAVA_METAL(Vendor.JAVA, "Metal", "javax.swing.plaf.metal.MetalLookAndFeel"),
    /** JGoodies plastic look and feel */
    JAVA_MOTIF(Vendor.JAVA, "Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"),

    /** JGoodies plastic look and feel */
    JGOODIES_PLASTIC(Vendor.JGOODIES, "Plastic", "com.jgoodies.plaf.plastic.PlasticLookAndFeel"),
    /** JGoodies plastic 3d look and feel */
    JGOODIES_PLASTIC_3D(Vendor.JGOODIES, "Plastic 3D", "com.jgoodies.plaf.plastic.Plastic3DLookAndFeel"),
    /** JGoodies plastic xp look and feel */
    JGOODIES_PLASTIC_XP(Vendor.JGOODIES, "Plastic XP", "com.jgoodies.plaf.plastic.PlasticXPLookAndFeel"),
    /** JGoodies windows look and feel */
    JGOODIES_WINDOWS(Vendor.JGOODIES, "Windows", "com.jgoodies.looks.windows.WindowsLookAndFeel"),

    /** Synthetica standard look and feel */
    SYNTHETICA_STANDARD(Vendor.SYNTHETICA, "Standard",
            "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel"),
    /** Synthetica blue moon look and feel */
    SYNTHETICA_BLUE_MOON(Vendor.SYNTHETICA, "Blue Moon",
            "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel"),
    /** Synthetica blue steel look and feel */
    SYNTHETICA_BLUE_STEEL(Vendor.SYNTHETICA, "Blue Steel",
            "de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel"),
    /** Synthetica blue ice look and feel */
    SYNTHETICA_BLUE_ICE(Vendor.SYNTHETICA, "Blue Ice",
            "de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel"),
    /** Synthetica black eye look and feel */
    SYNTHETICA_BLACK_EYE(Vendor.SYNTHETICA, "Black Eye",
            "de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel"),
    /** Synthetica black moon look and feel */
    SYNTHETICA_BLACK_MOON(Vendor.SYNTHETICA, "Black Moon",
            "de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel"),
    /** Synthetica black star look and feel */
    SYNTHETICA_BLACK_STAR(Vendor.SYNTHETICA, "Black Star",
            "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"),
    /** Synthetica green dream look and feel */
    SYNTHETICA_GREEN_DREAM(Vendor.SYNTHETICA, "Green Dream",
            "de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel"),
    /** Synthetica silver moon look and feel */
    SYNTHETICA_SILVER_MOON(Vendor.SYNTHETICA, "Silver Moon",
            "de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel"),
    /** Synthetica orange metallic look and feel */
    SYNTHETICA_ORANGE_METALLIC(Vendor.SYNTHETICA, "Orange Metallic",
            "de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel"),
    /** Synthetica mauve metallic look and feel */
    SYNTHETICA_MAUVE_METALLIC(Vendor.SYNTHETICA, "Mauve Metallic",
            "de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel"),
    /** Synthetica sky metallic look and feel */
    SYNTHETICA_SKY_METALLIC(Vendor.SYNTHETICA, "Sky Metallic",
            "de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel"),
    /** Synthetica white vision look and feel */
    SYNTHETICA_WHITE_VISION(Vendor.SYNTHETICA, "White Vision",
            "de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel"),
    /** Synthetica simple 2d look and feel */
    SYNTHETICA_SIMPLE_2D(Vendor.SYNTHETICA, "Simple 2D",
            "de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel"),

    XPERTLINE_DEFAULT(Vendor.XPERTLINE, "XpertLine Standard",
            "ch.xpertline.plaf.XpertLineStandardLookAndFeel");

    /** the LF vendor */
    private Vendor fVendor;
    /** the LF info (used by UIManager) */
    private LookAndFeelInfo fInfo;

    /**
     * Create a new LookAndFeel constant.
     * 
     * @param vendor
     * @param name
     * @param lfClassName
     */
    private LookAndFeel(Vendor vendor, String name, String lfClassName) {
      fVendor = vendor;
      fInfo = new LookAndFeelInfo(fVendor + " " + name, lfClassName);
    }

    /** @return vendor + name */
    @Override
    public String toString() {
      return getName() + " LookAndFeel";
    }

    /** @return he name of the Look and Feel (including the vendor) */
    public String getName() {
      return fInfo.getName();
    }

    /** @return the vendor of the Look and Feel */
    public Vendor getVendor() {
      return fVendor;
    }

    /** @return the look and feel info (name and class name) */
    public LookAndFeelInfo getInfo() {
      return fInfo;
    }
  }

  /** Private constructor. Do not instantiate. */
  private LookAndFeelUtil() {
  }

  /**
   * Installs the IvyTeam AG synthetica licence (must happen prior to installation of synthetica LF).
   */
  public static void installSyntheticaLicence() {
    String[] li = { "Licensee=ivyTeam AG", "LicenseRegistrationNumber=95305555", "Product=Synthetica",
            "LicenseType=Enterprise Site License", "ExpireDate=--.--.----", "MaxVersion=2.999.999" };
    UIManager.put("Synthetica.license.info", li);
    UIManager.put("Synthetica.license.key", "226C1B38-8B29A6BE-96BAD70F-36ADF164-2FC08ED3");

    String[] li2 = { "Licensee=ivyTeam AG", "LicenseRegistrationNumber=126652935",
            "Product=SyntheticaAddons", "LicenseType=Enterprise Site License", "ExpireDate=--.--.----",
            "MaxVersion=1.999.999" };
    UIManager.put("SyntheticaAddons.license.info", li2);
    UIManager.put("SyntheticaAddons.license.key", "F10498CE-6E2D84FE-E9EFD631-29045325-253BAF97");

    fSyntheticaLicenceIsInstalled = true;
  }

  /**
   * @param lf
   *          a look and feel enum constant
   * @return true, if the lf specified by given lfInfo is already installed
   * @see LookAndFeelUtil.LookAndFeel
   */
  private static boolean isInstalledLF(LookAndFeel lf) {
    for (LookAndFeelInfo lfInfo : UIManager.getInstalledLookAndFeels()) {
      if (lf.getInfo().getClassName().equals(lfInfo.getClassName()))
        return true;
    }
    return false;
  }

  /**
   * Installs the LookAndFeel of the given LookAndFeel info, if not installed yet.
   * 
   * @param lf
   *          the LF to install
   * @return true if given LF is installed (or was installed already)
   */
  public static boolean installLF(LookAndFeel lf) {
    if (!isInstalledLF(lf)) {
      if (!fSyntheticaLicenceIsInstalled) {
        installSyntheticaLicence();
      }

      try {
        UIManager.installLookAndFeel(lf.getInfo());
        return true;
      } catch (Exception ex) {
        return false;
      }
    }
    return true;
  }

  /**
   * Activates (i.e. sets) the LF described by the given lfInfo. If LF is not installed yet, it will be
   * installed prior to activation.
   * 
   * @param lf
   *          the lf to set
   * @return true if LF has been set active
   */
  public static boolean setLF(LookAndFeel lf) {
    boolean isInstalled = installLF(lf);
    if (isInstalled) {
      try {
        UIManager.setLookAndFeel(lf.getInfo().getClassName());
        return true;
      } catch (Exception ex) {
      }
    }
    return false;
  }

  /**
   * Installs all LookAndFeels of the given vendor
   * 
   * @param vendor
   *          a supported vendor
   * @return number of installed look and feels
   * @see LookAndFeelUtil.Vendor
   */
  public static int installAllLF(Vendor vendor) {
    int count = 0;
    for (LookAndFeel lf : LookAndFeel.values()) {
      if (lf.getVendor().equals(vendor)) {
        if (installLF(lf)) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * For testing purposes only.
   * 
   * @param args
   */
  public static void main(String[] args) {
  }

}
