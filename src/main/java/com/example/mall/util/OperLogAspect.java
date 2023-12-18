package com.example.mall.util;

import com.alibaba.fastjson.JSON;
import com.example.mall.modules.records.entity.UserAccessRecords;
import com.example.mall.modules.records.services.UserAccessRecordsService;
import com.example.mall.modules.user.entity.bo.UserBO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 切面处理类，操作日志异常日志记录处理
 *
 * @author wu
 * @date 2019/03/21
 */
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private UserAccessRecordsService userAccessRecordsService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.example.mall.annotation.OperLog)")
    public void operLogPoinCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有com.example.mall.modules下边包下边的controller包下的controller类的所有方法
     */
    @Pointcut("execution(* com.example.mall.modules..controller.*.*(..))")
    public void operExceptionLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     */
    @Before("operLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        try {
//            // 从切面织入点处通过反射机制获取织入点处的方法
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            // 获取切入点所在的方法
//            Method method = signature.getMethod();
//            // 获取操作
//            OperLog opLog = method.getAnnotation(OperLog.class);
//            if (opLog != null) {
//                String operModul = opLog.operModul(); // 操作模块
//                String operType = opLog.operType(); // 操作类型
//                String operDesc = opLog.operDesc(); // 操作描述
//            }
//            // 获取请求的类名
//            String className = joinPoint.getTarget().getClass().getName();
//            // 获取请求的方法名
//            String methodName = method.getName();
//            methodName = className + "." + methodName;

            UserBO userInfo = (UserBO) request.getAttribute("userInfo");
            String url = request.getRequestURI();
            insertUserAccessRecords(request, joinPoint, userInfo, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
//        // 获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        // 从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes
//                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
//
//        ExceptionLog excepLog = new ExceptionLog();
//        try {
//            // 从切面织入点处通过反射机制获取织入点处的方法
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            // 获取切入点所在的方法
//            Method method = signature.getMethod();
//            excepLog.setExcId(UuidUtil.get32UUID());
//            // 获取请求的类名
//            String className = joinPoint.getTarget().getClass().getName();
//            // 获取请求的方法名
//            String methodName = method.getName();
//            methodName = className + "." + methodName;
//            // 请求的参数
//            Map<String, String> rtnMap = converMap(request.getParameterMap());
//            // 将参数所在的数组转换成json
//            String params = JSON.toJSONString(rtnMap);
//            excepLog.setExcRequParam(params); // 请求参数
//            excepLog.setOperMethod(methodName); // 请求方法名
//            excepLog.setExcName(e.getClass().getName()); // 异常名称
//            excepLog.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
//            excepLog.setOperUserId(UserShiroUtil.getCurrentUserLoginName()); // 操作员ID
//            excepLog.setOperUserName(UserShiroUtil.getCurrentUserName()); // 操作员名称
//            excepLog.setOperUri(request.getRequestURI()); // 操作URI
//            excepLog.setOperIp(IPUtil.getRemortIP(request)); // 操作员IP
//            excepLog.setOperVer(operVer); // 操作版本号
//            excepLog.setOperCreateTime(new Date()); // 发生异常时间
//
//            exceptionLogService.insert(excepLog);
//
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }

    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

    /**
     * 获取用户访问记录
     */
    private void insertUserAccessRecords(HttpServletRequest request, JoinPoint joinPoint, UserBO user, String url) throws IOException {
        UserAccessRecords userAccessRecords = new UserAccessRecords();
        userAccessRecords.setUserId(user.getId());
        userAccessRecords.setUri(url);
        userAccessRecords.setCreateTime(new Date());
        userAccessRecords.setParams(getHeaderLog(request, joinPoint));
        userAccessRecordsService.insert(userAccessRecords);
    }

    private String getHeaderLog(HttpServletRequest request, JoinPoint pjp) {
        //取参数，打印参数
        if (request.getMethod().equals("GET")){
            return request.getQueryString();
        }
        if (request.getMethod().equals("POST")){
            //这么取，取出来的是方法的入参，不仅仅是body里的参数，body只是其中一项
            Object[] arguments = pjp.getArgs();
            if (arguments.length == 0) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Object argument : arguments) {
                // 这样不仅过滤掉了HttpServletRequest，toJson的时候还会去掉为空的字段
                if (argument instanceof HttpServletRequest) {
                    continue;
                }
                stringBuilder.append(JSON.toJSON(argument));
            }
            return stringBuilder.toString();
        }
        return null;
    }

}