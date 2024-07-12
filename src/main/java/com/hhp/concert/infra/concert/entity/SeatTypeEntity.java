package com.hhp.concert.infra.concert.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seat_type")
public class SeatTypeEntity {

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(nullable = false)
    private Long price;

    public SeatTypeEntity(final String typeName, final Long price) {
        this.typeName = typeName;
        this.price = price;
    }

}
