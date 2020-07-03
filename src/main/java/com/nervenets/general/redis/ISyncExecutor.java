package com.nervenets.general.redis;

public interface ISyncExecutor<R, P> {
    R execute(P p) throws Exception;
}
