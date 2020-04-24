package com.mmall.conctroller.Common;


import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.util.CookiesUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by www on 2020/4/1.
 */
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = CookiesUtil.readLoginToken(httpServletRequest);
        if (!StringUtils.isEmpty(token)) {
            String userJsonStr = RedisShardedPoolUtil.get(token);
            User user = JsonUtil.stringToObj(userJsonStr, User.class);
            if (user != null) {
                RedisShardedPoolUtil.expire(token, Const.RedisCashExtime.REDIS_SESSION_EXITME);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
