/*
 * SSMDataLoader
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.config.ServerContextProvider;
import com.s3s.ssm.dto.InvoiceStatus;
import com.s3s.ssm.dto.PaymentMode;
import com.s3s.ssm.dto.PaymentType;
import com.s3s.ssm.repo.AreaRepository;
import com.s3s.ssm.repo.CompanyRepository;
import com.s3s.ssm.repo.EmployeeRepository;
import com.s3s.ssm.repo.FinalPeriodProductProcessRepository;
import com.s3s.ssm.repo.FinalPeriodTableProcessRepository;
import com.s3s.ssm.repo.FoodTableRepository;
import com.s3s.ssm.repo.ImportStoreFormRepository;
import com.s3s.ssm.repo.InvoiceRepository;
import com.s3s.ssm.repo.MaterialRepository;
import com.s3s.ssm.repo.PaymentContentRepository;
import com.s3s.ssm.repo.PaymentRepository;
import com.s3s.ssm.repo.ProductRepository;
import com.s3s.ssm.repo.ProductTypeRepository;
import com.s3s.ssm.repo.RoleRepository;
import com.s3s.ssm.repo.SecurityRoleRepository;
import com.s3s.ssm.repo.SecurityUserRepository;
import com.s3s.ssm.repo.ShiftRepository;
import com.s3s.ssm.repo.SupplierRepository;
import com.s3s.ssm.repo.UnitOfMeasureRepository;
import com.s3s.ssm.repo.UomCategoryRepository;
import com.s3s.ssm.repo.UploadFileRepository;
import com.s3s.ssm.security.entity.SecurityRole;
import com.s3s.ssm.security.entity.SecurityUser;
import com.sunrise.xdoc.entity.catalog.Food;
import com.sunrise.xdoc.entity.catalog.FoodIngredient;
import com.sunrise.xdoc.entity.catalog.ImportPriceDetail;
import com.sunrise.xdoc.entity.catalog.Material;
import com.sunrise.xdoc.entity.catalog.Product;
import com.sunrise.xdoc.entity.catalog.ProductType;
import com.sunrise.xdoc.entity.config.Area;
import com.sunrise.xdoc.entity.config.Company;
import com.sunrise.xdoc.entity.config.FoodTable;
import com.sunrise.xdoc.entity.config.UnitOfMeasure;
import com.sunrise.xdoc.entity.config.UomCategory;
import com.sunrise.xdoc.entity.config.UploadFile;
import com.sunrise.xdoc.entity.contact.Supplier;
import com.sunrise.xdoc.entity.employee.Employee;
import com.sunrise.xdoc.entity.employee.Role;
import com.sunrise.xdoc.entity.employee.Shift;
import com.sunrise.xdoc.entity.finance.Payment;
import com.sunrise.xdoc.entity.finance.PaymentContent;
import com.sunrise.xdoc.entity.sale.Invoice;
import com.sunrise.xdoc.entity.sale.InvoiceDetail;
import com.sunrise.xdoc.entity.store.ImportStoreDetail;
import com.sunrise.xdoc.entity.store.ImportStoreForm;

public class SSMDataLoader {
  @SuppressWarnings("deprecation")
  private final static AnnotationConfigApplicationContext applicationContext = ServerContextProvider
          .getInstance().getApplicationContext();
  private static Log logger = LogFactory.getLog(SSMDataLoader.class);

  private static final String INACTIVE_AREA = "INACTIVE_AREA";

  public static final String TANG2 = "Tang 2";

  public static final String TANG1 = "Tang 1";

  private static final String TIGER = "Tiger";

  private static final String SINT_TO_BO = "sinttobo";

  private static final String CAFE_DA = "cafeDa";

  public static Food sinhToBo;

  public static Food cafeDa;

  public static Material tiger2;

  private static ProductType cafeType;

  private static ProductType softDrinkType;

  private static ProductType beerType;

  private static Supplier supplierBeer;

  private static Supplier supplierCoffee1;

  private static UploadFile cafeImage;

  private static UploadFile beerImage;

  private static UploadFile softdrinkImage;

  private static UploadFile iceCoffeeImage;

  private static List<Employee> employees;

  private static List<Area> areas;

  private static List<FoodTable> foodTables;

  private static List<Supplier> suppliers;

  private static List<Product> products;

  private static List<PaymentContent> paymentContents;

  public static FoodTable foodTable_01;

  public static FoodTable foodTable_02;

  public static FoodTable foodTable_03;

  private static Employee staff;

  private static Employee manager;

  public static Material heineken;

  private static UnitOfMeasure lon;

  private static UnitOfMeasure ly;

  private static UnitOfMeasure thung;

  private static UnitOfMeasure goi;

  private static UnitOfMeasure cayThuoc;

  private static Role staffRole;

  private static Role managerRole;

  private static Shift shift1;

  private static Shift shift2;

  private static Shift shift3;

  private static UomCategory weightCategory;

  private static UomCategory unitCategory;

  private static UomCategory beerCategory;

  private static UomCategory lyCategory;

  private static UnitOfMeasure lyLon;

  private static UomCategory thuoclaCategory;

  private static UnitOfMeasure dieu;

  private static Material sugar;

  private static ProductType spiceType;

  private static UnitOfMeasure gam;

  private static UnitOfMeasure kilogam;

  private static Supplier supplierSugar;

  private static ProductType rawMaterialType;

  private static Material cafeBot;

  private static Supplier supplierCoffee2;

  private static UploadFile trungnguyenAvatar;

  private static Area inActiveArea;

  private static ProductType fruitJuiceType;

  private static ProductType cokeType;

  private static ProductType yoghurtType;

  private static ProductType creamType;

  private static Food hotCafe;

  private static UploadFile hotCoffeeImage;

  private static UploadFile cocacolaImage;

  private static Material coca;

  private static UploadFile pepsiImage;

  private static Material pepsi;

  private static FoodTable foodTable_04;

  private static FoodTable foodTable_05;

  private static FoodTable foodTable_06;

  private static FoodTable foodTable_07;

  private static FoodTable foodTable_08;

  private static Food chocolateCream;

  private static UploadFile yaourtImage;

  private static UnitOfMeasure hop;

  private static UomCategory hopCategory;

  private static FoodTable foodTable_09;
  private static Food hotMilkCafe;
  private static ProductType otherDrinkType;
  private static UnitOfMeasure chai;
  private static ProductType tobatoType;
  private static ProductType breakfast;
  private static UomCategory foodCategory;
  private static UnitOfMeasure to;
  private static UnitOfMeasure cay;
  private static UnitOfMeasure dia;
  private static Properties prop;

  static {
    try (InputStream resourceAsStream = SSMDataLoader.class.getClassLoader().getResourceAsStream(
            "testdata/data.properties");
            InputStreamReader isr = new InputStreamReader(resourceAsStream, "UTF-8");) {
      prop = new Properties();
      prop.load(isr);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    // Not find solution to get class path from ssm-core.
    DOMConfigurator.configure("src/main/resources/log4j.xml");
    logger.info("Starting data loader SSM...");
    Locale.setDefault(new Locale("vi"));
    try {
      logger.info("Cleaning data SSM");
      cleanDatabase();

      logger.info("Initializing data SSM");
      initDatabase();

      logger.info("Finished data loader SSM");
      System.exit(0);
    } catch (Exception e) {
      logger.error("Error when init data, please check and clean test data", e);
      System.exit(0);
    }
  }

  public static void cleanDatabase() {
    cleanFinalProcessing();
    cleanStoreModule();
    cleanSaleModule();
    cleanFinanceModule();
    cleanEmployeeModule();
    cleanCatalogModule();
    cleanConfigModule();
    cleanContactModule();
    applicationContext.getBean(UploadFileRepository.class).deleteAll();

    cleanUserAndGroup();
  }

  private static void cleanUserAndGroup() {
    // List<String> usernames =
    // userDetailsManager.getJdbcTemplate().queryForList("select username from users",
    // String.class);
    //
    // for (String username : usernames) {
    // userDetailsManager.deleteUser(username);
    // }
    //
    // for (int i = 0; i < 100; i++) {
    // ObjectIdentity oid = new ObjectIdentityImpl(ACLResourceEnum.class, i);
    // mutableAclService.deleteAcl(oid, true);
    // }
    //
    // userDetailsManager.getJdbcTemplate().update("delete from acl_sid");
    // userDetailsManager.getJdbcTemplate().update("delete from acl_class");
  }

  public static void cleanFinalProcessing() {
    applicationContext.getBean(FinalPeriodProductProcessRepository.class).deleteAll();
    applicationContext.getBean(FinalPeriodTableProcessRepository.class).deleteAll();
  }

  private static void cleanStoreModule() {
    applicationContext.getBean(ImportStoreFormRepository.class).deleteAll();
  }

  private static void cleanSaleModule() {
    applicationContext.getBean(InvoiceRepository.class).deleteAll();
  }

  private static void cleanFinanceModule() {
    applicationContext.getBean(PaymentRepository.class).deleteAll();
    applicationContext.getBean(PaymentContentRepository.class).deleteAll();
  }

  private static void cleanContactModule() {
    applicationContext.getBean(SupplierRepository.class).deleteAll();
  }

  private static void cleanConfigModule() {
    applicationContext.getBean(AreaRepository.class).deleteAll();
    applicationContext.getBean(CompanyRepository.class).deleteAll();
    applicationContext.getBean(UnitOfMeasureRepository.class).deleteAll();
    applicationContext.getBean(UomCategoryRepository.class).deleteAll();
  }

  private static void cleanCatalogModule() {
    applicationContext.getBean(FoodTableRepository.class).deleteAll();
    applicationContext.getBean(ProductRepository.class).deleteAll();
    applicationContext.getBean(ProductTypeRepository.class).deleteAll();
  }

  private static void cleanEmployeeModule() {
    applicationContext.getBean(EmployeeRepository.class).deleteAll();
    applicationContext.getBean(RoleRepository.class).deleteAll();
    applicationContext.getBean(ShiftRepository.class).deleteAll();
  }

  public static void initDatabase() {
    initBasicData();
    initPayment(paymentContents, employees);
    initInvoice(foodTables, employees, products);
    initRetailInvoice(foodTables, employees, products);
    initImportStoreForm(employees, suppliers, products);
  }

  public static void initBasicData() {
    employees = initEmployeeModule();
    initCompany();
    areas = initArea();
    foodTables = initFoodTable(areas);
    initProductType();
    initUOMCategory();
    initUOM();
    initSupplier();
    initProduct();
    paymentContents = initPaymentContent();
  }

  private static void initImportStoreForm(List<Employee> employees,
          List<Supplier> suppliers, List<Product> products) {
    generateImportStoreForm1(employees, suppliers);
  }

  private static void generateImportStoreForm1(List<Employee> employees,
          List<Supplier> suppliers) {
    ImportStoreForm form = new ImportStoreForm();
    form.setCode("FORM_1");
    form.setStaff(employees.get(0));
    form.setSupplier(supplierBeer);

    Set<ImportStoreDetail> details = new HashSet<>();
    ImportStoreDetail detail1 = new ImportStoreDetail();
    detail1.setImportStoreForm(form);
    detail1.setMaterial(tiger2);
    detail1.setImportUnitPrice(getPriceOfMainSupplier(tiger2));
    detail1.setUom(tiger2.getUom());
    detail1.setQuantity(100);
    detail1.setPriceSubtotal(getPriceOfMainSupplier(tiger2) * 100);
    details.add(detail1);

    form.setImportDetails(details);
    applicationContext.getBean(ImportStoreFormRepository.class).save(form);
  }

  private static void initRetailInvoice(List<FoodTable> foodTables,
          List<Employee> employees, List<Product> products) {
    Date createdDate = generateCreatedDate();

    Invoice invoice1 = new Invoice();
    invoice1.setCode("INV_CODE3");
    invoice1.setInvoiceStatus(InvoiceStatus.PAID);
    invoice1.setStaff(employees.get(1));
    invoice1.setCreatedDate(createdDate);

    List<InvoiceDetail> details = new ArrayList<>();
    InvoiceDetail detail1 = new InvoiceDetail();
    detail1.setInvoice(invoice1);
    detail1.setProduct(tiger2);
    detail1.setQuantity(10);
    detail1.setUom(tiger2.getUom());
    detail1.setUnitPrice(tiger2.getSellPrice());
    long amount1 = tiger2.getSellPrice() * 10;
    detail1.setAmount(amount1);
    details.add(detail1);

    invoice1.setInvoiceDetails(details);
    invoice1.setTotalAmount(amount1);
    applicationContext.getBean(InvoiceRepository.class).save(invoice1);
  }

  private static Date generateCreatedDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, 2, 6, 8, 0);
    return calendar.getTime();
  }

  private static Date generateEndedDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, Calendar.FEBRUARY, 6, 10, 0);
    return calendar.getTime();
  }

  public static void initInvoice(List<FoodTable> foodTables, List<Employee> employees, List<Product> products) {
    // for (int i = 5; i < 1_000_005; i++) {
    // generateInvoice1(employees, i);
    // }
    generateInvoice1(employees, 15);
    generateInvoice2(foodTables, employees);
    generateInvoice3(foodTables, employees);
  }

  private static void generateInvoice1(List<Employee> employees, int order) {
    Date createdDate = generateCreatedDate();
    Invoice invoice1 = new Invoice();
    invoice1.setCode("INV_CODE" + order);
    invoice1.setFoodTable(foodTable_01);
    invoice1.setInvoiceStatus(InvoiceStatus.PAID);
    invoice1.setStaff(employees.get(0));
    invoice1.setCreatedDate(createdDate);
    invoice1.setEndedDate(generateEndedDate());

    List<InvoiceDetail> details = new ArrayList<>();
    InvoiceDetail detail1 = new InvoiceDetail();
    detail1.setInvoice(invoice1);
    detail1.setProduct(cafeDa);
    detail1.setQuantity(3);
    detail1.setUnitPrice(cafeDa.getSellPrice());
    detail1.setUom(cafeDa.getUom());
    long amount1 = cafeDa.getSellPrice() * 3;
    detail1.setAmount(amount1);
    details.add(detail1);

    InvoiceDetail detail2 = new InvoiceDetail();
    detail2.setInvoice(invoice1);
    detail2.setProduct(sinhToBo);
    detail2.setQuantity(2);
    detail2.setUom(sinhToBo.getUom());
    detail2.setUnitPrice(sinhToBo.getSellPrice());
    long amount2 = sinhToBo.getSellPrice() * 2;
    detail2.setAmount(amount2);
    details.add(detail2);

    invoice1.setInvoiceDetails(details);
    invoice1.setTotalAmount(amount1 + amount2);
    invoice1.setTotalPaymentAmount(200000l);
    invoice1.setTotalReturnAmount(105000l);
    invoice1.setIncome(95000l);
    applicationContext.getBean(InvoiceRepository.class).save(invoice1);
  }

  private static void generateInvoice2(List<FoodTable> foodTables,
          List<Employee> employees) {
    Calendar createdCalendar = Calendar.getInstance();
    createdCalendar.set(2014, Calendar.FEBRUARY, 6, 15, 0);

    Calendar endedCalendar = Calendar.getInstance();
    endedCalendar.set(2014, Calendar.FEBRUARY, 6, 18, 0);

    Invoice invoice1 = new Invoice();
    invoice1.setCode("INV_CODE2");
    invoice1.setFoodTable(foodTable_01);
    invoice1.setCreatedDate(createdCalendar.getTime());
    invoice1.setEndedDate(endedCalendar.getTime());
    invoice1.setInvoiceStatus(InvoiceStatus.PAID);
    invoice1.setStaff(employees.get(1));

    List<InvoiceDetail> details = new ArrayList<>();
    InvoiceDetail detail1 = new InvoiceDetail();
    detail1.setInvoice(invoice1);
    detail1.setProduct(cafeDa);
    detail1.setQuantity(1);
    detail1.setUom(cafeDa.getUom());
    detail1.setUnitPrice(cafeDa.getSellPrice());
    long amount1 = cafeDa.getSellPrice() * 1;
    detail1.setAmount(amount1);
    details.add(detail1);

    InvoiceDetail detail2 = new InvoiceDetail();
    detail2.setInvoice(invoice1);
    detail2.setProduct(tiger2);
    detail2.setQuantity(5);
    detail2.setUom(tiger2.getUom());
    detail2.setUnitPrice(tiger2.getSellPrice());
    long amount2 = tiger2.getSellPrice() * 5;
    detail2.setAmount(amount2);
    details.add(detail2);

    invoice1.setInvoiceDetails(details);
    invoice1.setTotalAmount(amount1 + amount2);
    invoice1.setTotalPaymentAmount(500000l);
    invoice1.setTotalReturnAmount(390000l);
    invoice1.setIncome(amount1 + amount2);
    applicationContext.getBean(InvoiceRepository.class).save(invoice1);
  }

  private static void generateInvoice3(List<FoodTable> foodTables,
          List<Employee> employees) {
    Calendar createdCalendar = Calendar.getInstance();
    createdCalendar.set(Calendar.HOUR_OF_DAY, 8);
    createdCalendar.set(Calendar.MINUTE, 30);

    Calendar endedCalendar = Calendar.getInstance();
    endedCalendar.set(Calendar.HOUR_OF_DAY, 13);
    endedCalendar.set(Calendar.MINUTE, 0);

    Invoice invoice1 = new Invoice();
    invoice1.setCode("INV_CODE4");
    invoice1.setFoodTable(foodTable_01);
    invoice1.setCreatedDate(createdCalendar.getTime());
    invoice1.setEndedDate(endedCalendar.getTime());
    invoice1.setInvoiceStatus(InvoiceStatus.PAID);
    invoice1.setStaff(employees.get(1));

    List<InvoiceDetail> details = new ArrayList<>();
    InvoiceDetail detail1 = new InvoiceDetail();
    detail1.setInvoice(invoice1);
    detail1.setProduct(cafeDa);
    detail1.setQuantity(1);
    detail1.setUom(cafeDa.getUom());
    detail1.setUnitPrice(cafeDa.getSellPrice());
    long amount1 = cafeDa.getSellPrice() * 1;
    detail1.setAmount(amount1);
    details.add(detail1);

    InvoiceDetail detail2 = new InvoiceDetail();
    detail2.setInvoice(invoice1);
    detail2.setProduct(tiger2);
    detail2.setQuantity(5);
    detail2.setUom(tiger2.getUom());
    detail2.setUnitPrice(tiger2.getSellPrice());
    long amount2 = tiger2.getSellPrice() * 5;
    detail2.setAmount(amount2);
    details.add(detail2);

    invoice1.setInvoiceDetails(details);
    invoice1.setTotalAmount(amount1 + amount2);
    invoice1.setTotalPaymentAmount(500000l);
    invoice1.setTotalReturnAmount(390000l);
    invoice1.setIncome(amount1 + amount2);
    applicationContext.getBean(InvoiceRepository.class).save(invoice1);
  }

  private static void initPayment(List<PaymentContent> paymentContents,
          List<Employee> employees) {
    List<Payment> payments = new ArrayList<Payment>();
    Payment customReceipt = new Payment();
    customReceipt.setCode("KHACH_TRA_TIEN_THIEU");
    customReceipt.setStaff(employees.get(0));
    customReceipt.setPaymentContent(paymentContents.get(0));
    customReceipt.setPaymentMode(PaymentMode.CASH);
    customReceipt.setAmount(50000L);
    payments.add(customReceipt);

    Payment elecPayment = new Payment();
    elecPayment.setCode("TRA_TIEN_DIEN");
    elecPayment.setStaff(employees.get(0));
    elecPayment.setPaymentContent(paymentContents.get(1));
    elecPayment.setPaymentMode(PaymentMode.CASH);
    elecPayment.setAmount(200000L);
    payments.add(elecPayment);

    Payment waterPament = new Payment();
    waterPament.setCode("TRA_TIEN_NUOC");
    waterPament.setStaff(employees.get(0));
    waterPament.setPaymentContent(paymentContents.get(2));
    waterPament.setPaymentMode(PaymentMode.CASH);
    waterPament.setAmount(300000L);
    payments.add(waterPament);

    applicationContext.getBean(PaymentRepository.class).save(payments);
  }

  public static List<PaymentContent> initPaymentContent() {
    List<PaymentContent> paymentContents = new ArrayList<PaymentContent>();
    PaymentContent receiveContent = new PaymentContent();
    receiveContent.setCode("KHACH_TRA_TIEN_THIEU");
    receiveContent.setName("Khach tra tien thieu");
    receiveContent.setPaymentType(PaymentType.RECEIPT);
    paymentContents.add(receiveContent);

    PaymentContent electricContent = new PaymentContent();
    electricContent.setCode("TRA_TIEN_DIEN");
    electricContent.setName("Tra tien dien");
    electricContent.setPaymentType(PaymentType.PAY);
    paymentContents.add(electricContent);

    PaymentContent waterContent = new PaymentContent();
    waterContent.setCode("TRA_TIEN_NUOC");
    waterContent.setName("Tra tien nuoc");
    waterContent.setPaymentType(PaymentType.PAY);
    paymentContents.add(waterContent);

    applicationContext.getBean(PaymentContentRepository.class).save(paymentContents);
    return paymentContents;
  }

  public static void initSupplier() {
    List<Supplier> suppliers = new ArrayList<Supplier>();
    supplierBeer = new Supplier();
    supplierBeer.setCode("Dona_Newtower");
    supplierBeer.setName("Dona Newtower");
    supplierBeer.setAddress("319 - D16, Ly Thuong Kiet, P. 15, Q. 11, Tp. HCM");
    supplierBeer.setPhone("(08) 38629242");
    supplierBeer.setFax("(08) 38627560");
    supplierBeer.setRepresenter("Nguyen Van Trinh");
    supplierBeer.setPosition("Giam doc");
    suppliers.add(supplierBeer);

    supplierCoffee1 = new Supplier();
    supplierCoffee1.setCode("Trung_Nguyen");
    supplierCoffee1.setName("Trung Nguyen");
    supplierCoffee1.setImage(getImageData("/testdata/trungnguyenAvatar.jpg"));
    supplierCoffee1.setAddress("76 Hoang Hoa Tham, P.12, QTB");
    supplierCoffee1.setPhone("08 3811 8902");
    supplierCoffee1.setFax("08 3811 8902");
    suppliers.add(supplierCoffee1);

    supplierCoffee2 = new Supplier();
    supplierCoffee2.setCode("VN_Cafe");
    supplierCoffee2.setName("VN Cafe");
    supplierCoffee2.setAddress("Bien Hoa, Dong Nai");
    supplierCoffee2.setPhone("+84-9 19145599");
    supplierCoffee2.setWebsite("www.vncafe.com.vn");
    supplierCoffee2.setEmail("customerservice.vncafe@gmail.com");
    suppliers.add(supplierCoffee2);

    supplierSugar = new Supplier();
    supplierSugar.setCode("HORECO");
    supplierSugar.setName("HORECO");
    supplierSugar.setAddress("Bien Hoa, Dong Nai");
    supplierSugar.setPhone("08 3811 8902");
    supplierSugar.setFax("08 3811 8902");
    suppliers.add(supplierSugar);

    applicationContext.getBean(SupplierRepository.class).save(suppliers);
  }

  @Transactional
  public static List<Employee> initEmployeeModule() {
    initSecurityRole();
    List<Role> roles = initRole();
    List<Shift> shifts = initShift();
    List<Employee> employees = initEmployee(shifts);
    return employees;
  }

  private static List<Employee> initEmployee(List<Shift> shifts) {
    List<UploadFile> avatars = initAvatar();

    List<Employee> employees = new ArrayList<Employee>();
    staff = new Employee();
    staff.setCode("staff");
    staff.setName("Nguyen Van A");
    staff.setBirthday(new Date());
    staff.setAddress("32/3 Ngo Be, Tan Binh, HCM");
    staff.setEmail("nguyenvana@yahoo.com");
    staff.setPhone("0976309763");
    staff.setIdentityCard("271700770");
    staff.setSalary(5000000L);
    staff.getShifts().add(shifts.get(0));
    staff.getShifts().add(shifts.get(1));
    staff.setImage(avatars.get(0).getData());
    staff.setRole(staffRole);

    employees.add(staff);

    manager = new Employee();
    manager.setCode("manager");
    manager.setName("Nguyen Van B");
    manager.setBirthday(new Date());
    manager.setAddress("105 Ton That Tung, Q1, HCM");
    manager.setEmail("nguyenvanb@yahoo.com");
    manager.setPhone("0903256993");
    manager.setIdentityCard("271200750");
    manager.setSalary(10000000L);
    manager.getShifts().add(shifts.get(2));
    manager.setImage(avatars.get(1).getData());
    manager.setRole(managerRole);
    employees.add(manager);

    Employee employee = new Employee();
    employee.setCode("employee");
    employee.setName("employee");
    employee.setBirthday(new Date());
    employee.setAddress("105 Ton That Tung, Q1, HCM");
    employee.setEmail("employee@yahoo.com");
    employee.setPhone("0903256993");
    employee.setIdentityCard("2712007502");
    employee.setSalary(10000000L);
    employee.getShifts().add(shifts.get(2));
    employee.setImage(avatars.get(1).getData());
    employee.setRole(staffRole);
    employees.add(employee);
    applicationContext.getBean(EmployeeRepository.class).save(employees);
    return employees;
  }

  private static List<UploadFile> initAvatar() {
    List<UploadFile> avatars = new ArrayList<UploadFile>();
    UploadFile avatar1 = new UploadFile();
    avatar1.setData(getImageData("/testdata/avatar1.jpg"));
    avatars.add(avatar1);

    UploadFile avatar2 = new UploadFile();
    avatar2.setData(getImageData("/testdata/avatar2.jpg"));
    avatars.add(avatar2);
    applicationContext.getBean(UploadFileRepository.class).save(avatars);
    return avatars;
  }

  private static byte[] getImageData(String path) {
    byte[] byteData = null;
    try (InputStream inputStream = SSMDataLoader.class.getResourceAsStream(path);) {
      byteData = toByteArray(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return byteData;
  }

  /**
   * Reads all the data from the input stream, and returns the bytes read.
   */
  public static byte[] toByteArray(InputStream stream) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    byte[] buffer = new byte[4096];
    int read = 0;
    while (read != -1) {
      read = stream.read(buffer);
      if (read > 0) {
        baos.write(buffer, 0, read);
      }
    }

    return baos.toByteArray();
  }

  private static List<SecurityRole> initSecurityRole() {
    List<SecurityRole> roles = new ArrayList<>();
    List<SecurityUser> salers = new ArrayList<>();
    SecurityRole saleRole = new SecurityRole();
    saleRole.setCode("ban_hang");
    saleRole.setName("Ban hang");
    roles.add(saleRole);

    SecurityRole adminRole = new SecurityRole();
    adminRole.setCode("admin");
    adminRole.setName("Admin");
    roles.add(adminRole);

    SecurityUser staff = new SecurityUser();
    staff.setCode("nhan_vien");
    staff.setUsername("nhan_vien");
    staff.setPassword("123456");
    staff.setRole(saleRole);
    salers.add(staff);

    SecurityUser admin = new SecurityUser();
    admin.setCode("admin");
    admin.setUsername("admin");
    admin.setPassword("123456");
    admin.setRole(adminRole);
    salers.add(admin);

    applicationContext.getBean(SecurityRoleRepository.class).save(roles);
    applicationContext.getBean(SecurityUserRepository.class).save(salers);
    return roles;
  }

  private static List<Role> initRole() {
    List<Role> roles = new ArrayList<Role>();
    staffRole = new Role();
    staffRole.setCode("STAFF");
    staffRole.setName("Nhan vien");
    roles.add(staffRole);

    Role managerRole = new Role();
    managerRole.setCode("MANAGER");
    managerRole.setName("Quan ly");
    roles.add(managerRole);
    applicationContext.getBean(RoleRepository.class).save(roles);
    return roles;
  }

  private static List<Shift> initShift() {
    List<Shift> shifts = new ArrayList<Shift>();
    Date shift1StartTime = new Date();
    shift1StartTime = DateUtils.setHours(shift1StartTime, 6);
    shift1StartTime = DateUtils.setMinutes(shift1StartTime, 0);

    Date shift1EndTime = new Date();
    shift1EndTime = DateUtils.setHours(shift1EndTime, 12);
    shift1EndTime = DateUtils.setMinutes(shift1EndTime, 0);

    Date shift2StartTime = new Date();
    shift2StartTime = DateUtils.setHours(shift2StartTime, 12);
    shift2StartTime = DateUtils.setMinutes(shift2StartTime, 0);

    Date shift2EndTime = new Date();
    shift2EndTime = DateUtils.setHours(shift2EndTime, 18);
    shift2EndTime = DateUtils.setMinutes(shift2EndTime, 0);

    Date shift3StartTime = new Date();
    shift3StartTime = DateUtils.setHours(shift3StartTime, 18);
    shift3StartTime = DateUtils.setMinutes(shift3StartTime, 0);

    Date shift3EndTime = new Date();
    shift3EndTime = DateUtils.setHours(shift3EndTime, 23);
    shift3EndTime = DateUtils.setMinutes(shift3EndTime, 0);

    shift1 = new Shift();
    shift1.setCode("ca 1");
    shift1.setName("Ca 1");
    shift1.setStartTime(shift1StartTime);
    shift1.setEndTime(shift1EndTime);
    shifts.add(shift1);

    shift2 = new Shift();
    shift2.setCode("ca 2");
    shift2.setName("Ca 2");
    shift2.setStartTime(shift2StartTime);
    shift2.setEndTime(shift2EndTime);
    shifts.add(shift2);

    shift3 = new Shift();
    shift3.setCode("ca 3");
    shift3.setName("Ca 3");
    shift3.setStartTime(shift3StartTime);
    shift3.setEndTime(shift3EndTime);
    shifts.add(shift3);
    applicationContext.getBean(ShiftRepository.class).save(shifts);
    return shifts;
  }

  public static void initUOMCategory() {
    List<UomCategory> uomCates = new ArrayList<UomCategory>();

    weightCategory = new UomCategory();
    weightCategory.setCode("Weight");
    weightCategory.setName("Can nang");
    uomCates.add(weightCategory);

    unitCategory = new UomCategory();
    unitCategory.setCode("Unit");
    unitCategory.setName("Cai");
    uomCates.add(unitCategory);

    beerCategory = new UomCategory();
    beerCategory.setCode("lon");
    beerCategory.setName("Lon");
    uomCates.add(beerCategory);

    lyCategory = new UomCategory();
    lyCategory.setCode("ly");
    lyCategory.setName("Ly");
    uomCates.add(lyCategory);

    thuoclaCategory = new UomCategory();
    thuoclaCategory.setCode("thuocla");
    thuoclaCategory.setName("Thuoc la");
    uomCates.add(thuoclaCategory);

    hopCategory = new UomCategory();
    hopCategory.setCode("Hop");
    hopCategory.setName("Hop");
    uomCates.add(hopCategory);

    foodCategory = new UomCategory();
    foodCategory.setCode("thuc_an");
    foodCategory.setName("Thuc an");
    uomCates.add(foodCategory);

    applicationContext.getBean(UomCategoryRepository.class).save(uomCates);
  }

  public static void initUOM() {
    List<UnitOfMeasure> uoms = new ArrayList<UnitOfMeasure>();
    lon = new UnitOfMeasure();
    lon.setCode("lon");
    lon.setName("Lon");
    lon.setShortName("lon");
    lon.setUomCategory(beerCategory);
    lon.setIsBaseMeasure(true);
    lon.setExchangeRate(1F);
    uoms.add(lon);

    chai = new UnitOfMeasure();
    chai.setCode("chai");
    chai.setName("Chai");
    chai.setShortName("chai");
    chai.setUomCategory(beerCategory);
    chai.setIsBaseMeasure(true);
    lon.setExchangeRate(1F);
    uoms.add(chai);

    thung = new UnitOfMeasure();
    thung.setCode("thung");
    thung.setName("thung");
    thung.setUomCategory(beerCategory);
    thung.setExchangeRate(24F);
    uoms.add(thung);

    ly = new UnitOfMeasure();
    ly.setCode("ly");
    ly.setName("Ly");
    ly.setUomCategory(lyCategory);
    ly.setIsBaseMeasure(true);
    ly.setExchangeRate(1F);
    uoms.add(ly);

    lyLon = new UnitOfMeasure();
    lyLon.setCode("lyLon");
    lyLon.setName("LyLon");
    lyLon.setUomCategory(lyCategory);
    lyLon.setExchangeRate(2F);
    uoms.add(lyLon);

    goi = new UnitOfMeasure();
    goi.setCode("goi");
    goi.setName("goi");
    goi.setUomCategory(thuoclaCategory);
    goi.setExchangeRate(24F);
    uoms.add(goi);

    hop = new UnitOfMeasure();
    hop.setCode("hop");
    hop.setName("Hop");
    hop.setUomCategory(hopCategory);
    hop.setExchangeRate(1F);
    uoms.add(hop);

    cayThuoc = new UnitOfMeasure();
    cayThuoc.setCode("cay_thuoc");
    cayThuoc.setName("Cay thuoc");
    cayThuoc.setUomCategory(thuoclaCategory);
    cayThuoc.setExchangeRate(24 * 20F);
    uoms.add(cayThuoc);

    dieu = new UnitOfMeasure();
    dieu.setCode("dieu");
    dieu.setName("dieu");
    dieu.setUomCategory(thuoclaCategory);
    dieu.setExchangeRate(1F);
    dieu.setIsBaseMeasure(true);
    uoms.add(dieu);

    gam = new UnitOfMeasure();
    gam.setCode("gam");
    gam.setName("gam");
    gam.setShortName("g");
    gam.setUomCategory(weightCategory);
    gam.setExchangeRate(1F);
    gam.setIsBaseMeasure(true);
    uoms.add(gam);

    kilogam = new UnitOfMeasure();
    kilogam.setCode("kilogam");
    kilogam.setName("kilogam");
    kilogam.setShortName("kg");
    kilogam.setUomCategory(weightCategory);
    kilogam.setExchangeRate(1000F);
    uoms.add(kilogam);

    to = new UnitOfMeasure();
    to.setCode("to");
    to.setName("to");
    to.setShortName("to");
    to.setUomCategory(foodCategory);
    to.setExchangeRate(1F);
    uoms.add(to);

    dia = new UnitOfMeasure();
    dia.setCode("dia");
    dia.setName("Dia");
    dia.setShortName("Dia");
    dia.setUomCategory(foodCategory);
    dia.setExchangeRate(1F);
    uoms.add(dia);

    cay = new UnitOfMeasure();
    cay.setCode("cay");
    cay.setName("Cay");
    cay.setShortName("cay");
    cay.setUomCategory(foodCategory);
    cay.setExchangeRate(1F);
    uoms.add(cay);
    applicationContext.getBean(UnitOfMeasureRepository.class).save(uoms);
  }

  private static long getPriceOfMainSupplier(Material material) {
    if (material.getImportPrices().size() == 0) {
      return 0;
    }
    for (ImportPriceDetail detail : material.getImportPrices()) {
      if (detail.isMainSupplier()) {
        return detail.getPrice();
      }
    }
    return 0;
  }

  public static void initProduct() {
    List<UploadFile> productImages = initProductImage();
    initMaterial();
    initCoffee(productImages);
    initCoke();
    initCream();
    initTobacco();
    initSmoothy();
    initFruitJuice();
    initBreakfast();
  }

  private static void initCoffee(List<UploadFile> productImages) {
    cafeDa = new Food();
    cafeDa.setCode(CAFE_DA);
    cafeDa.setName(prop.getProperty("lbl.product.coffee.iceCoffee"));
    cafeDa.setProductType(cafeType);
    cafeDa.setUom(ly);
    cafeDa.setUnitPrice(11800L);
    cafeDa.setSellPrice(18000L);
    cafeDa.setImage(productImages.get(3).getData());

    FoodIngredient sugarIngredient = new FoodIngredient();
    sugarIngredient.setMaterial(sugar);
    sugarIngredient.setQuantity(1);
    sugarIngredient.setUom(gam);
    sugarIngredient.setUnitPrice(1800l);
    sugarIngredient.setSubPriceTotal(1800l);
    sugarIngredient.setFood(cafeDa);

    FoodIngredient cafeBotIngredient = new FoodIngredient();
    cafeBotIngredient.setMaterial(cafeBot);
    cafeBotIngredient.setQuantity(5);
    cafeBotIngredient.setUom(gam);
    cafeBotIngredient.setUnitPrice(getPriceOfMainSupplier(cafeBot));
    cafeBotIngredient.setSubPriceTotal(5 * getPriceOfMainSupplier(cafeBot));
    cafeBotIngredient.setFood(cafeDa);

    cafeDa.getFoodIngredients().add(sugarIngredient);
    cafeDa.getFoodIngredients().add(cafeBotIngredient);
    applicationContext.getBean(ProductRepository.class).save(cafeDa);

    hotCafe = new Food();
    hotCafe.setCode("HotCafe");
    hotCafe.setName(prop.getProperty("lbl.product.coffee.hotCoffee"));
    hotCafe.setProductType(cafeType);
    hotCafe.setUom(ly);
    hotCafe.setUnitPrice(22000L);
    hotCafe.setSellPrice(25000L);
    hotCafe.setImage(hotCoffeeImage.getData());
    applicationContext.getBean(ProductRepository.class).save(hotCafe);

    hotMilkCafe = new Food();
    hotMilkCafe.setCode("HotMilkCafe");
    hotMilkCafe.setName(prop.getProperty("lbl.product.coffee.hotMilkCoffee"));
    hotMilkCafe.setProductType(cafeType);
    hotMilkCafe.setUom(ly);
    hotMilkCafe.setUnitPrice(15000L);
    hotMilkCafe.setSellPrice(17000L);
    hotMilkCafe.setImage(getImageData("/testdata/hotMilkCoffee.png"));
    applicationContext.getBean(ProductRepository.class).save(hotMilkCafe);

    Food iceCoffeeMilkCafe = new Food();
    iceCoffeeMilkCafe.setCode("IceMilkCafe");
    iceCoffeeMilkCafe.setName(prop.getProperty("lbl.product.coffee.iceMilkCoffee"));
    iceCoffeeMilkCafe.setProductType(cafeType);
    iceCoffeeMilkCafe.setUom(ly);
    iceCoffeeMilkCafe.setUnitPrice(15000L);
    iceCoffeeMilkCafe.setSellPrice(17000L);
    iceCoffeeMilkCafe.setImage(getImageData("/testdata/iceCoffeeMilk.png"));
    applicationContext.getBean(ProductRepository.class).save(iceCoffeeMilkCafe);

    Food capuchino = new Food();
    capuchino.setCode("capuchino");
    capuchino.setName(prop.getProperty("lbl.product.coffee.capuchino"));
    capuchino.setProductType(cafeType);
    capuchino.setUom(ly);
    capuchino.setUnitPrice(15000L);
    capuchino.setSellPrice(17000L);
    capuchino.setImage(getImageData("/testdata/capuchino.png"));
    applicationContext.getBean(ProductRepository.class).save(capuchino);

    Food bacxiuda = new Food();
    bacxiuda.setCode("bacxiuda");
    bacxiuda.setName(prop.getProperty("lbl.product.coffee.bacxiuda"));
    bacxiuda.setProductType(cafeType);
    bacxiuda.setUom(ly);
    bacxiuda.setUnitPrice(15000L);
    bacxiuda.setSellPrice(17000L);
    bacxiuda.setImage(getImageData("/testdata/bacxiuda.png"));
    applicationContext.getBean(ProductRepository.class).save(bacxiuda);

    Food bacxiunong = new Food();
    bacxiunong.setCode("bacxiunong");
    bacxiunong.setName(prop.getProperty("lbl.product.coffee.bacxiunong"));
    bacxiunong.setProductType(cafeType);
    bacxiunong.setUom(ly);
    bacxiunong.setUnitPrice(15000L);
    bacxiunong.setSellPrice(17000L);
    bacxiunong.setImage(getImageData("/testdata/bacxiunong.png"));
    applicationContext.getBean(ProductRepository.class).save(bacxiunong);

    Food cafechon = new Food();
    cafechon.setCode("cafechon");
    cafechon.setName(prop.getProperty("lbl.product.coffee.cafechon"));
    cafechon.setProductType(cafeType);
    cafechon.setUom(ly);
    cafechon.setUnitPrice(15000L);
    cafechon.setSellPrice(17000L);
    cafechon.setImage(getImageData("/testdata/cafechon.png"));
    applicationContext.getBean(ProductRepository.class).save(cafechon);
  }

  private static void initCream() {
    chocolateCream = new Food();
    chocolateCream.setCode("chocolateCream");
    chocolateCream.setName(prop.getProperty("lbl.product.cream.socola"));
    chocolateCream.setProductType(creamType);
    chocolateCream.setUom(ly);
    chocolateCream.setSellPrice(25000L);
    chocolateCream.setUnitPrice(22000L);
    chocolateCream.setImage(getImageData("/testdata/socola.png"));
    applicationContext.getBean(ProductRepository.class).save(chocolateCream);

    Food kemdau = new Food();
    kemdau.setCode("Kemdau");
    kemdau.setName(prop.getProperty("lbl.product.cream.strawberry"));
    kemdau.setProductType(creamType);
    kemdau.setUom(ly);
    kemdau.setSellPrice(25000L);
    kemdau.setUnitPrice(22000L);
    kemdau.setImage(getImageData("/testdata/kemdau.png"));
    applicationContext.getBean(ProductRepository.class).save(kemdau);

    Food kemsaurieng = new Food();
    kemsaurieng.setCode("kemsaurieng");
    kemsaurieng.setName(prop.getProperty("lbl.product.cream.durian"));
    kemsaurieng.setProductType(creamType);
    kemsaurieng.setUom(ly);
    kemsaurieng.setSellPrice(25000L);
    kemsaurieng.setUnitPrice(22000L);
    kemsaurieng.setImage(getImageData("/testdata/kemsaurieng.png"));
    applicationContext.getBean(ProductRepository.class).save(kemsaurieng);

    Food kemkhoaimon = new Food();
    kemkhoaimon.setCode("kemkhoaimon");
    kemkhoaimon.setName(prop.getProperty("lbl.product.cream.khoaimon"));
    kemkhoaimon.setProductType(creamType);
    kemkhoaimon.setUom(ly);
    kemkhoaimon.setSellPrice(25000L);
    kemkhoaimon.setUnitPrice(22000L);
    kemkhoaimon.setImage(getImageData("/testdata/kemkhoaimon.png"));
    applicationContext.getBean(ProductRepository.class).save(kemkhoaimon);

    Food kemdua = new Food();
    kemdua.setCode("kemdua");
    kemdua.setName(prop.getProperty("lbl.product.cream.coconut"));
    kemdua.setProductType(creamType);
    kemdua.setUom(ly);
    kemdua.setSellPrice(25000L);
    kemdua.setUnitPrice(22000L);
    kemdua.setImage(getImageData("/testdata/kemdua.png"));
    applicationContext.getBean(ProductRepository.class).save(kemdua);

    Food kemvani = new Food();
    kemvani.setCode("kemvani");
    kemvani.setName(prop.getProperty("lbl.product.cream.vani"));
    kemvani.setProductType(creamType);
    kemvani.setUom(ly);
    kemvani.setSellPrice(25000L);
    kemvani.setUnitPrice(22000L);
    kemvani.setImage(getImageData("/testdata/kemvani.png"));
    applicationContext.getBean(ProductRepository.class).save(kemvani);

    Food kemque = new Food();
    kemque.setCode("kemque");
    kemque.setName(prop.getProperty("lbl.product.cream.kemque"));
    kemque.setProductType(creamType);
    kemque.setUom(ly);
    kemque.setSellPrice(25000L);
    kemque.setUnitPrice(22000L);
    kemque.setImage(getImageData("/testdata/kemque.png"));
    applicationContext.getBean(ProductRepository.class).save(kemque);

    Food kemhanhnhan = new Food();
    kemhanhnhan.setCode("kemhanhnhan");
    kemhanhnhan.setName(prop.getProperty("lbl.product.cream.kemhanhnhan"));
    kemhanhnhan.setProductType(creamType);
    kemhanhnhan.setUom(ly);
    kemhanhnhan.setSellPrice(25000L);
    kemhanhnhan.setUnitPrice(22000L);
    kemhanhnhan.setImage(getImageData("/testdata/kemhanhnhan.png"));
    applicationContext.getBean(ProductRepository.class).save(kemhanhnhan);
  }

  private static void initTobacco() {
    Material thuocconmeo = new Material();
    thuocconmeo.setCode("thuoc_con_meo");
    thuocconmeo.setName(prop.getProperty("lbl.product.tobacco.caraven"));
    thuocconmeo.setRetailable(true);
    thuocconmeo.setProductType(tobatoType);
    thuocconmeo.setUom(goi);
    thuocconmeo.setSellPrice(25000L);
    thuocconmeo.setMinimumQuantity(10L);
    thuocconmeo.setQuantityInStock(100D);
    thuocconmeo.setImage(getImageData("/testdata/thuocconmeo.png"));
    applicationContext.getBean(ProductRepository.class).save(thuocconmeo);

    Material baso = new Material();
    baso.setCode("thuoc_ba_so");
    baso.setName(prop.getProperty("lbl.product.tobacco.555"));
    baso.setRetailable(true);
    baso.setProductType(tobatoType);
    baso.setUom(goi);
    baso.setSellPrice(25000L);
    baso.setMinimumQuantity(10L);
    baso.setQuantityInStock(150D);
    baso.setImage(getImageData("/testdata/555.png"));
    applicationContext.getBean(ProductRepository.class).save(baso);

    Material thuocjet = new Material();
    thuocjet.setCode("thuoc_jet");
    thuocjet.setName(prop.getProperty("lbl.product.tobacco.jet"));
    thuocjet.setRetailable(true);
    thuocjet.setProductType(tobatoType);
    thuocjet.setUom(goi);
    thuocjet.setSellPrice(25000L);
    thuocjet.setMinimumQuantity(10L);
    thuocjet.setQuantityInStock(170D);
    thuocjet.setImage(getImageData("/testdata/thuocjet.jpg"));
    applicationContext.getBean(ProductRepository.class).save(thuocjet);

    Material thuochero = new Material();
    thuochero.setCode("thuoc_hero");
    thuochero.setName(prop.getProperty("lbl.product.tobacco.hero"));
    thuochero.setRetailable(true);
    thuochero.setProductType(tobatoType);
    thuochero.setUom(goi);
    thuochero.setSellPrice(25000L);
    thuochero.setImage(getImageData("/testdata/thuoclahero.png"));
    thuochero.setMinimumQuantity(10L);
    thuochero.setQuantityInStock(90D);
    applicationContext.getBean(ProductRepository.class).save(thuochero);
  }

  private static void initFruitJuice() {
    Food orangeJuice = new Food();
    orangeJuice.setCode("cam_vat");
    orangeJuice.setName(prop.getProperty("lbl.product.fruitJuice.orange"));
    orangeJuice.setProductType(fruitJuiceType);
    orangeJuice.setUom(ly);
    orangeJuice.setUnitPrice(9000L);
    orangeJuice.setSellPrice(10000L);
    orangeJuice.setImage(getImageData("/testdata/orange.png"));
    applicationContext.getBean(ProductRepository.class).save(orangeJuice);

    Food strawberry = new Food();
    strawberry.setCode("dau");
    strawberry.setName(prop.getProperty("lbl.product.fruitJuice.strawberry"));
    strawberry.setProductType(fruitJuiceType);
    strawberry.setUom(ly);
    strawberry.setUnitPrice(19000L);
    strawberry.setSellPrice(20000L);
    strawberry.setImage(getImageData("/testdata/strawberry.png"));
    applicationContext.getBean(ProductRepository.class).save(strawberry);

    Food pineapple = new Food();
    pineapple.setCode("thom");
    pineapple.setName(prop.getProperty("lbl.product.fruitJuice.pineapple"));
    pineapple.setProductType(fruitJuiceType);
    pineapple.setUom(ly);
    pineapple.setUnitPrice(18000L);
    pineapple.setSellPrice(20000L);
    pineapple.setImage(getImageData("/testdata/pineapple.png"));
    applicationContext.getBean(ProductRepository.class).save(pineapple);

    Food watermelon = new Food();
    watermelon.setCode("duahau");
    watermelon.setName(prop.getProperty("lbl.product.fruitJuice.watermelon"));
    watermelon.setProductType(fruitJuiceType);
    watermelon.setUom(ly);
    watermelon.setUnitPrice(18000L);
    watermelon.setSellPrice(20000L);
    watermelon.setImage(getImageData("/testdata/watermelon.png"));
    applicationContext.getBean(ProductRepository.class).save(watermelon);

    Food carrot = new Food();
    carrot.setCode("carrot");
    carrot.setName(prop.getProperty("lbl.product.fruitJuice.carrot"));
    carrot.setProductType(fruitJuiceType);
    carrot.setUom(ly);
    carrot.setUnitPrice(18000L);
    carrot.setSellPrice(20000L);
    carrot.setImage(getImageData("/testdata/carrot.png"));
    applicationContext.getBean(ProductRepository.class).save(carrot);

    Food tomato = new Food();
    tomato.setCode("tomato");
    tomato.setName(prop.getProperty("lbl.product.fruitJuice.tomato"));
    tomato.setProductType(fruitJuiceType);
    tomato.setUom(ly);
    tomato.setUnitPrice(18000L);
    tomato.setSellPrice(20000L);
    tomato.setImage(getImageData("/testdata/tomato.png"));
    applicationContext.getBean(ProductRepository.class).save(tomato);

    Food grape = new Food();
    grape.setCode("grape");
    grape.setName(prop.getProperty("lbl.product.fruitJuice.grape"));
    grape.setProductType(fruitJuiceType);
    grape.setUom(ly);
    grape.setUnitPrice(18000L);
    grape.setSellPrice(20000L);
    grape.setImage(getImageData("/testdata/grape.png"));
    applicationContext.getBean(ProductRepository.class).save(grape);

    Food apple = new Food();
    apple.setCode("apple");
    apple.setName(prop.getProperty("lbl.product.fruitJuice.apple"));
    apple.setProductType(fruitJuiceType);
    apple.setUom(ly);
    apple.setUnitPrice(18000L);
    apple.setSellPrice(20000L);
    apple.setImage(getImageData("/testdata/apple.png"));
    applicationContext.getBean(ProductRepository.class).save(apple);
  }

  private static void initBreakfast() {
    Food pho = new Food();
    pho.setCode("pho");
    pho.setName(prop.getProperty("lbl.product.food.pho"));
    pho.setProductType(breakfast);
    pho.setUom(to);
    pho.setUnitPrice(9000L);
    pho.setSellPrice(10000L);
    pho.setImage(getImageData("/testdata/pho.png"));
    applicationContext.getBean(ProductRepository.class).save(pho);

    Food bokho = new Food();
    bokho.setCode("bokho");
    bokho.setName(prop.getProperty("lbl.product.food.bokho"));
    bokho.setProductType(breakfast);
    bokho.setUom(to);
    bokho.setUnitPrice(9000L);
    bokho.setSellPrice(10000L);
    bokho.setImage(getImageData("/testdata/bokho.png"));
    applicationContext.getBean(ProductRepository.class).save(bokho);

    Food nuixaobo = new Food();
    nuixaobo.setCode("nuixaobo");
    nuixaobo.setName(prop.getProperty("lbl.product.food.nuixaobo"));
    nuixaobo.setProductType(breakfast);
    nuixaobo.setUom(dia);
    nuixaobo.setUnitPrice(9000L);
    nuixaobo.setSellPrice(10000L);
    nuixaobo.setImage(getImageData("/testdata/nuixaobo.png"));
    applicationContext.getBean(ProductRepository.class).save(nuixaobo);

    Food xucxich = new Food();
    xucxich.setCode("xucxich");
    xucxich.setName(prop.getProperty("lbl.product.food.sausage"));
    xucxich.setProductType(breakfast);
    xucxich.setUom(cay);
    xucxich.setUnitPrice(9000L);
    xucxich.setSellPrice(10000L);
    xucxich.setImage(getImageData("/testdata/xucxich.png"));
    applicationContext.getBean(ProductRepository.class).save(xucxich);

  }

  private static void initCoke() {
    coca = new Material();
    coca.setCode("CocaLon");
    coca.setName("Coca lon");
    coca.setRetailable(true);
    coca.setProductType(cokeType);
    coca.setUom(lon);
    coca.setSellPrice(25000L);
    coca.setImage(cocacolaImage.getData());
    coca.setMinimumQuantity(10L);
    coca.setQuantityInStock(170D);
    applicationContext.getBean(ProductRepository.class).save(coca);

    pepsi = new Material();
    pepsi.setCode("PepsiLon");
    pepsi.setName("Pepsi lon");
    pepsi.setProductType(cokeType);
    pepsi.setUom(lon);
    pepsi.setRetailable(true);
    pepsi.setSellPrice(25000L);
    pepsi.setImage(pepsiImage.getData());
    pepsi.setMinimumQuantity(10L);
    pepsi.setQuantityInStock(200D);
    applicationContext.getBean(ProductRepository.class).save(pepsi);

    Material sevenup = new Material();
    sevenup.setCode("sevenup");
    sevenup.setName("Seven up");
    sevenup.setProductType(cokeType);
    sevenup.setUom(lon);
    sevenup.setRetailable(true);
    sevenup.setSellPrice(25000L);
    sevenup.setImage(getImageData("/testdata/7up.png"));
    sevenup.setMinimumQuantity(10L);
    sevenup.setQuantityInStock(100D);
    applicationContext.getBean(ProductRepository.class).save(sevenup);

    Material stingdaulon = new Material();
    stingdaulon.setCode("stingdaulon");
    stingdaulon.setName(prop.getProperty("lbl.product.coke.stingdau"));
    stingdaulon.setProductType(cokeType);
    stingdaulon.setUom(lon);
    stingdaulon.setRetailable(true);
    stingdaulon.setSellPrice(25000L);
    stingdaulon.setImage(getImageData("/testdata/sting.png"));
    stingdaulon.setMinimumQuantity(10L);
    stingdaulon.setQuantityInStock(100D);
    applicationContext.getBean(ProductRepository.class).save(stingdaulon);

    Material saxi = new Material();
    saxi.setCode("saxi");
    saxi.setName(prop.getProperty("lbl.product.coke.saxiCan"));
    saxi.setProductType(cokeType);
    saxi.setUom(lon);
    saxi.setRetailable(true);
    saxi.setSellPrice(25000L);
    saxi.setImage(getImageData("/testdata/saxi.png"));
    saxi.setMinimumQuantity(10L);
    saxi.setQuantityInStock(110D);
    applicationContext.getBean(ProductRepository.class).save(saxi);

    Material twister = new Material();
    twister.setCode("twister");
    twister.setName("Twister");
    twister.setProductType(cokeType);
    twister.setUom(lon);
    twister.setRetailable(true);
    twister.setSellPrice(25000L);
    twister.setImage(getImageData("/testdata/twister.png"));
    twister.setMinimumQuantity(10L);
    twister.setQuantityInStock(90D);
    applicationContext.getBean(ProductRepository.class).save(twister);

    Material mirinda = new Material();
    mirinda.setCode("mirinda");
    mirinda.setName("Mirinda");
    mirinda.setProductType(cokeType);
    mirinda.setUom(lon);
    mirinda.setRetailable(true);
    mirinda.setSellPrice(25000L);
    mirinda.setImage(getImageData("/testdata/mirinda.png"));
    mirinda.setMinimumQuantity(10L);
    mirinda.setQuantityInStock(80D);
    applicationContext.getBean(ProductRepository.class).save(mirinda);

    Material samurai = new Material();
    samurai.setCode("samurai");
    samurai.setName("Samurai");
    samurai.setProductType(cokeType);
    samurai.setUom(chai);
    samurai.setRetailable(true);
    samurai.setSellPrice(25000L);
    samurai.setImage(getImageData("/testdata/samurai.png"));
    samurai.setMinimumQuantity(10L);
    samurai.setQuantityInStock(180D);
    applicationContext.getBean(ProductRepository.class).save(samurai);
  }

  private static void initSmoothy() {
    sinhToBo = new Food();
    sinhToBo.setCode(SINT_TO_BO);
    sinhToBo.setName(prop.getProperty("lbl.product.smoothy.avocado"));
    sinhToBo.setProductType(softDrinkType);
    sinhToBo.setUom(ly);
    sinhToBo.setUnitPrice(9000L);
    sinhToBo.setSellPrice(10000L);
    sinhToBo.setImage(getImageData("/testdata/avocado_smoothy.png"));
    applicationContext.getBean(ProductRepository.class).save(sinhToBo);

    Food coconut = new Food();
    coconut.setCode("sinhtodua");
    coconut.setName(prop.getProperty("lbl.product.smoothy.coconut"));
    coconut.setProductType(softDrinkType);
    coconut.setUom(ly);
    coconut.setUnitPrice(9000L);
    coconut.setSellPrice(10000L);
    coconut.setImage(getImageData("/testdata/coconut.png"));
    applicationContext.getBean(ProductRepository.class).save(coconut);

    Food strawberrySmoothy = new Food();
    strawberrySmoothy.setCode("sinhtodau");
    strawberrySmoothy.setName(prop.getProperty("lbl.product.smoothy.strawberry"));
    strawberrySmoothy.setProductType(softDrinkType);
    strawberrySmoothy.setUom(ly);
    strawberrySmoothy.setUnitPrice(19000L);
    strawberrySmoothy.setSellPrice(20000L);
    strawberrySmoothy.setImage(getImageData("/testdata/strawberry_smoothy.png"));
    applicationContext.getBean(ProductRepository.class).save(strawberrySmoothy);

    Food papayaSmoothy = new Food();
    papayaSmoothy.setCode("sinhtodudu");
    papayaSmoothy.setName(prop.getProperty("lbl.product.smoothy.papaya"));
    papayaSmoothy.setProductType(softDrinkType);
    papayaSmoothy.setUom(ly);
    papayaSmoothy.setUnitPrice(19000L);
    papayaSmoothy.setSellPrice(20000L);
    papayaSmoothy.setImage(getImageData("/testdata/papaya_smoothy.png"));
    applicationContext.getBean(ProductRepository.class).save(papayaSmoothy);

    Food mangoSmoothy = new Food();
    mangoSmoothy.setCode("sinhtoxoai");
    mangoSmoothy.setName(prop.getProperty("lbl.product.smoothy.mango"));
    mangoSmoothy.setProductType(softDrinkType);
    mangoSmoothy.setUom(ly);
    mangoSmoothy.setUnitPrice(19000L);
    mangoSmoothy.setSellPrice(20000L);
    mangoSmoothy.setImage(getImageData("/testdata/mango_smoothy.png"));
    applicationContext.getBean(ProductRepository.class).save(mangoSmoothy);

    Food mangcau_smoothy = new Food();
    mangcau_smoothy.setCode("sinhtomangcau");
    mangcau_smoothy.setName(prop.getProperty("lbl.product.smoothy.mangcau"));
    mangcau_smoothy.setProductType(softDrinkType);
    mangcau_smoothy.setUom(ly);
    mangcau_smoothy.setUnitPrice(19000L);
    mangcau_smoothy.setSellPrice(20000L);
    mangcau_smoothy.setImage(getImageData("/testdata/mangcau.png"));
    applicationContext.getBean(ProductRepository.class).save(mangcau_smoothy);

    Food sapoche_smoothy = new Food();
    sapoche_smoothy.setCode("sinhtosapoche");
    sapoche_smoothy.setName(prop.getProperty("lbl.product.smoothy.sapoche"));
    sapoche_smoothy.setProductType(softDrinkType);
    sapoche_smoothy.setUom(ly);
    sapoche_smoothy.setUnitPrice(19000L);
    sapoche_smoothy.setSellPrice(20000L);
    sapoche_smoothy.setImage(getImageData("/testdata/sapoche.png"));
    applicationContext.getBean(ProductRepository.class).save(sapoche_smoothy);

    Food passion_smoothy = new Food();
    passion_smoothy.setCode("sinhtochanhday");
    passion_smoothy.setName(prop.getProperty("lbl.product.smoothy.passion"));
    passion_smoothy.setProductType(softDrinkType);
    passion_smoothy.setUom(ly);
    passion_smoothy.setUnitPrice(19000L);
    passion_smoothy.setSellPrice(20000L);
    passion_smoothy.setImage(getImageData("/testdata/passion.png"));
    applicationContext.getBean(ProductRepository.class).save(passion_smoothy);
  }

  private static void initMaterial() {
    initBeer();

    sugar = new Material();
    sugar.setCode("sugar");
    sugar.setName(prop.getProperty("lbl.product.material.sugar"));
    sugar.setProductType(spiceType);
    sugar.setUom(gam);
    sugar.setImage(getImageData("/testdata/sugar.jpg"));
    sugar.setMinimumQuantity(20l);
    sugar.setQuantityInStock(30d);

    ImportPriceDetail detail4 = new ImportPriceDetail(sugar, supplierSugar, 21000L);
    detail4.setMainSupplier(true);
    sugar.getImportPrices().add(detail4);
    applicationContext.getBean(MaterialRepository.class).save(sugar);

    cafeBot = new Material();
    cafeBot.setCode("BOT_CAFE");
    cafeBot.setName(prop.getProperty("lbl.product.material.coffeeFlour"));
    cafeBot.setProductType(rawMaterialType);
    cafeBot.setImage(getImageData("/testdata/cafeIcon.png"));
    cafeBot.setUom(gam);
    cafeBot.setMinimumQuantity(30L);
    cafeBot.setQuantityInStock(10D);

    ImportPriceDetail detail5 = new ImportPriceDetail(cafeBot, supplierCoffee1, 2000L);
    ImportPriceDetail detail6 = new ImportPriceDetail(cafeBot, supplierCoffee2, 1800L);
    detail5.setMainSupplier(true);
    cafeBot.getImportPrices().add(detail5);
    cafeBot.getImportPrices().add(detail6);
    applicationContext.getBean(MaterialRepository.class).save(cafeBot);
  }

  private static void initBeer() {
    tiger2 = new Material();
    tiger2.setCode(TIGER);
    tiger2.setName(prop.getProperty("lbl.product.beer.tigerBottle"));
    tiger2.setProductType(beerType);
    tiger2.setUom(chai);
    tiger2.setSellPrice(17000L);
    tiger2.setImage(getImageData("/testdata/tiger.png"));
    tiger2.setMinimumQuantity(10L);
    tiger2.setQuantityInStock(20D);
    tiger2.setRetailable(true);

    ImportPriceDetail detail = new ImportPriceDetail(tiger2, supplierBeer, 15000L);
    tiger2.getImportPrices().add(detail);
    detail.setMainSupplier(true);
    applicationContext.getBean(MaterialRepository.class).save(tiger2);

    heineken = new Material();
    heineken.setCode("Heineken_lon");
    heineken.setName(prop.getProperty("lbl.product.beer.heinekenCan"));
    heineken.setProductType(beerType);
    heineken.setUom(lon);
    heineken.setSellPrice(21000L);
    heineken.setImage(getImageData("/testdata/heineken.png"));
    heineken.setMinimumQuantity(20L);
    heineken.setQuantityInStock(30D);
    heineken.setRetailable(true);

    ImportPriceDetail detail1 = new ImportPriceDetail(heineken, supplierBeer, 18000L);
    detail1.setMainSupplier(true);
    heineken.getImportPrices().add(detail1);
    applicationContext.getBean(MaterialRepository.class).save(heineken);

    Material bia333 = new Material();
    bia333.setCode("bia333_lon");
    bia333.setName(prop.getProperty("lbl.product.beer.333Can"));
    bia333.setProductType(beerType);
    bia333.setUom(lon);
    bia333.setSellPrice(21000L);
    bia333.setImage(getImageData("/testdata/333.png"));
    bia333.setMinimumQuantity(20L);
    bia333.setQuantityInStock(30D);
    bia333.setRetailable(true);

    ImportPriceDetail detail2 = new ImportPriceDetail(bia333, supplierBeer, 18000L);
    detail2.setMainSupplier(true);
    bia333.getImportPrices().add(detail2);
    applicationContext.getBean(MaterialRepository.class).save(bia333);

    Material saigonchai = new Material();
    saigonchai.setCode("saigondo");
    saigonchai.setName(prop.getProperty("lbl.product.beer.saigonBottle"));
    saigonchai.setProductType(beerType);
    saigonchai.setUom(chai);
    saigonchai.setSellPrice(21000L);
    saigonchai.setImage(getImageData("/testdata/saigonchai.png"));
    saigonchai.setMinimumQuantity(20L);
    saigonchai.setQuantityInStock(30D);
    saigonchai.setRetailable(true);

    ImportPriceDetail detail3 = new ImportPriceDetail(saigonchai, supplierBeer, 18000L);
    detail3.setMainSupplier(true);
    saigonchai.getImportPrices().add(detail3);
    applicationContext.getBean(MaterialRepository.class).save(saigonchai);

    Material saigonxanhchai = new Material();
    saigonxanhchai.setCode("saigonxanhchai");
    saigonxanhchai.setName(prop.getProperty("lbl.product.beer.saigonGreenBottle"));
    saigonxanhchai.setProductType(beerType);
    saigonxanhchai.setUom(chai);
    saigonxanhchai.setSellPrice(21000L);
    saigonxanhchai.setImage(getImageData("/testdata/saigonxanh.png"));
    saigonxanhchai.setMinimumQuantity(20L);
    saigonxanhchai.setQuantityInStock(30D);
    saigonxanhchai.setRetailable(true);

    ImportPriceDetail detailsaigonxanhchai = new ImportPriceDetail(saigonxanhchai, supplierBeer, 18000L);
    detailsaigonxanhchai.setMainSupplier(true);
    saigonxanhchai.getImportPrices().add(detailsaigonxanhchai);
    applicationContext.getBean(MaterialRepository.class).save(saigonxanhchai);

    Material sapporo = new Material();
    sapporo.setCode("sapporochai");
    sapporo.setName(prop.getProperty("lbl.product.beer.sapporoBottle"));
    sapporo.setProductType(beerType);
    sapporo.setUom(chai);
    sapporo.setSellPrice(21000L);
    sapporo.setImage(getImageData("/testdata/sapporo.png"));
    sapporo.setMinimumQuantity(20L);
    sapporo.setQuantityInStock(30D);
    sapporo.setRetailable(true);

    ImportPriceDetail sapporoDetail = new ImportPriceDetail(sapporo, supplierBeer, 18000L);
    sapporoDetail.setMainSupplier(true);
    sapporo.getImportPrices().add(sapporoDetail);
    applicationContext.getBean(MaterialRepository.class).save(sapporo);

    Material sapporoLon = new Material();
    sapporoLon.setCode("sapporolon");
    sapporoLon.setName(prop.getProperty("lbl.product.beer.sapporoCan"));
    sapporoLon.setProductType(beerType);
    sapporoLon.setUom(lon);
    sapporoLon.setSellPrice(21000L);
    sapporoLon.setImage(getImageData("/testdata/sapporolon.png"));
    sapporoLon.setMinimumQuantity(20L);
    sapporoLon.setQuantityInStock(30D);
    sapporoLon.setRetailable(true);

    ImportPriceDetail sapporoLonDetail = new ImportPriceDetail(sapporoLon, supplierBeer, 18000L);
    sapporoLonDetail.setMainSupplier(true);
    sapporoLon.getImportPrices().add(sapporoLonDetail);
    applicationContext.getBean(MaterialRepository.class).save(sapporoLon);

    Material budweiserLon = new Material();
    budweiserLon.setCode("budweiserLon");
    budweiserLon.setName(prop.getProperty("lbl.product.beer.budweiserCan"));
    budweiserLon.setProductType(beerType);
    budweiserLon.setUom(chai);
    budweiserLon.setSellPrice(21000L);
    budweiserLon.setImage(getImageData("/testdata/budweiser.png"));
    budweiserLon.setMinimumQuantity(20L);
    budweiserLon.setQuantityInStock(30D);
    budweiserLon.setRetailable(true);

    ImportPriceDetail budweiserLonDetail = new ImportPriceDetail(sapporoLon, supplierBeer, 18000L);
    budweiserLonDetail.setMainSupplier(true);
    budweiserLon.getImportPrices().add(budweiserLonDetail);
    applicationContext.getBean(MaterialRepository.class).save(budweiserLon);
  }

  private static List<UploadFile> initProductImage() {
    List<UploadFile> images = new ArrayList<UploadFile>();
    cafeImage = new UploadFile();
    cafeImage.setData(getImageData("/testdata/cafeIcon.png"));
    images.add(cafeImage);

    beerImage = new UploadFile();
    beerImage.setData(getImageData("/testdata/beerIcon.png"));
    images.add(beerImage);

    softdrinkImage = new UploadFile();
    softdrinkImage.setData(getImageData("/testdata/softdrinkIcon.png"));
    images.add(softdrinkImage);

    iceCoffeeImage = new UploadFile();
    iceCoffeeImage.setData(getImageData("/testdata/iceCoffeeIcon.png"));
    images.add(iceCoffeeImage);

    trungnguyenAvatar = new UploadFile();
    trungnguyenAvatar.setData(getImageData("/testdata/trungnguyenAvatar.jpg"));
    images.add(trungnguyenAvatar);

    hotCoffeeImage = new UploadFile();
    hotCoffeeImage.setData(getImageData("/testdata/hotCoffeeIcon.png"));
    images.add(hotCoffeeImage);

    cocacolaImage = new UploadFile();
    cocacolaImage.setData(getImageData("/testdata/cocacolaIcon.png"));
    images.add(cocacolaImage);

    pepsiImage = new UploadFile();
    pepsiImage.setData(getImageData("/testdata/pepsiIcon.png"));
    images.add(pepsiImage);

    yaourtImage = new UploadFile();
    yaourtImage.setData(getImageData("/testdata/yaourtIcon.jpg"));
    images.add(yaourtImage);

    applicationContext.getBean(UploadFileRepository.class).save(images);
    return images;
  }

  public static void initProductType() {
    List<ProductType> productTypes = new ArrayList<>();
    List<UploadFile> images = initProductTypeImage();
    cafeType = new ProductType();
    cafeType.setCode("cafe");
    cafeType.setName(prop.getProperty("lbl.productType.cafe"));
    cafeType.setImage(images.get(0).getData());
    productTypes.add(cafeType);

    softDrinkType = new ProductType();
    softDrinkType.setCode("softDrink");
    softDrinkType.setName(prop.getProperty("lbl.productType.smoothy"));
    softDrinkType.setImage(images.get(1).getData());
    productTypes.add(softDrinkType);

    beerType = new ProductType();
    beerType.setCode("beer");
    beerType.setName("Bia");
    beerType.setImage(images.get(2).getData());
    productTypes.add(beerType);

    fruitJuiceType = new ProductType();
    fruitJuiceType.setCode("fruitJuice");
    fruitJuiceType.setName(prop.getProperty("lbl.productType.juiceFruit"));
    fruitJuiceType.setImage(getImageData("/testdata/orange.png"));
    productTypes.add(fruitJuiceType);

    otherDrinkType = new ProductType();
    otherDrinkType.setCode("otherDrinkType");
    otherDrinkType.setName(prop.getProperty("lbl.productType.otherDrinkType"));
    otherDrinkType.setImage(getImageData("/testdata/samurai.png"));
    productTypes.add(otherDrinkType);

    cokeType = new ProductType();
    cokeType.setCode("coke");
    cokeType.setName(prop.getProperty("lbl.productType.coke"));
    cokeType.setImage(getImageData("/testdata/cocacolaIcon.png"));
    productTypes.add(cokeType);

    tobatoType = new ProductType();
    tobatoType.setCode("tobacco");
    tobatoType.setName(prop.getProperty("lbl.productType.tobacco"));
    tobatoType.setImage(getImageData("/testdata/thuocjet.jpg"));
    productTypes.add(tobatoType);

    yoghurtType = new ProductType();
    yoghurtType.setCode("yoghurtType");
    yoghurtType.setName(prop.getProperty("lbl.productType.yoghurt"));
    yoghurtType.setImage(getImageData("/testdata/yaourtIcon.jpg"));
    productTypes.add(yoghurtType);

    creamType = new ProductType();
    creamType.setCode("creamType");
    creamType.setName("Kem");
    creamType.setImage(getImageData("/testdata/kembaymau.jpg"));
    productTypes.add(creamType);

    breakfast = new ProductType();
    breakfast.setCode("breakfast");
    breakfast.setName(prop.getProperty("lbl.productType.breakfast"));
    breakfast.setImage(getImageData("/testdata/nuixaobo.png"));
    productTypes.add(breakfast);

    spiceType = new ProductType();
    spiceType.setCode("GIA_VI");
    spiceType.setName(prop.getProperty("lbl.productType.spice"));
    spiceType.setImage(getImageData("/testdata/chilli.jpg"));
    productTypes.add(spiceType);

    rawMaterialType = new ProductType();
    rawMaterialType.setCode("RawMaterial");
    rawMaterialType.setName(prop.getProperty("lbl.productType.rawMaterial"));
    rawMaterialType.setImage(getImageData("/testdata/sugar.jpg"));
    productTypes.add(rawMaterialType);

    applicationContext.getBean(ProductTypeRepository.class).save(productTypes);
  }

  private static List<UploadFile> initProductTypeImage() {
    List<UploadFile> images = new ArrayList<UploadFile>();
    UploadFile cafeImage = new UploadFile();
    cafeImage.setData(getImageData("/testdata/cafeIcon.png"));
    images.add(cafeImage);

    UploadFile softdrinkImage = new UploadFile();
    softdrinkImage.setData(getImageData("/testdata/softdrinkIcon.png"));
    images.add(softdrinkImage);

    UploadFile beerImage = new UploadFile();
    beerImage.setData(getImageData("/testdata/beerIcon.png"));
    images.add(beerImage);

    applicationContext.getBean(UploadFileRepository.class).save(images);
    return images;
  }

  public static List<FoodTable> initFoodTable(List<Area> areas) {
    String tableName = prop.getProperty("lbl.table") + " ";
    List<FoodTable> results = new ArrayList<FoodTable>();
    for (int i = 0; i < areas.size() - 1; i++) {
      foodTable_01 = new FoodTable();
      foodTable_01.setCode(i + "_01");
      foodTable_01.setName(tableName + i + "_01");
      foodTable_01.setArea(areas.get(i));
      foodTable_01.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_01);

      foodTable_02 = new FoodTable();
      foodTable_02.setCode(i + "_02");
      foodTable_02.setName(tableName + i + "_02");
      foodTable_02.setArea(areas.get(i));
      foodTable_02.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_02);

      foodTable_03 = new FoodTable();
      foodTable_03.setCode(i + "_03");
      foodTable_03.setName(tableName + i + "_03");
      foodTable_03.setArea(areas.get(i));
      foodTable_03.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_03);

      foodTable_04 = new FoodTable();
      foodTable_04.setCode(i + "_04");
      foodTable_04.setName(tableName + i + "_04");
      foodTable_04.setArea(areas.get(i));
      foodTable_04.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_04);

      foodTable_05 = new FoodTable();
      foodTable_05.setCode(i + "_05");
      foodTable_05.setName(tableName + i + "_05");
      foodTable_05.setArea(areas.get(i));
      foodTable_05.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_05);

      foodTable_06 = new FoodTable();
      foodTable_06.setCode(i + "_06");
      foodTable_06.setName(tableName + i + "_06");
      foodTable_06.setArea(areas.get(i));
      foodTable_06.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_06);

      foodTable_07 = new FoodTable();
      foodTable_07.setCode(i + "_07");
      foodTable_07.setName(tableName + i + "_07");
      foodTable_07.setArea(areas.get(i));
      foodTable_07.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_07);

      foodTable_08 = new FoodTable();
      foodTable_08.setCode(i + "_08");
      foodTable_08.setName(tableName + i + "_08");
      foodTable_08.setArea(areas.get(i));
      foodTable_08.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_08);

      foodTable_09 = new FoodTable();
      foodTable_09.setCode(i + "_09");
      foodTable_09.setName(tableName + i + "_09");
      foodTable_09.setArea(areas.get(i));
      foodTable_09.setSeatNum(4);
      applicationContext.getBean(FoodTableRepository.class).save(foodTable_09);

      results.add(foodTable_01);
      results.add(foodTable_02);
      results.add(foodTable_03);
      results.add(foodTable_04);
      results.add(foodTable_05);
      results.add(foodTable_06);
      results.add(foodTable_07);
      results.add(foodTable_08);
      results.add(foodTable_09);
    }
    FoodTable foodTable_01 = new FoodTable();
    foodTable_01.setCode("9_01");
    foodTable_01.setName(tableName + "9_01");
    foodTable_01.setArea(inActiveArea);
    foodTable_01.setActive(false);
    foodTable_01.setSeatNum(4);
    applicationContext.getBean(FoodTableRepository.class).save(foodTable_01);

    FoodTable foodTable_02 = new FoodTable();
    foodTable_02.setCode("9_02");
    foodTable_02.setName(tableName + "9_02");
    foodTable_02.setArea(inActiveArea);
    foodTable_02.setActive(false);
    foodTable_02.setSeatNum(4);
    applicationContext.getBean(FoodTableRepository.class).save(foodTable_02);

    FoodTable foodTable_03 = new FoodTable();
    foodTable_03.setCode("9_03");
    foodTable_03.setName(tableName + "9_03");
    foodTable_03.setArea(inActiveArea);
    foodTable_03.setActive(false);
    foodTable_03.setSeatNum(4);
    applicationContext.getBean(FoodTableRepository.class).save(foodTable_03);

    results.add(foodTable_01);
    results.add(foodTable_02);
    results.add(foodTable_03);
    return results;
  }

  public static List<Area> initArea() {
    String areaName = prop.getProperty("lbl.area");
    String flatRoofName = prop.getProperty("lbl.area.flat_roof");
    List<Area> areas = new ArrayList<>();
    for (int i = 1; i < 7; i++) {
      Area area = new Area();
      area.setCode("T" + i);
      area.setName(areaName + " " + i);
      areas.add(area);
    }

    Area area7 = new Area();
    area7.setCode("T8");
    area7.setName(flatRoofName);
    areas.add(area7);

    inActiveArea = new Area();
    inActiveArea.setCode(INACTIVE_AREA);
    inActiveArea.setName(INACTIVE_AREA);
    inActiveArea.setActive(false);
    areas.add(inActiveArea);
    applicationContext.getBean(AreaRepository.class).save(areas);
    return areas;
  }

  public static void initCompany() {
    Company company = new Company("Hoa Su");
    company.setCode("HOASU");
    company.setPhone("097876543");
    company.setFixPhone("089765433");
    company.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
    Calendar calendar = Calendar.getInstance();
    Date openTime = DateUtils.setHours(calendar.getTime(), 8);
    openTime = DateUtils.setMinutes(openTime, 0);
    Date closeTime = DateUtils.setHours(calendar.getTime(), 23);
    closeTime = DateUtils.setMinutes(closeTime, 0);
    company.setOpenTime(openTime);
    company.setCloseTime(closeTime);

    company.setImportFormCodeRule("NHAPKHO");
    company.setExportFormCodeRule("XUATKHO");
    company.setInvoiceCodeRule("HOADON");
    company.setPaymentCodeRule("PHIEUCHI");
    company.setReceiptCodeRule("PHIEUTHU");
    company.setEmployeeCodeRule("NV");
    calendar.clear();
    calendar.set(Calendar.MINUTE, 5);
    company.setTableViewAlertTime(calendar.getTime());

    applicationContext.getBean(CompanyRepository.class).save(company);
  }
}