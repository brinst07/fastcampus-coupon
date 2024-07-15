package org.brinst.couponapi.controller;

import org.brinst.couponapi.controller.dto.CouponIssueRequestDTO;
import org.brinst.couponapi.controller.dto.CouponIssueResponseDTO;
import org.brinst.couponapi.service.CouponIssueRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CouponIssueController {
	private final CouponIssueRequestService couponIssueRequestService;

	@PostMapping("/v1/issue")
	public CouponIssueResponseDTO issueV1(@RequestBody CouponIssueRequestDTO requestDTO) {
		couponIssueRequestService.issueRequestV1(requestDTO);
		return new CouponIssueResponseDTO(true, null);
	}
}
