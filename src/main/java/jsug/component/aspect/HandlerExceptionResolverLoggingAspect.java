package jsug.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect // (1)
@Slf4j
public class HandlerExceptionResolverLoggingAspect {
    @Around("execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))") // (2)
    public Object logException(ProceedingJoinPoint joinPoint /* (3) */) throws Throwable {
        Object ret = joinPoint.proceed();
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0]; // (1)

        if (request.getAttribute("ERROR_LOGGED") == null) {
            Object handler = joinPoint.getArgs()[2];
            Exception exception = (Exception) joinPoint.getArgs()[3];
            log.info("Error occurred [url=" + request.getMethod() + " " + request.getRequestURI() + ", handler=" + handler + "]",
                    exception);
            // mark as logged
            request.setAttribute("ERROR_LOGGED", true); // (4)
        }
        return ret;
    }
}