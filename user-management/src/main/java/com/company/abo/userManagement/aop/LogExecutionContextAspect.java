package com.company.abo.userManagement.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Class for the AOP to log execution duration, method parameters, ...
 * @author ABO
 *
 */
@Aspect
@Component
@Slf4j
public class LogExecutionContextAspect {
	
	/**
	 * Log the execution context for a method : duration, input parameters, output parameters, ..., 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(LogExecutionContext)")
	public Object logExceutionContext(ProceedingJoinPoint joinPoint) throws Throwable {
		final long start = System.currentTimeMillis();
		final Signature methodSignature = joinPoint.getSignature();
		log.debug("Call {} Input : {} ", methodSignature.getDeclaringTypeName(), joinPoint.getArgs());
		final Object result = joinPoint.proceed();
		
		final long end = System.currentTimeMillis();
		long duration = end - start;
		log.debug("Call {} Output : {} ", methodSignature.getDeclaringTypeName(), result);
		log.debug("Execution of {} taks {} ms", methodSignature, duration);
		return result;
	}
}
