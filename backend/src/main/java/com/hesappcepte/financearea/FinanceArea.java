package com.hesappcepte.financearea;

import com.hesappcepte.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "finance_areas")
public class FinanceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "area_type", nullable = false, length = 20)
    private FinanceAreaType areaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sharing_type", nullable = false, length = 20)
    private SharingType sharingType;

    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FinanceAreaStatus status = FinanceAreaStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected FinanceArea() {
    }

    public FinanceArea(
            String name,
            FinanceAreaType areaType,
            SharingType sharingType,
            String baseCurrency,
            User createdBy
    ) {
        this.name = name;
        this.areaType = areaType;
        this.sharingType = sharingType;
        this.baseCurrency = baseCurrency;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
    }

    @PrePersist
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FinanceAreaType getAreaType() {
        return areaType;
    }

    public SharingType getSharingType() {
        return sharingType;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public FinanceAreaStatus getStatus() {
        return status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}