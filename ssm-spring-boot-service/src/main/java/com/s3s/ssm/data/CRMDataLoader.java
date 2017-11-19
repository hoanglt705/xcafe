package com.s3s.ssm.data;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.s3s.crm.repo.ConfigRepository;
import com.s3s.crm.repo.CrmProductRepository;
import com.s3s.crm.repo.CustomerCardRepository;
import com.s3s.crm.repo.CustomerRepository;
import com.s3s.crm.repo.CustomerTypeRepository;
import com.s3s.crm.repo.InternalMaterialRepository;
import com.s3s.crm.repo.MaterialTypeRepository;
import com.s3s.crm.repo.PromotionRepository;
import com.s3s.crm.repo.ShapeRepository;
import com.s3s.crm.repo.SizeRepository;
import com.s3s.ssm.config.CrmServerContextProvider;
import com.sunrise.xdoc.entity.crm.Config;
import com.sunrise.xdoc.entity.crm.CrmProduct;
import com.sunrise.xdoc.entity.crm.Customer;
import com.sunrise.xdoc.entity.crm.CustomerCard;
import com.sunrise.xdoc.entity.crm.CustomerType;
import com.sunrise.xdoc.entity.crm.InternalMaterial;
import com.sunrise.xdoc.entity.crm.MaterialType;
import com.sunrise.xdoc.entity.crm.Promotion;
import com.sunrise.xdoc.entity.crm.Shape;
import com.sunrise.xdoc.entity.crm.Size;

public class CRMDataLoader {
  @SuppressWarnings("deprecation")
  private final static AnnotationConfigApplicationContext applicationContext = CrmServerContextProvider
          .getInstance().getApplicationContext();
  private static Log logger = LogFactory.getLog(CRMDataLoader.class);
  private static Properties prop;
  private static Size size20;
  private static Shape tron;
  private static InternalMaterial socola;
  private static MaterialType kemsua;
  private static CustomerType binh_thuong;
  private static CustomerType than_thiet;
  private static CustomerType thanhvien;
  private static CustomerType vip;
  private static Customer khach_than_thiet;

  static {
    File file = new File(CRMDataLoader.class.getClassLoader().getResource("testdata/data.properties")
            .getPath());
    prop = new Properties();
    try (Reader reader = Files.newReader(file, Charsets.UTF_8)) {
      prop.load(reader);
    } catch (IOException e) {
      // Do something
    }
  }

  public static void main(String[] args) {
    // Not find solution to get class path from ssm-core.
    DOMConfigurator.configure("src/main/resources/log4j.xml");
    logger.info("Starting data loader SSM...");
    Locale.setDefault(new Locale("vi"));
    try {
      logger.info("Initializing data SSM");
      initBasicData();

      logger.info("Finished data loader SSM");
      System.exit(0);
    } catch (Exception e) {
      logger.error("Error when init data, please check and clean test data", e);
      System.exit(0);
    }
  }

  public static void initBasicData() {
    initConfig();
    initCustomerType();
    initCustomer();
    initCustomerCard();
    initMaterialType();
    initSize();
    initShape();
    initInternalMaterial();
    initCake();
    initPromotion();
  }

  private static void initPromotion() {
    Calendar calendar = Calendar.getInstance();
    Date fromDate = DateUtils.setDays(calendar.getTime(), 1);
    Date toDate = DateUtils.setDays(calendar.getTime(), 28);
    Promotion promotion8_3 = new Promotion();
    promotion8_3.setCode("thanh_lap_cong_ty");
    promotion8_3.setTitle("Ki niem thanh lap cong ty");
    promotion8_3.setFromDate(fromDate);
    promotion8_3.setToDate(toDate);
    promotion8_3.setPercentDiscount(5);
    promotion8_3.setContent("<html dir='ltr'><head></head><body contenteditable='true'><p>"
            + "<font face='Segoe UI'>Chao mung</font></p></body></html>");
    applicationContext.getBean(PromotionRepository.class).save(promotion8_3);
  }

  private static void initCake() {
    CrmProduct cake1 = new CrmProduct();
    cake1.setCode("cake1");
    cake1.setName("cake1");
    cake1.setSize(size20);
    cake1.setShape(tron);
    cake1.setMaterialType(kemsua);
    cake1.setInternalMaterial(socola);
    cake1.setSellPrice(200_000l);
    cake1.setTag("20cm-tron-kem_sua-socola");
    applicationContext.getBean(CrmProductRepository.class).save(cake1);
  }

