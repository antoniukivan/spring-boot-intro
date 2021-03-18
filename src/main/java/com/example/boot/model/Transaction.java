package com.example.boot.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_from", nullable = false)
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to", nullable = false)
    private Account accountTo;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm:ss")
    private LocalDateTime date;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    public enum Type {
        INCOMING, OUTCOMING
    }
}
