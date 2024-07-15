package org.brinst.couponapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record CouponIssueResponseDTO(
	boolean isSuccess,
	String comment
) {
}
