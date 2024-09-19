package kgp.liivm.discounttest.domain

enum class DiscountCode(
    val success: String,
    val fail: String
) {
    DISCOUNT01("LDZ0241001", "0000000000"),
    DISCOUNT02("LDZ0241002", "0000000000")
}
