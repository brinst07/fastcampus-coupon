package org.brinst.couponapi.service;

import org.brinst.couponapi.controller.dto.CouponIssueRequestDTO;
import org.brinst.couponcore.model.Coupon;
import org.brinst.couponcore.service.CouponIssueService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponIssueRequestService {
	private final CouponIssueService couponIssueService;

	public void issueRequestV1(CouponIssueRequestDTO requestDTO) {
		couponIssueService.issue(requestDTO.couponId(), requestDTO.userId());
		log.info("쿠폰 발급 완료. couponId: %s, userId %s".formatted(requestDTO.couponId(), requestDTO.userId()));
	}
}