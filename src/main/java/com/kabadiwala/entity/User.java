package com.kabadiwala.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kabadiwala.enums.Role;
import com.kabadiwala.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Generates getters, setters, toString, equals, and hashCode
@Builder // Implements the builder pattern
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all arguments
@ToString // No relationships to exclude in User entity
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) // Enables JPA Auditing for this entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = true)
    private String avatarUrl;

    @Column(nullable = false, unique = true, length = 10)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(length = 20, nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(length = 20, nullable = false)
    private Status status = Status.NEW;

    // --- OTP Fields ---
    @Column(length = 6)
    private String otp;

    private LocalDateTime otpExpiry;

    @Column(length = 512) // Make the column long enough for a JWT
    private String accessToken;

    private LocalDateTime accessTokenExpiry;

    // --- Auditing Fields ---
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Order> assignedOrders;

    // Note: createdBy and updatedBy are commented out as they require
    // a more complex setup with Spring Security. Enable them when you have
    // authentication fully implemented.

    // @CreatedBy
    // @Column(nullable = false, updatable = false)
    // private String createdBy;

    // @LastModifiedBy
    // @Column(insertable = false)
    // private String updatedBy;

}
