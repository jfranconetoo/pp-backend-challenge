package com.financialsimplified.picpay.domain.entities;

import com.financialsimplified.picpay.domain.entities.enums.TransactionStatus;
import com.financialsimplified.picpay.domain.entities.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity(name = "transactions")
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "transactionId")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private LocalDateTime timestamp;

    public void valid() throws Exception {
        if(sender.getType() == UserType.SHOP_MAN) {
            throw new Exception("It is not able to perform transaction");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("There is no balance");
        }
    }
}
