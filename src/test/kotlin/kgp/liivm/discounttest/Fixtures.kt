package kgp.liivm.discounttest

import kgp.liivm.discounttest.domain.*
import java.math.BigDecimal
import java.time.LocalDate


fun fixedAmountDiscount() = Discount(
    id = 1L,
    discountCode = DiscountCode.DISCOUNT01,
    discountName = "테스트 할인 1",
    discountType = DiscountType.FIXED_AMOUNT,
    discountValue = DiscountValue(
        amount = BigDecimal.valueOf(-1000)
    ),
    discountConditions = listOf(
        DiscountCondition(
            conditionType = DiscountConditionType.SUBSCRIPTION_PLAN,
            conditionValue = DiscountConditionValue.SubscriptionPlan(
                products = listOf(
                    TargetProduct(
                        productCode = "PD00000001",
                        productName = "테스트 상품 1",
                        rateItemCode = "RI00001"
                    ),
                    TargetProduct(
                        productCode = "PD00000002",
                        productName = "테스트 상품 2",
                        rateItemCode = "RI00002"
                    ),
                    TargetProduct(
                        productCode = "PD00000003",
                        productName = "테스트 상품 3",
                        rateItemCode = "RI00003"
                    )
                )
            )
        ),
        DiscountCondition(
            conditionType = DiscountConditionType.CONTRACT_START_DATE,
            conditionValue = DiscountConditionValue.ContractStartDate(
                from = LocalDate.of(2024, 9, 1),
                to = LocalDate.of(2024, 9, 30)
            )
        )
    ),
    exposurePeriod = ExposurePeriod(
        from = LocalDate.of(2024, 9, 1),
        to = LocalDate.of(2024, 9, 30),
    )
)

fun percentageDiscount() = Discount(
    id = 1L,
    discountCode = DiscountCode.DISCOUNT01,
    discountName = "테스트 할인 1",
    discountType = DiscountType.PERCENTAGE,
    discountValue = DiscountValue(
        percentage = 0.5
    ),
    discountConditions = listOf(
        DiscountCondition(
            conditionType = DiscountConditionType.SUBSCRIPTION_PLAN,
            conditionValue = DiscountConditionValue.SubscriptionPlan(
                products = listOf(
                    TargetProduct(
                        productCode = "PD00000001",
                        productName = "테스트 상품 1",
                        rateItemCode = "RI00001"
                    ),
                    TargetProduct(
                        productCode = "PD00000002",
                        productName = "테스트 상품 2",
                        rateItemCode = "RI00002"
                    ),
                    TargetProduct(
                        productCode = "PD00000003",
                        productName = "테스트 상품 3",
                        rateItemCode = "RI00003"
                    )
                )
            )
        ),
        DiscountCondition(
            conditionType = DiscountConditionType.CONTRACT_START_DATE,
            conditionValue = DiscountConditionValue.ContractStartDate(
                from = LocalDate.of(2024, 9, 1),
                to = LocalDate.of(2024, 9, 30)
            )
        )
    ),
    exposurePeriod = ExposurePeriod(
        from = LocalDate.of(2024, 9, 1),
        to = LocalDate.of(2024, 9, 30),
    )
)
