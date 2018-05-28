package net.helpscout.playaway.web;

import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

@Aspect
public abstract class UrlParameterGuardian {

    @Before("execution(public * (@net.helpscout.playaway.stereotype.ReadingController *).*(..))")
    public void checkReadingAccess(JoinPoint jp) {
        val parameters = parameters(jp);
        checkUrlVariables(parameters);
    }

    @Before("execution(public * (@net.helpscout.playaway.stereotype.WritingController *).*(..))")
    public void checkWritingAccess(JoinPoint jp) {
        val parameters = parameters(jp);
        checkUrlVariables(parameters);
    }

    private Map<String, Object> parameters(JoinPoint jp) {
        String[] paramNames = parameterNames(jp);
        Object[] args = jp.getArgs();

        val parameters = new HashMap<String, Object>();
        for (int i = 0; i < paramNames.length; i++) {
            parameters.put(paramNames[i], args[i]);
        }
        return parameters;
    }

    private String[] parameterNames(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return signature.getParameterNames();
    }

    protected abstract void checkUrlVariables(Map<String, Object> parameters);
}
