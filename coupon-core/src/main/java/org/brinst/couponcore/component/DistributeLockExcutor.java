package org.brinst.couponcore.component;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class DistributeLockExcutor {
	private final RedissonClient redissonClient;

	public void execute(String lockName, long waitMilliSecond, long leaseMilliSecond, Runnable runnable) {
		RLock lock = redissonClient.getLock(lockName);
		try {
			boolean isLocked = lock.tryLock(waitMilliSecond, leaseMilliSecond, TimeUnit.MILLISECONDS);
			if (!isLocked) {
				throw new IllegalStateException("[" + lockName + "] lock 획득 실패");
			}
			runnable.run();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}

	}
}
