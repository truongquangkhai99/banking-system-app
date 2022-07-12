package com.unvise.bankingsystemapp.domain.currency;

import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rate", uniqueConstraints = {
        @UniqueConstraint(name = "currencies", columnNames = {"from_currency", "to_currency"})
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exc_rate_seq_gen")
    @SequenceGenerator(name = "exc_rate_seq_gen", sequenceName = "exc_rate_seq_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "from_currency", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType fromCurrency;

    @Column(name = "to_currency", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType toCurrency;

    @Column(name = "ratio", precision = 18, scale = 4, nullable = false)
    private BigDecimal ratio;

}
