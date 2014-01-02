/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.aspect;

/**
 *
 * @author zannami
 */
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("cacheAspect")
@Aspect
@Order(2)
public class CacheAspect {

    @Resource(name = "cacheSessionValue")
    private Cache cache;

    @Around("execution(* com.vss.cardservice.api.ICardService.useCard(com.vss.cardservice.dto.Transaction))")
    public Object cacheSessionValue(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        String location = (String) arguments[0];
        Element element = cache.get(location);
        if (null == element) {
            Object result = joinPoint.proceed();
            if (null != result) {
                element = new Element(location, result);
                cache.put(element);
            }
        }
        return element != null ? element.getValue() : null;
    }
}
