package kgp.liivm.discounttest.domain

import java.math.BigDecimal

class DiscountValue(
    val amount: BigDecimal? = null,
    val percentage: Double? = null
) {
    init {
        if (amount != null) {
            check(amount < BigDecimal.ZERO)
        }

        if (percentage != null) {
            check(percentage <= 1)
        }
    }
}
