package org.brinst.couponapi.controller.dto;

public record CouponIssueRequestDTO(
	long userId,
	long couponId
) {
}
