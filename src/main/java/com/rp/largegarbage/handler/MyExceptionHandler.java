//package com.rp.largegarbage.handler;
//
//import com.alibaba.fastjson.support.spring.FastJsonJsonView;
//import org.apache.shiro.authz.UnauthenticatedException;
//import org.apache.shiro.authz.UnauthorizedException;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
///**
// * @Description
// * @Author liulida <2979284403@qq.com>
// * @Version v1.0.0
// * @Since 1.0
// * @Date 2020/6/23 17:45
// */
//public class MyExceptionHandler implements HandlerExceptionResolver {
//    @Override
//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        ModelAndView mv = new ModelAndView();
//        FastJsonJsonView view = new FastJsonJsonView();
//        Map<String, Object> attributes = new HashMap<String, Object>();
//        if (e instanceof UnauthenticatedException) {
//            attributes.put("code", "1000001");
//            attributes.put("msg", "token错误");
//        } else if (e instanceof UnauthorizedException) {
//            attributes.put("code", "1000002");
//            attributes.put("msg", "用户无权限");
//        } else {
//            attributes.put("code", "1000003");
//            attributes.put("msg", e.getMessage());
//        }
//        view.setAttributesMap(attributes);        mv.setView(view);
//        return mv;
//    }
//}
