package org.brinst.couponcore.reopsitory.mysql;

import org.brinst.couponcore.model.Coupon;
import org.brinst.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}
