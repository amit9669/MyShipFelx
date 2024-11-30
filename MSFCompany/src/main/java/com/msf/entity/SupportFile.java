package com.msf.entity;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "support_file")
public class SupportFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_file_id")
    private Long supportFileId;

    @Column(name = "file")
    private String file;

    @Column(name = "support_id")
    private Long supportId;
}
