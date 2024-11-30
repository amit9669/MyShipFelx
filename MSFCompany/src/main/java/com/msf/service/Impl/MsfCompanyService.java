package com.msf.service.Impl;

import com.msf.entity.*;
import com.msf.entity.request.*;
import com.msf.entity.response.Msf_jwtToken;
import com.msf.entity.response.PageDTO;
import com.msf.entity.response.ShipmentFilterResponse;
import com.msf.jwt.AuthenticationService;
import com.msf.jwt.JwtService;
import com.msf.repository.*;
import com.msf.service.IMsfCompanyService;
import com.msf.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MsfCompanyService implements IMsfCompanyService {

    private static final String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$"; // Example regex

    @Autowired
    private MsfCompanyRepository msfCompanyRepository;

    @Autowired
    private OrderDetailsRepository orderRepository;

    @Autowired
    private SupportFileRepository supportFileRepository;

    @Autowired
    private StoreDetailsRepository storeRepository;

    @Autowired
    private MsfCompanyCarriesRepository msfCompanyCarriesRepository;

    @Autowired
    PaymentDetailsRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserToken userToken;

    @Autowired
    OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private CommercialCargoRepository cargoRepository;

    @Autowired
    private CommercialCommodityDetailsRepository commodityRepository;

    @Autowired
    private CommercialInvoiceRepository commercialInvoiceRepository;

    @Override
    public Object signUpMsfCompany(MsfCompanyRequest msfCompanyRequest) {
        MsfCompany msfCompany = new MsfCompany();
        msfCompany.setName(msfCompanyRequest.getName());
        msfCompany.setPassword(passwordEncoder.encode(msfCompanyRequest.getPassword()));
        msfCompany.setLocation(msfCompanyRequest.getLocation());

        if (regex.matches(msfCompanyRequest.getMobNumber())) {
            if (msfCompanyRepository.existsByMobNumber(msfCompanyRequest.getMobNumber())) {
                throw new CustomException("Mobile Number already present!!");
            } else {
                msfCompany.setMobNumber(msfCompanyRequest.getMobNumber());
            }
        } else {
            return "Number should contain 10 digits.";
        }
        if (msfCompanyRepository.existsByEmail(msfCompanyRequest.getEmail())) {
            throw new CustomException("Email already present!!");
        } else {
            msfCompany.setEmail(msfCompanyRequest.getEmail());
        }
        MsfCompany msfCompany1 = msfCompanyRepository.save(msfCompany);
        msfCompany.setCompanyId(msfCompany1.getId());
        msfCompanyRepository.save(msfCompany1);
        this.sendEmail(msfCompanyRequest.getEmail(), "Welcome to MSF Dashboard!!",
                "Email : " + msfCompanyRequest.getEmail() + "\n" + "Password : " + msfCompanyRequest.getPassword());
        return "Please Check Your Email!!";
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(message);
            javaMailSender.send(email);
        } catch (CustomException e) {
            log.error("Exception while send Email ", e);
        }
    }

    @Override
    public Object logIn(MsfLogIn msfLogIn) {
        if (msfCompanyRepository.existsByEmail(msfLogIn.getEmail())) {
            MsfCompany msfCompany = msfCompanyRepository.findByEmail(msfLogIn.getEmail()).orElseThrow(() ->
                    new CustomException("Company not found!"));

            if (passwordEncoder.matches(msfLogIn.getPassword(), msfCompany.getPassword())) {
                Authentication authenticate = authenticationService.authenticate(msfLogIn);
                if (authenticate.isAuthenticated()) {
                    this.sendEmail(msfCompany.getEmail(), "MSF-OTP Notification", "OTP is: " + otpService.generateOTP(msfCompany.getEmail()));
                    return "LogIn Successfully! Please check your email for the OTP.";
                } else {
                    return "Authentication failed!";
                }
            } else {
                throw new CustomException("Invalid Password!");
            }
        } else {
            throw new CustomException("Invalid Email!");
        }
    }

    @Override
    public Object checkOtp(MsfLogIn msfLogIn) {
        if (msfCompanyRepository.existsByEmail(msfLogIn.getEmail())) {
            if (otpService.getOtp(msfLogIn.getEmail()) == (msfLogIn.getOtp())) {
                otpService.clearOTP(msfLogIn.getEmail());
                String token = jwtService.generateToken(msfLogIn.getEmail());
                return Msf_jwtToken.builder().jwtToken(token).build();
            } else {
                return "Sorry! Invalid OTP.";
            }
        } else {
            return "Email Invalid.";
        }
    }

    @Override
    public Object getCompanyById(Long companyId) {
        if (msfCompanyRepository.existsByCompanyIdAndIsDeleted(companyId, false)) {
            return msfCompanyRepository.findById(companyId).get();
        } else {
            return "Company Not Found!!";
        }
    }

    @Override
    public Object findByEmail(String email) {
        if (msfCompanyRepository.existsByEmailAndIsDeleted(email, false)) {
            return msfCompanyRepository.findByEmail(email).get();
        } else {
            return "Company Not Found!!";
        }
    }

    @Override
    public Object saveOrUpdate(UserRequest userRequest) {
        if (userRepository.existsById(userRequest.getUserId())) {
            User user = userRepository.findById(userRequest.getUserId()).get();
            List<Long> uId = new ArrayList<>();
            uId.add(userRequest.getUserId());
            if (userRepository.existsByPhoneNumberAndUserIdNotIn(userRequest.getPhoneNumber(), uId)) {
                return "Phone Number Already Exists!!";
            } else {
                if (userRequest.getPhoneNumber().matches(regex)) {
                    user.setPhoneNumber(userRequest.getPhoneNumber());
                } else {
                    return "Number should contain 10 digits.";
                }
            }
            if (msfCompanyRepository.existsByNameAndIsDeleted(userToken.getCompanyFromToken().getName(), false)) {
                if (userRequest.getCompanyName().equalsIgnoreCase(userToken.getCompanyFromToken().getName())) {
                    user.setCompanyName(userRequest.getCompanyName());
                } else {
                    return "Company Invalid";
                }
            } else {
                return "Company Invalid!!";
            }
            saveOrUpdate(user, userRequest);
            userRepository.save(user);
            return "Updated Successfully!!";
        } else {
            User user = new User();
            if (userRepository.existsByPhoneNumberAndIsDeleted(userRequest.getPhoneNumber(), false)) {
                return "Phone Number Already Exists!!";
            } else {
                if (userRequest.getPhoneNumber().matches(regex)) {
                    user.setPhoneNumber(userRequest.getPhoneNumber());
                } else {
                    return "Number should contain 10 digits.";
                }
            }
            if (msfCompanyRepository.existsByNameAndIsDeleted(userToken.getCompanyFromToken().getName(), false)) {
                if (userRequest.getCompanyName().equalsIgnoreCase(userToken.getCompanyFromToken().getName())) {
                    user.setCompanyName(userRequest.getCompanyName());
                } else {
                    return "Company Invalid";
                }
            } else {
                return "Company Invalid!!";
            }
            saveOrUpdate(user, userRequest);
            return "Inserted Successfully!!";
        }
    }

    public void saveOrUpdate(User user, UserRequest userRequest) {
        user.setCompanyId(userToken.getCompanyFromToken().getCompanyId());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setIndustry(userRequest.getIndustry());
        user.setLocation(userRequest.getLocation());
        userRepository.save(user);
    }

    @Override
    public Object getAllUserByCompanyId(Pageable pageable) {
        if (userToken.getCompanyFromToken().getCompanyId() != null) {
            Page<User> allUser = userRepository.findAllUserByCompanyId(userToken.getCompanyFromToken().getCompanyId(), pageable);
            return new PageDTO(allUser.getContent(), allUser.getTotalElements(), allUser.getTotalPages(), allUser.getNumberOfElements());
        } else {
            return "id not found!";
        }
    }

    @Override
    public Object saveOrUpdatePaymentDetails(PaymentRequest paymentRequest) {
        if (paymentRepository.existsById(paymentRequest.getPaymentDetailsId())) {
            PaymentDetails paymentDetails = paymentRepository.findById(paymentRequest.getPaymentDetailsId()).get();
            saveOrUpdate(paymentDetails, paymentRequest);
            return "Updated Successfully!!";
        } else {
            PaymentDetails paymentDetails = new PaymentDetails();
            saveOrUpdate(paymentDetails, paymentRequest);
            return "Inserted Successfully!!";
        }
    }

    public void saveOrUpdate(PaymentDetails paymentDetails, PaymentRequest paymentRequest) {
        paymentDetails = PaymentDetails.builder()
                .paymentMethod(paymentRequest.getPaymentMethod())
                .cardNumber(paymentRequest.getCardNumber())
                .cardType(paymentRequest.getCardType())
                .companyId(userToken.getCompanyFromToken().getCompanyId())
                .cvv(paymentRequest.getCvv())
                .firstName(paymentRequest.getFirstName())
                .lastName(paymentRequest.getLastName())
                .monthYear(paymentRequest.getMonthYear())
                .build();
        paymentRepository.save(paymentDetails);
    }

    @Override
    public Object saveOrUpdateStoreDetails(StoreDetailsRequest storeRequest) {
        if (storeRepository.existsById(storeRequest.getStoreId())) {
            StoreDetails storeDetails = storeRepository.findById(storeRequest.getStoreId()).get();
            if (regex.matches(storeRequest.getPhoneNo())) {
                if (storeRepository.existsByPhoneNo(storeRequest.getPhoneNo())) {
                    return "Phone Number Already Exists!!";
                } else {
                    storeDetails.setPhoneNo(storeRequest.getPhoneNo());
                }
            } else {
                return "Number should contain 10 digits.";
            }
            saveOrUpdate(storeDetails, storeRequest);
            return "Updated Successfully!!!";
        } else {
            StoreDetails storeDetails = new StoreDetails();
            if (regex.matches(storeRequest.getPhoneNo())) {
                if (storeRepository.existsByPhoneNo(storeRequest.getPhoneNo())) {
                    return "Phone Number Already Exists!!";
                } else {
                    storeDetails.setPhoneNo(storeRequest.getPhoneNo());
                }
            } else {
                return "Number should contain 10 digits.";
            }
            saveOrUpdate(storeDetails, storeRequest);
            return "Inserted Successfully!!";
        }
    }

    public void saveOrUpdate(StoreDetails storeDetails, StoreDetailsRequest storeRequest) {
        storeDetails = StoreDetails.builder()
                .companyId(userToken.getCompanyFromToken().getCompanyId())
                .country(storeRequest.getCountry())
                .industry(storeRequest.getIndustry())
                .name(storeRequest.getName())
                .parcelCount(storeRequest.getParcelCount())
                .isActive(true)
                .isDeleted(false)
                .phoneNo(storeRequest.getPhoneNo())
                .build();
        storeRepository.save(storeDetails);
    }

    @Override
    public Object getStoreDetailsByCompanyId(Pageable pageable) {
        if (userToken.getCompanyFromToken().getCompanyId() != null) {
            Page<StoreDetails> storeDetails = storeRepository.findByStoreDetailsByCompanyId(userToken.getCompanyFromToken().getCompanyId(), pageable);
            return new PageDTO(storeDetails.getContent(), storeDetails.getTotalElements(), storeDetails.getTotalPages(), storeDetails.getNumberOfElements());
        } else {
            return "id not found!!";
        }
    }

    @Override
    public Object saveMsfCompanyCarriers(Long msfCompanyCarrierId, List<Long> carrierIds) {
        List<MsfCompanyCarriers> carriersList = new ArrayList<>();
        for (Long carrierId : carrierIds) {
            MsfCompanyCarriers msfCompanyCarriers = new MsfCompanyCarriers();
            msfCompanyCarriers.setMsfCompanyCarrierId(msfCompanyCarrierId);
            msfCompanyCarriers.setCarrierId(carrierId);
            msfCompanyCarriers.setCompanyId(userToken.getCompanyFromToken().getCompanyId());
            msfCompanyCarriers.setIsActive(true);
            msfCompanyCarriers.setIsDeleted(false);
            carriersList.add(msfCompanyCarriers);
        }
        msfCompanyCarriesRepository.saveAll(carriersList);
        return "Inserted Successfully!!!";
    }

    @Override
    public Object getAllCarriersByCompanyId(Pageable pageable) {
        Page<MsfCompanyCarriers> companyCarriersPage = msfCompanyCarriesRepository.
                findAllMsfCompanyCarriersByCompanyId(userToken.getCompanyFromToken().getCompanyId(), pageable);
        return new PageDTO(companyCarriersPage.getContent(), companyCarriersPage.getTotalElements(),
                companyCarriersPage.getTotalPages(), companyCarriersPage.getNumberOfElements());
    }

    @Override
    public Object saveOrUpdateBrand(BrandRequest brandRequest) {
        if (brandRepository.existsById(brandRequest.getBrandId())) {
            Brand brand = brandRepository.findById(brandRequest.getBrandId()).get();
            saveOrUpdateBrand(brand, brandRequest);
            return "Updated Successfully!!";
        } else {
            Brand brand = new Brand();
            saveOrUpdateBrand(brand, brandRequest);
            return "Inserted Successfully!!";
        }
    }

    public void saveOrUpdateBrand(Brand brand, BrandRequest brandRequest) {
        brand.setBrandName(brandRequest.getBrandName());
        brand.setCompanyId(userToken.getCompanyFromToken().getCompanyId());
        brand.setColor(brandRequest.getColor());
        brand.setLogo(imageUploadService.uploadImage(brandRequest.getLogo()));
        brand.setLink(brandRequest.getLink());
        brandRepository.save(brand);
    }

    @Override
    public Object saveOrUpdateSupport(SupportRequest supportRequest) {
        if (supportRepository.existsById(supportRequest.getSupportId())) {
            Support support = supportRepository.findById(supportRequest.getSupportId()).get();
            saveOrUpdateSupport(support, supportRequest);
            return "Updated Successfully!!";
        } else {
            Support support = new Support();
            saveOrUpdateSupport(support, supportRequest);
            return "Inserted Successfully!!";
        }
    }

    public void saveOrUpdateSupport(Support support, SupportRequest supportRequest) {
        MsfCompany msfCompany = msfCompanyRepository.findById(userToken.getCompanyFromToken().getCompanyId()).get();
        support = Support.builder()
                .carrierId(supportRequest.getCarrierId())
                .carrierName(supportRequest.getCarrierName())
                .companyId(userToken.getCompanyFromToken().getCompanyId())
                .companyName(msfCompany.getName())
                .descriptionOfIssue(supportRequest.getDescriptionOfIssue())
                .email(supportRequest.getEmail())
                .fromCountry(supportRequest.getFromCountry())
                .issueType(supportRequest.getIssueType())
                .isActive(true)
                .isDeleted(false)
                .name(supportRequest.getName())
                .phoneNumber(supportRequest.getPhoneNumber())
                .status(supportRequest.getStatus())
                .toCountry(supportRequest.getToCountry())
                .ticketId(this.createTicketCode())
                .trackingNumber(supportRequest.getTrackingNumber())
                .build();
        Support save = supportRepository.save(support);
        List<MultipartFile> multipartFiles = supportRequest.getFile();
        List<SupportFile> supportFiles = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            SupportFile supportFile = new SupportFile();
            supportFile.setFile(imageUploadService.uploadImage(file));
            supportFile.setSupportId(save.getSupportId());
            supportFiles.add(supportFile);
        }
        supportFileRepository.saveAll(supportFiles);

    }

    public String createTicketCode() {
        String code = "TICKET" + (LocalDate.now().getYear());
        for (int i = 1; i < supportRepository.findAll().size() + 100; i++) {
            if (!supportRepository.existsByTicketId(code + (String.format("%05d", i)))) {
                return code + (String.format("%05d", i));
            }
        }
        return code;
    }

    @Override
    public Object getBySupportId(Long supportId) {
        String attachFile = "";
        if (supportRepository.existsById(supportId)) {
            Support support = supportRepository.findById(supportId).get();
            List<SupportFile> supportFile = supportFileRepository.findBySupportId(supportId);
            for (SupportFile file : supportFile) {
                attachFile = file.getFile().length() >= 2 ? (file.getFile() + ", " + attachFile) : (file.getFile());
            }
            attachFile = attachFile.replaceAll(", $", " ");
            support.setFile(attachFile);
            return support;
        } else {
            return "supportId Not Found!!";
        }
    }

    @Override
    public Object getAllSupportByCompanyId(Pageable pageable) {
        String attachFile = "";
        Page<Support> supportPage = supportRepository.findAllSupportByCompanyId(userToken.getCompanyFromToken().getCompanyId(),
                pageable);
        List<Support> supports = new ArrayList<>(supportPage.getContent());
        for (Support support : supports) {
            List<SupportFile> supportFile = supportFileRepository.findAllBySupportId(support.getSupportId());
            for (SupportFile file : supportFile) {
                attachFile = file.getFile().length() >= 2 ? (file.getFile() + ", " + attachFile) : (file.getFile());
            }
            attachFile = attachFile.replaceAll(", $", " ");
            support.setFile(attachFile);
        }
        return new PageDTO(supports, supportPage.getTotalElements(), supportPage.getTotalPages(),
                supportPage.getNumberOfElements());
    }

    @Override
    public Object saveOrUpdateOrderDetails(OrderRequest orderRequest) {
        if (orderRepository.existsById(orderRequest.getCommercialOrderId())) {

            OrderDetails orderDetails = orderRepository.findById(orderRequest.getCommercialOrderId()).get();
            return saveOrUpdateOrderDetails(orderDetails, orderRequest);
        } else {
            OrderDetails orderDetails = new OrderDetails();
            return saveOrUpdateOrderDetails(orderDetails, orderRequest);
        }
    }

    public Long saveOrUpdateOrderDetails(OrderDetails orderDetails, OrderRequest orderRequest) {

        orderDetails = OrderDetails.builder()
                .companyId(userToken.getCompanyFromToken().getCompanyId())
                .exporterAddress(orderRequest.getExporterAddress())
                .exporterAddressLine2(orderRequest.getExporterAddressLine2())
                .exporterCity(orderRequest.getExporterCity())
                .exporterCompanyName(orderRequest.getExporterCompanyName())
                .exporterContactPersonName(orderRequest.getExporterContactPersonName())
                .exporterCountry(orderRequest.getExporterCountry())
                .exporterDestinationType(orderRequest.getExporterDestinationType())
                .exporterEmailAddress(orderRequest.getExporterEmailAddress())
                .exporterPhoneNumber(orderRequest.getExporterPhoneNumber())
                .exporterState(orderRequest.getExporterState())
                .exporterZip(orderRequest.getExporterZip())
                .referenceNumber(orderRequest.getReferenceNumber())
                .shipmentFromPhoneNumber(orderRequest.getShipmentFromPhoneNumber())
                .shippingFromAddress(orderRequest.getShippingFromAddress())
                .shippingFromAddressLine2(orderRequest.getShippingFromAddressLine2())
                .shipmentId(orderRequest.getShipmentId())
                .shippingFromCity(orderRequest.getShippingFromCity())
                .shippingFromCompanyName(orderRequest.getShippingFromCompanyName())
                .shippingFromContactPersonName(orderRequest.getShippingFromContactPersonName())
                .shippingFromCountry(orderRequest.getShippingFromCountry())
                .shippingFromDestinationType(orderRequest.getShippingFromDestinationType())
                .shippingFromEmailAddress(orderRequest.getShippingFromEmailAddress())
                .shippingFromState(orderRequest.getShippingFromState())
                .shippingFromZip(orderRequest.getShippingFromZip())
                .shippingToAddress(orderRequest.getShippingToAddress())
                .shippingToAddressLine2(orderRequest.getShippingToAddressLine2())
                .shippingToCity(orderRequest.getShippingToCity())
                .shippingToCompanyName(orderRequest.getShippingToCompanyName())
                .shippingToContactPersonName(orderRequest.getShippingToContactPersonName())
                .shippingToCountry(orderRequest.getShippingToCountry())
                .shippingToDestinationType(orderRequest.getShippingToDestinationType())
                .shippingToEmailAddress(orderRequest.getShippingToEmailAddress())
                .shippingToPhoneNumber(orderRequest.getShippingToPhoneNumber())
                .shippingToState(orderRequest.getShippingToState())
                .shippingToZip(orderRequest.getShippingToZip())
                .commercialInvoicesId(this.createCommercialInvoiceCode())
                .isActive(true)
                .isDeleted(false)
                .build();
        OrderDetails details = orderRepository.save(orderDetails);
        return details.getCommercialOrderId();
    }

    public String createCommercialInvoiceCode() {
        String code = "MS-";
        for (int i = 1; i < orderRepository.findAll().size() + 100; i++) {
            if (!orderRepository.existsByCommercialInvoicesId(code + (String.format("%05d", i)))) {
                return code + (String.format("%05d", i));
            }
        }
        return code;
    }

    @Override
    public Object getCommercialOrderById(Long commercialOrderId) {
        if (orderRepository.existsById(commercialOrderId)) {
            return orderRepository.findById(commercialOrderId);
        } else {
            return "commercialOrderId Not Found";
        }
    }

    @Override
    public Object getAllCommercialOrderByCompanyId(Pageable pageable) {
        Page<OrderDetails> orderPages = orderRepository.
                findAllOrderDetailsByCompanyIdAndIsDeleted(userToken.getCompanyFromToken().getCompanyId(), false, pageable);
        return new PageDTO(orderPages.getContent(), orderPages.getTotalElements(), orderPages.getTotalPages(), orderPages.getNumberOfElements());
    }

    @Override
    public Object softDeleteCommercialOrderByCommercialOrderId(Long commercialOrderId) {
        if (orderRepository.existsById(commercialOrderId)) {
            OrderDetails orderDetails = orderRepository.findById(commercialOrderId).get();
            if (orderDetails.getIsActive()) {
                orderDetails.setIsActive(false);
                orderDetails.setIsDeleted(true);
            }
            orderRepository.save(orderDetails);
            return "isDeleted is get true!!!";
        } else {
            return "commercialOrderId Not Found";
        }
    }

    @Override
    public Object deleteCommercialOrderByCommercialOrderId(Long commercialOrderId) {
        if (orderRepository.existsById(commercialOrderId)) {
            orderRepository.deleteById(commercialOrderId);
            return "Deleted Successfully!!!";
        } else {
            return "commercialOrderId Not Found";
        }
    }

    @Override
    public Object saveOrUpdateCommercialCargoDetails(CommercialCargoRequest commercialCargoRequest) {
        if (cargoRepository.existsById(commercialCargoRequest.getCommercialCargoId())) {
            CommercialCargoDetails commercialCargoDetails = cargoRepository.findById(commercialCargoRequest.getCommercialCargoId()).get();
            saveOrUpdateCommercialCargoDetails(commercialCargoDetails, commercialCargoRequest);
            return "Updated";
        } else {
            CommercialCargoDetails commercialCargoDetails = new CommercialCargoDetails();
            saveOrUpdateCommercialCargoDetails(commercialCargoDetails, commercialCargoRequest);
            return "Inserted";
        }
    }

    public void saveOrUpdateCommercialCargoDetails(CommercialCargoDetails cargoDetails, CommercialCargoRequest commercialCargoRequest) {
        cargoDetails = CommercialCargoDetails.builder()
                .commercialOrderId(commercialCargoRequest.getCommercialOrderId())
                .packageDetails(commercialCargoRequest.getPackageDetails())
                .packageType(commercialCargoRequest.getPackageType())
                .isActive(true)
                .isDeleted(false)
                .build();
        cargoRepository.save(cargoDetails);
    }

    @Override
    public Object getCommercialCargoDetailsByCommercialOrderId(Long commercialOrderId) {
        if (cargoRepository.existsByCommercialOrderIdAndIsDeleted(commercialOrderId, false)) {
            return cargoRepository.findByCommercialOrderIdAndIsDeleted(commercialOrderId, false);
        } else {
            return "commercialCargoId not found!!";
        }
    }

    @Override
    public Object softDeleteCommercialCargoDetailsByCommercialOrderId(Long commercialOrderId) {
        if (cargoRepository.existsByCommercialOrderIdAndIsDeleted(commercialOrderId, false)) {
            List<CommercialCargoDetails> detailsList = cargoRepository.findByCommercialOrderIdAndIsDeleted(commercialOrderId, false);
            for (CommercialCargoDetails details : detailsList) {
                if (details.getIsActive()) {
                    details.setIsActive(false);
                    details.setIsDeleted(true);
                }
                cargoRepository.save(details);
            }
            return "isDeleted is get true!!!";
        } else {
            return "commercialCargoId not found!!";
        }
    }

    @Override
    public Object deleteCommercialCargoDetailsByCommercialOrderId(Long commercialOrderId) {
        if (cargoRepository.existsByCommercialOrderIdAndIsDeleted(commercialOrderId, false)) {
            List<CommercialCargoDetails> detailsList = cargoRepository.findByCommercialOrderIdAndIsDeleted(commercialOrderId, false);
            cargoRepository.deleteAll(detailsList);
            return "Deleted Successfully!!!";
        } else {
            return "commercialCargoId not found!!";
        }
    }

    @Override
    public Object saveOrUpdateCommercialCommodityDetails(List<CommercialCommodityRequest> requestList) {
        for (CommercialCommodityRequest request : requestList) {
            if (commodityRepository.existsById(request.getCommercialCommodityId())) {
                CommercialCommodityDetails commodityDetails = commodityRepository.findById(request.getCommercialCommodityId()).get();
                saveOrUpdateCommercialCommodityDetails(commodityDetails, request);
            } else {
                CommercialCommodityDetails commodityDetails = new CommercialCommodityDetails();
                saveOrUpdateCommercialCommodityDetails(commodityDetails, request);
            }
        }
        return "Thank-You";
    }

    public void saveOrUpdateCommercialCommodityDetails(CommercialCommodityDetails details, CommercialCommodityRequest request) {
        details = CommercialCommodityDetails.builder()
                .commercialOrderId(request.getCommercialOrderId())
                .hsCode(request.getHsCode())
                .itemName(request.getItemName())
                .unitCurrency(request.getUnitCurrency())
                .unitPrice(request.getUnitPrice())
                .units(request.getUnits())
                .unitType(request.getUnitType())
                .isActive(true)
                .isDeleted(false)
                .build();
        commodityRepository.save(details);
    }

    @Override
    public Object getCommercialCommodityDetailsByCommercialOrderId(Long commercialOrderId) {
        if (commodityRepository.existsByCommercialOrderIdAndIsDeleted(commercialOrderId, false)) {
            return commodityRepository.findByCommercialOrderIdAndIsDeleted(commercialOrderId, false);
        } else {
            return "commercialCommodityId Not found!!";
        }
    }

    @Override
    public Object softDeleteCommercialCommodityDetailsByCommercialOrderId(Long commercialOrderId) {
        if (commodityRepository.existsByCommercialOrderIdAndIsDeleted(commercialOrderId, false)) {
            List<CommercialCommodityDetails> detailsList = commodityRepository.findByCommercialOrderIdAndIsDeleted(commercialOrderId, false);
            for (CommercialCommodityDetails details : detailsList) {
                if (details.getIsActive()) {
                    details.setIsActive(false);
                    details.setIsDeleted(true);
                }
                commodityRepository.save(details);
            }
            return "isDeleted is get true!!!";
        } else {
            return "commercialCommodityId Not found!!";
        }
    }

    @Override
    public Object deleteCommercialCommodityDetailsByCommercialOrderId(Long commercialOrderId) {
        if (commodityRepository.existsByCommercialOrderIdAndIsDeleted(commercialOrderId, false)) {
            List<CommercialCommodityDetails> detailsList = commodityRepository.findByCommercialOrderIdAndIsDeleted(commercialOrderId, false);
            commodityRepository.deleteAll(detailsList);
            return "Deleted Successfully!!!";
        } else {
            return "commercialCommodityId Not found!!";
        }
    }

    @Override
    public Object saveOrUpdateCommercialInvoice(CommercialInvoiceRequest commercialInvoiceRequest) {
        if (commercialInvoiceRepository.existsById(commercialInvoiceRequest.getCommercialInvoiceId())) {
            CommercialInvoice commercialInvoice = commercialInvoiceRepository.findById(commercialInvoiceRequest.getCommercialInvoiceId()).get();
            saveOrUpdateCommercialInvoice(commercialInvoice, commercialInvoiceRequest);
            return "Updated";
        } else {
            CommercialInvoice commercialInvoice = new CommercialInvoice();
            saveOrUpdateCommercialInvoice(commercialInvoice, commercialInvoiceRequest);
            return "Inserted";
        }
    }

    public void saveOrUpdateCommercialInvoice(CommercialInvoice commercialInvoice, CommercialInvoiceRequest commercialInvoiceRequest) {
        commercialInvoice = CommercialInvoice.builder()
                .commercialOrderId(commercialInvoiceRequest.getCommercialOrderId())
                .countryOfManufacturing(commercialInvoiceRequest.getCountryOfManufacturing())
                .reasonOfExport(commercialInvoiceRequest.getReasonOfExport())
                .termsOfSale(commercialInvoiceRequest.getTermsOfSale())
                .isActive(true)
                .isCancel(false)
                .build();
        commercialInvoiceRepository.save(commercialInvoice);
    }

    @Override
    public Object getCommercialInvoiceByCommercialOrderId(Long commercialOrderId) {
        if (commercialInvoiceRepository.existsByCommercialOrderIdAndIsCancel(commercialOrderId, false)) {
            return commercialInvoiceRepository.findByCommercialOrderIdAndIsCancel(commercialOrderId, false);
        } else {
            return "commercialInvoiceID not found!";
        }
    }

    @Override
    public Object getCommercialInvoiceIsCancel(Long commercialOrderId) {
        if (commercialInvoiceRepository.existsByCommercialOrderIdAndIsCancel(commercialOrderId, false)) {
            List<CommercialInvoice> invoiceList = commercialInvoiceRepository.findByCommercialOrderIdAndIsCancel(commercialOrderId, false);
            for (CommercialInvoice invoice : invoiceList) {
                if (invoice.getIsActive()) {
                    invoice.setIsActive(false);
                    invoice.setIsCancel(true);
                }
                commercialInvoiceRepository.save(invoice);
            }
            return "isDeleted is get true!!!";
        } else {
            return "commercialInvoiceID not found!";
        }
    }

    @Override
    public Object deleteCommercialInvoiceByCommercialOrderId(Long commercialOrderId) {
        if (commercialInvoiceRepository.existsByCommercialOrderIdAndIsCancel(commercialOrderId, false)) {
            List<CommercialInvoice> invoiceList = commercialInvoiceRepository.findByCommercialOrderIdAndIsCancel(commercialOrderId, false);
            commercialInvoiceRepository.deleteAll(invoiceList);
            return "Deleted Successfully!!!";
        } else {
            return "commercialInvoiceID not found!";
        }
    }

    @Override
    public Object getAllCommercialDetails() {
        return CommercialAllDetails.builder()
                .cargoDetails(cargoRepository.findAll())
                .commodityDetails(commodityRepository.findAll())
                .orderDetails(orderRepository.findAll())
                .invoice(commercialInvoiceRepository.findAll())
                .build();
    }

    @Override
    public Object searchFilterOfCommercialInvoices(ShippingFilterRequest filterRequest, Pageable pageable) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if (filterRequest.getStartDate() != null && !filterRequest.getStartDate().isEmpty()) {
            LocalDate date = LocalDate.parse(filterRequest.getStartDate());
            startDate = date.atStartOfDay();
        }
        if (filterRequest.getEndDate() != null && !filterRequest.getEndDate().isEmpty()) {
            LocalDate date = LocalDate.parse(filterRequest.getEndDate());
            endDate = date.atStartOfDay();
        }
        Page<ShipmentFilterResponse> responsePage = orderRepository.getSearchFilter(filterRequest.getCompanyId(), filterRequest.getShipmentFromCountry(),
                filterRequest.getShipmentToCountry(), startDate, endDate, filterRequest.getCommercialInvoicesId(), pageable);

        List<ShipmentFilterResponse> searchFilterList = new ArrayList<>(responsePage.getContent());

        if ("desc".equalsIgnoreCase(filterRequest.getSortBy())) {

            if ("shipmentToCountry".equalsIgnoreCase(filterRequest.getSortField())) {
                Collections.reverse(searchFilterList);
                return searchFilterList;
            } else if ("shipmentFromCountry".equalsIgnoreCase(filterRequest.getSortField())) {
                Collections.reverse(searchFilterList);
                return searchFilterList;
            } else if ("commercialInvoicesId".equalsIgnoreCase(filterRequest.getSortField())) {
                Collections.reverse(searchFilterList);
                return searchFilterList;
            }
            Collections.reverse(searchFilterList);
            return searchFilterList;
        }
        return searchFilterList;
    }
}