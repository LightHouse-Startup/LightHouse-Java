package light.house.redis.distributed.lock.util;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的分布式锁
 */
@Component("redisDistributedLockUtil")
public class RedisDistributedLockUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Redisson redisson;

    /**
     * 加锁
     * @param key
     * @param leaseTime  自动释放时间
     * @return
     */
    public boolean lock(String key,long leaseTime){
        RLock lock = redisson.getFairLock(key);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return  true;
    }

    /**
     * 尝试加锁
     * @param key
     * @param unit
     * @param waitTime  等待时间
     * @param leaseTime 自动释放时间
     * @return
     */
    public boolean trylock(String key, TimeUnit unit, int waitTime, int leaseTime){
        RLock lock = redisson.getLock(key);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试加锁，使用默认配置 等待时间 自动释放时间
     * @param key
     * @return
     */
    public boolean tryDefaultLock(String key){
        RLock lock = redisson.getLock(key);
        try {
            return lock.tryLock(3, 180, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 释放锁
     * @param key
     */
    public void unlock(String key){
        logger.info("释放锁: "+key);
        RLock lock = redisson.getLock(key);
        unlock(lock);
    }

    /**
     * 释放锁
     * @param lock
     */
    public void unlock(RLock lock){
        try {
            lock.unlock();
        }catch (Exception ex){
            logger.error("锁已经不存在",ex);
        }

    }

}
