package org.brinst.couponapi;

import org.brinst.couponapi.controller.dto.CouponIssueResponseDTO;
import org.brinst.couponcore.exception.CouponIssueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

	@ExceptionHandler(CouponIssueException.class)
	public CouponIssueResponseDTO couponIssueException(CouponIssueException e) {
		return new CouponIssueResponseDTO(false, e.getErrorCode().getMessage());
	}
}
