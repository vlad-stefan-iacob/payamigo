package com.homework.payamigo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Wallet sourceWallet;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Wallet destinationWallet;

    private Double amount;
    private Double commission_percent;
    private Double commission_amount;
    private String currency;
    private String datetime;

}
