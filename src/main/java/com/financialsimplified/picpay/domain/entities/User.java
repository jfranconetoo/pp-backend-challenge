package com.financialsimplified.picpay.domain.entities;

import com.financialsimplified.picpay.domain.entities.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    private String name;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private BigDecimal balance;
}
