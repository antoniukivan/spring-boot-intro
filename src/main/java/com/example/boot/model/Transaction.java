package com.example.boot.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "transactions")
@Builder
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "account_from", nullable = false)
    private Account accountFrom;

    @ManyToOne
    @Column(name = "account_to", nullable = false)
    private Account accountTo;

    private double amount;

    private LocalDateTime date;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    public enum Type {
        INCOMING, OUTCOMING
    }
}
