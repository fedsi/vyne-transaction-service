package com.vyne.assignment.transactionservice.entity;

import com.vyne.assignment.transactionservice.validation.CurrencyCodeIso4217;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is mandatory")
    private Date date;

    @NotBlank(message = "Status is mandatory")
    private String status;

    @Min(value = 0, message = "Amount should be greater than 0")
    private long amount;

    @CurrencyCodeIso4217(message = "Must be a valid ISO4217 alphabetic currency code")
    private String currency;

    private String description;
}
