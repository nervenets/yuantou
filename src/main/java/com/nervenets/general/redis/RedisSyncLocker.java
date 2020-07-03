package com.nervenets.general.redis;

import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.service.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisSyncLocker<R, P> {
    private static final String LOCKED_KEY_PREFIX = "_LOCK(%s)";
    private static final int TIMEOUT_MILLI_SECONDS = 60000;
    @Resource
    private RedisService redisService;

    /**
     * 同步方法块
     *
     * @param lockedKey 加锁的KEY（自由定制）
     * @param executor  通知执行实现
     * @param o         输入参数
     * @return 同步返回参数
     * @throws Exception
     */
    public R keyLock(String lockedKey, ISyncExecutor<R, P> executor, P o) throws Exception {
        String lockKey = String.format(LOCKED_KEY_PREFIX, lockedKey);
        long startTime = System.currentTimeMillis();
        Boolean canProcess = redisService.setNx(lockKey, lockKey);
        while (!canProcess) {
            if (System.currentTimeMillis() - startTime > TIMEOUT_MILLI_SECONDS) {
                redisService.delete(lockKey);
                throw new NerveNetsGeneralException("同步方法块超时，请稍后再试");
            }
            canProcess = redisService.setNx(lockKey, lockKey);
            Thread.sleep(10);
        }
        R result;

        try {
            result = executor.execute(o);
            redisService.delete(lockKey);
        } catch (Exception ex) {
            redisService.delete(lockKey);
            throw ex;
        }
        return result;
    }

    /**
     * 同步方法块
     *
     * @param id       加锁的KEY（自由定制）
     * @param executor 通知执行实现
     * @return 同步返回参数
     * @throws Exception
     */
    public R idLock(long id, ISyncExecutor<R, P> executor) throws Exception {
        return this.keyLock(String.format("_id(%s)", id), executor, null);
    }

    public R keyLock(String id, ISyncExecutor<R, P> executor) throws Exception {
        return this.keyLock(String.format("_id(%s)", id), executor, null);
    }
}
