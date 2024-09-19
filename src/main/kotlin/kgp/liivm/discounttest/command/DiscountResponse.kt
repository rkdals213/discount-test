package kgp.liivm.discounttest.command

import java.math.BigDecimal

data class DiscountResponse(
    val productCode: String,
    val rateItemCode: String,
    val discountCode: String,
    val amount: BigDecimal,
    val accepted: Boolean
)
