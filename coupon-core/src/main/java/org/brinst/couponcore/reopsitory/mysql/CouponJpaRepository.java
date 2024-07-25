package org.brinst.couponcore.reopsitory.mysql;

import java.util.Optional;

import org.brinst.couponcore.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c FROM Coupon  c WHERE c.id = :id")
	Optional<Coupon> findCouponWithLock(long id);
}
