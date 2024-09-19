package kgp.liivm.discounttest.domain

enum class DiscountConditionType {
    SUBSCRIPTION_PLAN, // 특정 요금제
    CUSTOMER_AGE_GROUP, // 고객 나이대 (청소년, 고령자)
    LOYALTY_DURATION, // 가입 기간 (장기 가입자 등)
    MINIMUM_USAGE_AMOUNT, // 최소 사용 금액
    CONTRACT_START_DATE // 계약 가입 시점
}
