package kgp.liivm.discounttest

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.bigdecimal.shouldBeEqualIgnoringScale
import io.kotest.matchers.shouldBe
import kgp.liivm.discounttest.command.CalculateDiscountCommand
import java.math.BigDecimal
import java.time.LocalDate

class DiscountTestApplicationTests : BehaviorSpec({

    Given("금액 할인이 정의 되어 있고") {
        val discount = fixedAmountDiscount()

        When("할인 대상 상품이면서 개통기간이 할인 대상 기간 안에 있을경우") {
            val calculateDiscountCommand = CalculateDiscountCommand(
                productCode = "PD00000001",
                rateStartDate = LocalDate.of(2024, 9, 1),
                BigDecimal.valueOf(10000)
            )

            val result = discount.calculate(calculateDiscountCommand)

            Then("성공한다") {
                result.accepted shouldBe true
            }
        }

        When("할인 대상 상품이면서 개통기간이 할인 대상 기간 밖에 있을경우 - 기간 전") {
            val calculateDiscountCommand = CalculateDiscountCommand(
                productCode = "PD00000001",
                rateStartDate = LocalDate.of(2024, 8, 31),
                BigDecimal.valueOf(10000)
            )

            val result = discount.calculate(calculateDiscountCommand)

            Then("실패한다") {
                result.accepted shouldBe false
            }
        }

        When("할인 대상 상품이면서 개통기간이 할인 대상 기간 밖에 있을경우 - 기간 후") {
            val calculateDiscountCommand = CalculateDiscountCommand(
                productCode = "PD00000001",
                rateStartDate = LocalDate.of(2024, 10, 1),
                BigDecimal.valueOf(10000)
            )

            val result = discount.calculate(calculateDiscountCommand)

            Then("실패한다") {
                result.accepted shouldBe false
            }
        }

        When("할인 대상 상품이 아니면서 개통기간이 할인 대상 기간 안에 있을경우") {
            val calculateDiscountCommand = CalculateDiscountCommand(
                productCode = "PD00000004",
                rateStartDate = LocalDate.of(2024, 9, 1),
                BigDecimal.valueOf(10000)
            )

            val result = discount.calculate(calculateDiscountCommand)

            Then("실패한다") {
                result.accepted shouldBe false
            }
        }
    }

    Given("퍼센트 할인이 정의 되어 있고") {
        val discount = percentageDiscount()

        When("할인 적용이 성공 하는 경우") {
            val calculateDiscountCommand = CalculateDiscountCommand(
                productCode = "PD00000001",
                rateStartDate = LocalDate.of(2024, 9, 1),
                BigDecimal.valueOf(10000)
            )

            val result = discount.calculate(calculateDiscountCommand)

            Then("할인 금액은 현재 기본료의 할인 퍼센트와 동일하다") {
                result.amount shouldBeEqualIgnoringScale BigDecimal.valueOf(-5000)
            }
        }
    }
})
