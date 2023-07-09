package com.example.api.service

import com.example.api.domain.Coupon
import com.example.api.repository.CouponCountRepository
import com.example.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    val couponRepository: CouponRepository,
    val couponCountRepository: CouponCountRepository,
) {

    fun apply(userId: Long) {
        val count = couponCountRepository.increment()

        if (count != null) {
            if (count > 100) {
                return
            }
        }

        couponRepository.save(Coupon(userId))
    }
}
