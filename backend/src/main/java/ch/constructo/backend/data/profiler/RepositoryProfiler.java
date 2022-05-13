package ch.constructo.backend.data.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class RepositoryProfiler {

  private static Logger log = LoggerFactory.getLogger(RepositoryProfiler.class);

  @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..)) || " +
      "execution(public * com.querydsl.jpa.impl.AbstractJPAQuery.fetch*(..)) || " +
      "execution(public * com.querydsl.jpa.impl.AbstractJPAQuery.getResult*(..) ) || " +
      "execution(public * com.querydsl.jpa.impl.AbstractJPAQuery.getSingle*() )")
  public void intercept() {
  }

  @Around("intercept()")
  public Object profile(ProceedingJoinPoint joinPoint) {

    long startMs = System.currentTimeMillis();



    Object result = null;
    try {
      result = joinPoint.proceed();
    } catch (Throwable e) {
      Signature signature = joinPoint.getSignature();
      Object[] args = joinPoint.getArgs();      String arguments = Arrays.stream(args).map( n -> n!=null ? n.toString() : "null").collect( Collectors.joining( "," ) );
      String msg = "fails to execute: " + joinPoint.getTarget().getClass().getSimpleName() + "." + signature + " [" + arguments +  "] ";
      log.warn(msg);
      if(e instanceof ConstraintViolationException){
        throw (ConstraintViolationException)e;
      } else if(e instanceof DataIntegrityViolationException){
        throw (DataIntegrityViolationException)e;
      } else if( e instanceof ObjectOptimisticLockingFailureException){
        throw (ObjectOptimisticLockingFailureException)e;
      }


      throw new RuntimeException(msg, e);
    } finally {
      long elapsedMs = System.currentTimeMillis() - startMs;

      if(log.isDebugEnabled()) {
        // you may like to use logger.debug
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if(args!=null){
          String arguments = Arrays.stream(args).map(n -> n!=null ? n.toString() : "null").collect( Collectors.joining( "," ) );

          log.debug(joinPoint.getTarget().getClass().getSimpleName() + "." + signature + " [" + arguments +  "] " + " executed in " + elapsedMs + " ms");

        } else {
          log.debug(joinPoint.getTarget().getClass().getSimpleName() + "." + signature + " executed in " + elapsedMs + " ms");
        }
      }

    }

    return result;
  }
}
