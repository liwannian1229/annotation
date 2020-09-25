package com.lwn.interceptor;//package com.lwn.annotation.annotationdemo.interceptor;
//
//import com.lwn.annotation.annotationdemo.annotation.MyAnnotation;
//import com.lwn.annotation.annotationdemo.exception.AnnotationException;
//import com.lwn.annotation.annotationdemo.token.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
///**
// * @author liwannian
// * @date 2020年9月11日17点58分17点58分
// *
// */
//@Component
//public class MyInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        if (!(handler instanceof HandlerMethod)) {
//
//            return true;
//        }
//        Method method = ((HandlerMethod) handler).getMethod();
//        MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
//
//        if (myAnnotation != null) {
//            try {
//                tokenService.checkToken(request);
//            } catch (AnnotationException e) {
//                throw e;
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
