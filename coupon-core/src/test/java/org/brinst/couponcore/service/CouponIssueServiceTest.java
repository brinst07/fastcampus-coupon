package org.brinst.couponcore.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.brinst.couponcore.TestConfig;
import org.brinst.couponcore.exception.CouponIssueException;
import org.brinst.couponcore.exception.ErrorCode;
import org.brinst.couponcore.model.Coupon;
import org.brinst.couponcore.model.CouponIssue;
import org.brinst.couponcore.model.CouponType;
import org.brinst.couponcore.reopsitory.mysql.CouponIssueJpaRepository;
import org.brinst.couponcore.reopsitory.mysql.CouponIssueRepository;
import org.brinst.couponcore.reopsitory.mysql.CouponJpaRepository;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CouponIssueServiceTest extends TestConfig {
	@Autowired
	CouponIssueService sut;
	@Autowired
	CouponIssueJpaRepository couponIssueJpaRepository;
	@Autowired
	CouponIssueRepository couponIssueRepository;
	@Autowired
	CouponJpaRepository couponJpaRepository;

	@BeforeEach
	void clean() {
		couponJpaRepository.deleteAllInBatch();
		couponIssueJpaRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("쿠폰 발급 내역이 존재하면 예외를 반환한다.")
	void saveCouponIssue_1() {
		//given
		CouponIssue couponIssue = CouponIssue.builder()
			.couponId(1L)
			.userId(1L)
			.build();
		couponIssueJpaRepository.save(couponIssue);
		//when & then
		CouponIssueException exception = assertThrows(CouponIssueException.class,
			() -> sut.saveCouponIssue(couponIssue.getCouponId(), couponIssue.getUserId()));
		Assertions.assertEquals(exception.getErrorCode(), ErrorCode.DUPLICATED_COUPON_ISSUE);
	}

	@Test
	@DisplayName("쿠폰 발급 내역이 존재하지 않는다면 쿠폰을 발급한다.")
	void saveCouponIssue_2() {
		//given
		long couponId = 1L;
		long userId = 1L;
		//when
		CouponIssue couponIssue = sut.saveCouponIssue(couponId, userId);
		//then
		assertTrue(couponIssueJpaRepository.findById(couponIssue.getId()).isPresent());
	}

	@Test
	@DisplayName("발급 수량, 기한, 중복 발급 문제가 없다면 쿠폰을 발급한다.")
	void issue_1() {
	    //given
	    long userId = 1;
		Coupon coupon = Coupon.builder()
			.couponType(CouponType.FIRST_COM_FIRST_SERVED)
			.title("선착순 테스트 쿠폰")
			.totalQuantity(100)
			.issuedQuantity(0)
			.dateIssueStart(LocalDateTime.now().minusDays(1L))
			.dateIssueEnd(LocalDateTime.now().plusDays(1L))
			.build();
		couponJpaRepository.save(coupon);
		//when
		sut.issue(coupon.getId(), userId);
	    //then
		Coupon couponResult = couponJpaRepository.findById(coupon.getId()).get();
		assertEquals(couponResult.getIssuedQuantity(),1);

		CouponIssue couponIssueResult = couponIssueRepository.findFirstCouponIssue(coupon.getId(), userId);
		assertNotNull(couponIssueResult);
	}
}