package com.slackcollection.config.aop;

import java.util.Arrays;
import java.util.Locale;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class HTTPLoggingAspect {

  private final ObjectMapper objectMapper;

  public HTTPLoggingAspect(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void restControllerBeanPointcut() {
  }

  @Around("restControllerBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

    String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName().toLowerCase(Locale.ROOT);
    boolean isNotController = !declaringTypeName.contains("controller");
    boolean isNotRouter = !declaringTypeName.contains("router");

    if (isNotController && isNotRouter) {
      return joinPoint.proceed();
    }
    long startAt = System.currentTimeMillis();

    String requestInfo = String.format("[%s] %s(%s) ", joinPoint.getSignature().getDeclaringType().getSimpleName(),
        joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

    log.info("\n");
    log.info("\n=====>>> [REQUEST] {}", requestInfo);
    try {
      Object result = joinPoint.proceed();

      long endAt = System.currentTimeMillis();

      String responseInfo = String.format("[%s] %s (%s ms)",
          joinPoint.getSignature().getDeclaringType().getSimpleName(),
          joinPoint.getSignature().getName(), endAt - startAt);

      log.info("\n");
      log.info("\n<<<===== [RESPONSE] {} ", responseInfo);

      if (result instanceof ResponseEntity) {
        Object resultBody = ((ResponseEntity<?>) result).getBody();
        log.info("ResponseData: {}", objectMapper.writeValueAsString(resultBody));

      }

      log.info("============================ [EndRequest] ============================");
      log.info("\n");

      return result;
    } catch (Exception e) {
      log.error("Exception: {} in {}()", Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getName());
      throw e;
    }
  }
}