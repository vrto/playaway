package net.helpscout.playaway.data;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Intercepts "logical transaction" beginning at the controller level and binds it into a particular data source (master or slave).
 * By logical transaction we mean the whole request-process-response cycle which happens within a single thread.
 */
@Order(5)
@Aspect
@Component
public class RoutingDataSourceTransactionInterceptor {

    @Pointcut("@within(net.helpscout.playaway.stereotype.ReadingController)")
    private void inReadingController() {}

    @Around("inReadingController()")
    public Object proceedFromReadingController(ProceedingJoinPoint pjp) throws Throwable {
        return useSlaveReplica(pjp);
    }

    private Object useSlaveReplica(ProceedingJoinPoint pjp) throws Throwable {
        try {
            DbContextHolder.useSlave();
            return pjp.proceed();
        } finally {
            DbContextHolder.reset();
        }
    }
}
