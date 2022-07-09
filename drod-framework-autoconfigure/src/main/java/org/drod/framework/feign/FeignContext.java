package org.drod.framework.feign;


import org.drod.framework.web.exception.BizException;

/**
 * feign上下文对象，主要用来传递bizException等信息
 *
 * @author e9
 */
public class FeignContext {

    private static final ThreadLocal<BizException> threadLocal = new ThreadLocal<BizException>();

    /**
     * 获取异常信息
     *
     * @return
     */
    public static BizException getBizException() {
        return threadLocal.get();
    }

    /**
     * 设置异常信息
     *
     * @param e
     */
    public static void setBizException(BizException e) {
        threadLocal.set(e);
    }


    /**
     * 清除异常信息
     */
    public static void clearException() {
        threadLocal.remove();
    }

}
