package com.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
// JPA Entity 클래스들이 해당 추상 클래스를 상속할 경우 추상 클래스의 멤버 변수를 컬럼으로 인식
@MappedSuperclass
// Auditing 기능을 포함
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeStamped {

    // 엔티티 객체가 생성되어 저장될 경우 생성 시간이 자동 저장
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    // 이후 엔티티가 변경된 시간을 자동 저장
    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}
