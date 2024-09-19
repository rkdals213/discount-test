package kgp.liivm.discounttest.domain

import kgp.liivm.discounttest.command.CalculateDiscountCommand
import kgp.liivm.discounttest.command.DiscountResponse
import java.math.BigDecimal

class Discount(
    val id: Long,

    val discountCode: DiscountCode,

    val discountName: String,

    val discountType: DiscountType,

    val discountValue: DiscountValue,

    val discountConditions: List<DiscountCondition>,

    val exposurePeriod: ExposurePeriod
) {
    init {
        check(discountConditions.any { it.conditionType == DiscountConditionType.SUBSCRIPTION_PLAN })
    }

    fun calculate(calculateDiscountCommand: CalculateDiscountCommand): DiscountResponse {
        for (discountCondition in discountConditions) {
            val result = validateWithCondition(discountCondition, calculateDiscountCommand)

            if (!result) {
                return DiscountResponse(
                    productCode = calculateDiscountCommand.productCode,
                    rateItemCode = "",
                    discountCode = discountCode.fail,
                    amount = BigDecimal.ZERO,
                    accepted = false
                )
            }
        }

        return when (discountType) {
            DiscountType.FIXED_AMOUNT -> {
                val discountAmount = if (calculateDiscountCommand.currentAmount + discountValue.amount!! < BigDecimal.ZERO) {
                    calculateDiscountCommand.currentAmount * BigDecimal.valueOf(-1)
                } else {
                    discountValue.amount
                }

                DiscountResponse(
                    productCode = calculateDiscountCommand.productCode,
                    rateItemCode = discountConditions.getRateItemCodeByProductCode(calculateDiscountCommand.productCode),
                    discountCode = discountCode.success,
                    amount = discountAmount,
                    accepted = true
                )
            }

            DiscountType.PERCENTAGE -> {
                val discountAmount = calculateDiscountCommand.currentAmount * BigDecimal.valueOf(discountValue.percentage!! * -1)

                DiscountResponse(
                    productCode = calculateDiscountCommand.productCode,
                    rateItemCode = discountConditions.getRateItemCodeByProductCode(calculateDiscountCommand.productCode),
                    discountCode = discountCode.success,
                    amount = discountAmount,
                    accepted = true
                )
            }
        }
    }

    private fun validateWithCondition(discountCondition: DiscountCondition, calculateDiscountCommand: CalculateDiscountCommand): Boolean {
        return when (discountCondition.conditionType) {
            DiscountConditionType.SUBSCRIPTION_PLAN -> {
                discountCondition.conditionValue.validate(calculateDiscountCommand.productCode)
            }

            DiscountConditionType.CUSTOMER_AGE_GROUP -> {
                discountCondition.conditionValue.validate(null)
            }

            DiscountConditionType.LOYALTY_DURATION -> {
                discountCondition.conditionValue.validate(null)
            }

            DiscountConditionType.MINIMUM_USAGE_AMOUNT -> {
                discountCondition.conditionValue.validate(null)
            }

            DiscountConditionType.CONTRACT_START_DATE -> {
                discountCondition.conditionValue.validate(calculateDiscountCommand.rateStartDate)
            }
        }
    }

    private fun List<DiscountCondition>.getRateItemCodeByProductCode(productCode: String): String {
        val subscriptionPlan = this.first { it.conditionType == DiscountConditionType.SUBSCRIPTION_PLAN }
            .conditionValue as DiscountConditionValue.SubscriptionPlan

        return subscriptionPlan.products
            .first { it.productCode == productCode }
            .rateItemCode
    }
}