  private static void initInternalMaterial() {
    socola = new InternalMaterial();
    socola.setCode("socola");
    socola.setName("Socola");
    applicationContext.getBean(InternalMaterialRepository.class).save(socola);

    InternalMaterial ca_phe = new InternalMaterial();
    ca_phe.setCode("ca_phe");
    ca_phe.setName("Ca phe");
    applicationContext.getBean(InternalMaterialRepository.class).save(ca_phe);

    InternalMaterial tra_xanh = new InternalMaterial();
    tra_xanh.setCode("tra_xanh");
    tra_xanh.setName("Tra xanh");
    applicationContext.getBean(InternalMaterialRepository.class).save(tra_xanh);

    InternalMaterial nho_kho = new InternalMaterial();
    nho_kho.setCode("nho_kho");
    nho_kho.setName("Nho kho");
    applicationContext.getBean(InternalMaterialRepository.class).save(nho_kho);

  }

  private static void initShape() {
    tron = new Shape();
    tron.setCode("tron");
    tron.setName("Tron");
    applicationContext.getBean(ShapeRepository.class).save(tron);

    Shape tim = new Shape();
    tim.setCode("tim");
    tim.setName("Tim");
    applicationContext.getBean(ShapeRepository.class).save(tim);

    Shape vuong = new Shape();
    vuong.setCode("vuong");
    vuong.setName("Vuong");
    applicationContext.getBean(ShapeRepository.class).save(vuong);

    Shape hcn = new Shape();
    hcn.setCode("hcn");
    hcn.setName("Hinh chu nhat");
    applicationContext.getBean(ShapeRepository.class).save(hcn);

    Shape taohinh = new Shape();
    taohinh.setCode("taohinh");
    taohinh.setName("Tao hinh");
    applicationContext.getBean(ShapeRepository.class).save(taohinh);
  }

  private static void initSize() {
    size20 = new Size();
    size20.setCode("20cm");
    size20.setName("20cm");
    applicationContext.getBean(SizeRepository.class).save(size20);

    Size size23 = new Size();
    size23.setCode("23cm");
    size23.setName("23cm");
    applicationContext.getBean(SizeRepository.class).save(size23);

    Size size25 = new Size();
    size25.setCode("25cm");
    size25.setName("25cm");
    applicationContext.getBean(SizeRepository.class).save(size25);

    Size size27 = new Size();
    size27.setCode("27cm");
    size27.setName("27cm");
    applicationContext.getBean(SizeRepository.class).save(size27);

    Size size29 = new Size();
    size29.setCode("29cm");
    size29.setName("29cm");
    applicationContext.getBean(SizeRepository.class).save(size29);

    Size size30 = new Size();
    size30.setCode("30cm");
    size30.setName("30cm");
    applicationContext.getBean(SizeRepository.class).save(size30);

    Size size40 = new Size();
    size40.setCode("40cm");
    size40.setName("40cm");
    applicationContext.getBean(SizeRepository.class).save(size40);

    Size size22_32 = new Size();
    size22_32.setCode("22*32cm");
    size22_32.setName("22*32cm");
    applicationContext.getBean(SizeRepository.class).save(size22_32);

    Size size26_34 = new Size();
    size26_34.setCode("26*34cm");
    size26_34.setName("26*34cm");
    applicationContext.getBean(SizeRepository.class).save(size26_34);
  }

  private static void initMaterialType() {
    kemsua = new MaterialType();
    kemsua.setCode("kem_sua");
    kemsua.setName("Kem sua");
    applicationContext.getBean(MaterialTypeRepository.class).save(kemsua);

    MaterialType socola = new MaterialType();
    socola.setCode("socola");
    socola.setName("Socola");
    applicationContext.getBean(MaterialTypeRepository.class).save(socola);

    MaterialType coffee = new MaterialType();
    coffee.setCode("coffee");
    coffee.setName("Coffee");
    applicationContext.getBean(MaterialTypeRepository.class).save(coffee);

    MaterialType traxanh = new MaterialType();
    traxanh.setCode("tra_xanh");
    traxanh.setName("Tra xanh");
    applicationContext.getBean(MaterialTypeRepository.class).save(traxanh);

    MaterialType dau = new MaterialType();
    dau.setCode("dau");
    dau.setName("Dau");
    applicationContext.getBean(MaterialTypeRepository.class).save(dau);

    MaterialType saurieng = new MaterialType();
    saurieng.setCode("sau_rieng");
    saurieng.setName("Sau rieng");
    applicationContext.getBean(MaterialTypeRepository.class).save(saurieng);
  }

  private static void initCustomerCard() {
    CustomerCard customerCard = new CustomerCard();
    customerCard.setCode("customerCard");
    customerCard.setCreatedDate(Calendar.getInstance().getTime());
    customerCard.setCustomer(khach_than_thiet);
    applicationContext.getBean(CustomerCardRepository.class).save(customerCard);
  }

