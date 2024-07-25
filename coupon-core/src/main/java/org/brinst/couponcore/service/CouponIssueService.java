package org.brinst.couponcore.service;

import static org.brinst.couponcore.exception.ErrorCode.*;

import org.brinst.couponcore.exception.CouponIssueException;
import org.brinst.couponcore.model.Coupon;
import org.brinst.couponcore.model.CouponIssue;
import org.brinst.couponcore.reopsitory.mysql.CouponIssueJpaRepository;
import org.brinst.couponcore.reopsitory.mysql.CouponIssueRepository;
import org.brinst.couponcore.reopsitory.mysql.CouponJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponIssueService {
	private final CouponJpaRepository couponJpaRepository;
	private final CouponIssueJpaRepository couponIssueJpaRepository;
	private final CouponIssueRepository couponIssueRepository;

	@Transactional
	public void issue(long couponId, long userId) {
		Coupon coupon = findCouponWithLock(couponId);
		coupon.issue();
		saveCouponIssue(couponId, userId);
	}

	@Transactional(readOnly = true)
	public Coupon findCoupon(long couponId) {
		return couponJpaRepository.findById(couponId).orElseThrow(()
			-> new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId)));
	}

	@Transactional(readOnly = true)
	public Coupon findCouponWithLock(long couponId) {
		return couponJpaRepository.findCouponWithLock(couponId).orElseThrow(()
			-> new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId)));
	}

	@Transactional
	public CouponIssue saveCouponIssue(long couponId, long userId) {
		checkAlreadyIssuance(couponId,userId);
		CouponIssue issue = CouponIssue.builder()
			.couponId(couponId)
			.userId(userId)
			.build();
		return couponIssueJpaRepository.save(issue);
	}

	private void checkAlreadyIssuance(long couponId, long userId) {
		CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
		if (issue != null) {
			throw new CouponIssueException(DUPLICATED_COUPON_ISSUE,
				"이미 발급된 쿠폰입니다. user_id: %s, coupon_id: %s".formatted(userId, couponId));
		}
	}
}
