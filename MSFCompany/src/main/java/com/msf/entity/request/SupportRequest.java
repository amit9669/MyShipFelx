package com.msf.entity.request;
import com.msf.entity.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportRequest {

    private Long supportId;
    private Long companyId;
    private String name;
    private String email;
    private Status status;
    private String ticketId;
    private String phoneNumber;
    private String companyName;
    private String trackingNumber;
    private Long carrierId;
    private String carrierName;
    private String fromCountry;
    private String toCountry;
    private String issueType;
    private String descriptionOfIssue;
    private List<MultipartFile> file;
    private Boolean isDeleted;
    private Boolean isActive;
}
