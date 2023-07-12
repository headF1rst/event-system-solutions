package com.example.api.service

import com.example.api.domain.Coupon
import com.example.api.producer.CouponCreateProducer
import com.example.api.repository.AppliedUserRepository
import com.example.api.repository.CouponCountRepository
import com.example.api.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class ApplyService(
    val couponRepository: CouponRepository,
    val couponCountRepository: CouponCountRepository,
    val couponCreateProducer: CouponCreateProducer,
    val appliedUserRepository: AppliedUserRepository,
) {

    fun apply(userId: Long) {
        val apply = appliedUserRepository.add(userId)

        if (apply != 1L) {
            return
        }

        val count = couponCountRepository.increment()

        if (count != null) {
            if (count > 100) {
                return
            }
        }

        couponCreateProducer.create(userId)
    }
}