  private static void initCustomer() {
    Customer khach_binh_thuong = new Customer();
    khach_binh_thuong.setCode("khach_binh_thuong");
    khach_binh_thuong.setName("Khach binh thuong");
    khach_binh_thuong.setPhone("097876543");
    khach_binh_thuong.setBirthday(Calendar.getInstance().getTime());
    khach_binh_thuong.setEmail("hoanglt705@gmail.com");
    khach_binh_thuong.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
    khach_binh_thuong.setCustomerType(binh_thuong);
    khach_binh_thuong.setPoint(5);
    applicationContext.getBean(CustomerRepository.class).save(khach_binh_thuong);

    khach_than_thiet = new Customer();
    khach_than_thiet.setCode("khach_than_thiet");
    khach_than_thiet.setName("Khach than thiet");
    khach_than_thiet.setPhone("097876543");
    khach_than_thiet.setBirthday(Calendar.getInstance().getTime());
    khach_than_thiet.setEmail("hoanglt705@gmail.com");
    khach_than_thiet.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
    khach_than_thiet.setCustomerType(than_thiet);
    khach_than_thiet.setPoint(11);
    applicationContext.getBean(CustomerRepository.class).save(khach_than_thiet);

    Customer khach_thanh_vien = new Customer();
    khach_thanh_vien.setCode("khach_thanh_vien");
    khach_thanh_vien.setName("Khach thanh vien");
    khach_thanh_vien.setPhone("097876543");
    khach_thanh_vien.setBirthday(Calendar.getInstance().getTime());
    khach_thanh_vien.setEmail("hoanglt705@gmail.com");
    khach_thanh_vien.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
    khach_thanh_vien.setCustomerType(thanhvien);
    khach_thanh_vien.setPoint(91);
    applicationContext.getBean(CustomerRepository.class).save(khach_thanh_vien);

    Customer khach_vip = new Customer();
    khach_vip.setCode("khach_vip");
    khach_vip.setName("Khach vip");
    khach_vip.setPhone("097876543");
    khach_vip.setBirthday(Calendar.getInstance().getTime());
    khach_vip.setEmail("hoanglt705@gmail.com");
    khach_vip.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
    khach_vip.setCustomerType(vip);
    khach_vip.setPoint(120);
    applicationContext.getBean(CustomerRepository.class).save(khach_vip);
  }

  private static void initCustomerType() {
    binh_thuong = new CustomerType();
    binh_thuong.setCode("binh_thuong");
    binh_thuong.setName("Binh_thuong");
    binh_thuong.setDiscountPercent(0);
    binh_thuong.setMinPoint(0);
    binh_thuong.setEffectiveTime(0);
    binh_thuong.setPointToRemind(0);

    than_thiet = new CustomerType();
    than_thiet.setCode("than_thiet");
    than_thiet.setName("Than thiet");
    than_thiet.setDiscountPercent(10);
    than_thiet.setMinPoint(10);
    than_thiet.setEffectiveTime(12);
    than_thiet.setPointToRemind(8);

    thanhvien = new CustomerType();
    thanhvien.setCode("thanh_vien");
    thanhvien.setName("Thanh vien");
    thanhvien.setDiscountPercent(15);
    thanhvien.setMinPoint(50);
    thanhvien.setEffectiveTime(12);
    thanhvien.setPointToRemind(45);

    vip = new CustomerType();
    vip.setCode("vip");
    vip.setName("VIP");
    vip.setDiscountPercent(20);
    vip.setMinPoint(100);
    vip.setEffectiveTime(12);
    vip.setPointToRemind(90);

    binh_thuong.setNextCustomerType(than_thiet);
    than_thiet.setNextCustomerType(thanhvien);
    thanhvien.setNextCustomerType(vip);

    applicationContext.getBean(CustomerTypeRepository.class).save(vip);
    applicationContext.getBean(CustomerTypeRepository.class).save(thanhvien);
    applicationContext.getBean(CustomerTypeRepository.class).save(than_thiet);
    applicationContext.getBean(CustomerTypeRepository.class).save(binh_thuong);
  }

  private static void initConfig() {
    Config config = new Config();
    config.setCode("HOASU");
    config.setName("Woa Cake");
    config.setPhone("097876543");
    config.setFixPhone("089765433");
    config.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
    config.setPointValue(200_000l);
    config.setRewardIntroducePercent(50);
    config.setWarningBirthdayBerore(3);
    config.setPrefixInvoiceCode("WOA");
    config.setEmailHostName("smtp.gmail.com");
    config.setSmtpPort(465);
    config.setEmailUsername("hoanglt705@gmail.com");
    config.setEmailPassword("Abc0974656005");
    applicationContext.getBean(ConfigRepository.class).save(config);
  }
}