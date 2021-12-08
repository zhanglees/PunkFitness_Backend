package com.healthclubs.pengke.filter;

import com.alibaba.fastjson.JSON;
import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.utils.CacheService;
import com.healthclubs.pengke.utils.Has256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器：增加重复请求的过滤、话单记录处理
 *
 * @author Daniel.tz
 * @since 2021/12/7
 */
@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final static SimpleDateFormat CURRECTTIME = new SimpleDateFormat("yyyyMMddHHmmss");

    //缓存
    @Autowired
    private  CacheService cacheService ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 请求头校验
        String timestamp = request.getHeader("timestamp");
      //  String sign = request.getHeader("version");
        String sessionKey = request.getHeader("sessionkey");

        //String authorization = request.getHeader("authorization");
       //|| authorization == null || authorization.isEmpty()
        if (timestamp == null || timestamp.isEmpty()
        ||sessionKey == null || sessionKey.isEmpty()) {
            Result result = new Result(1025, "请求头缺失.",null);
            getResponse(result, response);
            return false;
        }

        // 时间戳校验
        if (timestamp.length() != 13) {
            Result result = new Result(1026, "时间戳格式错误.",null);
            getResponse(result, response);
            return false;
        }

        UserInfo userInfo = new UserInfo();

        if(!cacheService.hasKey(sessionKey))
        {
            Result result = new Result(1029, "请重新登录.",null);
            getResponse(result, response);
            return false;
        }

        userInfo = cacheService.getObject(sessionKey,UserInfo.class);

        //校验授权
        /*String checkAuthor = timestamp +"&"+sessionKey+"&"+userInfo.getId();

        String keySecret = Has256.sign(checkAuthor,userInfo.getVersionKey());

        if(!keySecret.equals(authorization))
        {
            Result result = new Result(1030, "验签错误.",null);
            getResponse(result, response);
            return false;
        }*/

        Long requestTime = null;
        try {
            requestTime = Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            Result result = new Result(1026, "时间戳格式错误.",null);
            getResponse(result, response);
            return false;
        }

        // 重复请求校验
        if (cacheService.hasKey(timestamp)) {
            Result result = new Result(1027, "重复请求.",null);
            getResponse(result, response);
            return false;
        }

        cacheService.add(timestamp,timestamp,120, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

        // 计算接口时延
        Map<String, String> strMap =new HashMap<>();
        long beginTime = Long.parseLong(strMap.get("beginTime"));
        long currentTime = System.currentTimeMillis();

        // 获取当前时间
        String currentDate = CURRECTTIME.format(new Date(currentTime));

    }


    /**
     * 构造响应消息体
     *
     * @param result
     * @param response
     * @throws IOException
     */
    private void getResponse(Result result, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;
        out = response.getWriter();
        out.write(JSON.toJSON(result).toString());
        out.flush();
        out.close();
    }
}