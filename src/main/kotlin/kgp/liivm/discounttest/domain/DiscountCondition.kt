package kgp.liivm.discounttest.domain

import java.math.BigDecimal
import java.time.LocalDate

class DiscountCondition(
    val conditionType: DiscountConditionType,
    val conditionValue: DiscountConditionValue,
) {
    init {
        conditionValue.check(conditionType)
    }
}

sealed class DiscountConditionValue {
    abstract fun validate(value: Any?): Boolean
    abstract fun check(conditionType: DiscountConditionType)

    class SubscriptionPlan(
        val products: List<TargetProduct>
    ) : DiscountConditionValue() {
        override fun validate(value: Any?): Boolean {
            val productCode = value as String
            return products.any { it.productCode == productCode }
        }

        override fun check(conditionType: DiscountConditionType) {
            check(conditionType == DiscountConditionType.SUBSCRIPTION_PLAN)
        }
    }

    class CustomerAgeGroup(
        val from: Int,
        val to: Int
    ) : DiscountConditionValue() {
        override fun validate(value: Any?): Boolean {
            return false
        }

        override fun check(conditionType: DiscountConditionType) {
            check(conditionType == DiscountConditionType.CUSTOMER_AGE_GROUP)
        }
    }

    class LoyaltyDuration(
        val from: Int,
        val to: Int
    ) : DiscountConditionValue() {
        override fun validate(value: Any?): Boolean {
            return false
        }

        override fun check(conditionType: DiscountConditionType) {
            check(conditionType == DiscountConditionType.LOYALTY_DURATION)
        }
    }

    class MinimumUsageAmount(
        val amount: BigDecimal
    ) : DiscountConditionValue() {
        override fun validate(value: Any?): Boolean {
            return false
        }

        override fun check(conditionType: DiscountConditionType) {
            check(conditionType == DiscountConditionType.MINIMUM_USAGE_AMOUNT)
        }
    }

    class ContractStartDate(
        val from: LocalDate,
        val to: LocalDate,
    ) : DiscountConditionValue() {
        override fun validate(value: Any?): Boolean {
            val rateStartDate = value as LocalDate
            return !(rateStartDate.isBefore(from) or rateStartDate.isAfter(to))
        }

        override fun check(conditionType: DiscountConditionType) {
            check(conditionType == DiscountConditionType.CONTRACT_START_DATE)
        }
    }
}
