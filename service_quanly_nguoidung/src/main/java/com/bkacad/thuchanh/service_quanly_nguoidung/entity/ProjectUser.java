package com.bkacad.thuchanh.service_quanly_nguoidung.entity;

import com.bkacad.thuchanh.service_quanly_nguoidung.enums.ProjectUserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_users")
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProjectUserRole role;
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
