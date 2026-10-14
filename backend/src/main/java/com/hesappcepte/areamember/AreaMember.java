package com.hesappcepte.areamember;

import com.hesappcepte.financearea.FinanceArea;
import com.hesappcepte.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "area_members")
public class AreaMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "finance_area_id", nullable = false)
    private FinanceArea financeArea;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AreaMemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AreaMemberStatus status = AreaMemberStatus.ACTIVE;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "removed_at")
    private LocalDateTime removedAt;

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

    protected AreaMember() {
    }

    public AreaMember(
            FinanceArea financeArea,
            User user,
            AreaMemberRole role,
            User createdBy
    ) {
        this.financeArea = financeArea;
        this.user = user;
        this.role = role;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
    }

    @PrePersist
    private void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        this.joinedAt = now;
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

    public FinanceArea getFinanceArea() {
        return financeArea;
    }

    public User getUser() {
        return user;
    }

    public AreaMemberRole getRole() {
        return role;
    }

    public AreaMemberStatus getStatus() {
        return status;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public LocalDateTime getRemovedAt() {
        return removedAt;
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