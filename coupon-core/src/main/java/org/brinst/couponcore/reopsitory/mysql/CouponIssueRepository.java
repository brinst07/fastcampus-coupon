package org.brinst.couponcore.reopsitory.mysql;

import static org.brinst.couponcore.model.QCouponIssue.*;

import org.brinst.couponcore.model.CouponIssue;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {
	private final JPQLQueryFactory queryFactory;

	public CouponIssue findFirstCouponIssue(long couponId, long userId) {
		return queryFactory.selectFrom(couponIssue)
			.where(couponIssue.couponId.eq(couponId))
			.where(couponIssue.userId.eq(userId))
			.fetchFirst();
	}
}
