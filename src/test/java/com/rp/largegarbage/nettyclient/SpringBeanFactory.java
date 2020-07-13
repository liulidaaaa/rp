package com.rp.largegarbage.nettyclient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 22:25
 */
@Component
public final class SpringBeanFactory implements ApplicationContextAware {
    private static ApplicationContext context;
    public static <T> T getBean(Class<T> c) {
        return context.getBean(c);
    }
    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}

