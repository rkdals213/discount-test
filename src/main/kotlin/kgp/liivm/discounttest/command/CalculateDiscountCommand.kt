package kgp.liivm.discounttest.command

import java.math.BigDecimal
import java.time.LocalDate

data class CalculateDiscountCommand(
    val productCode: String,
    val rateStartDate: LocalDate,
    val currentAmount: BigDecimal
)
