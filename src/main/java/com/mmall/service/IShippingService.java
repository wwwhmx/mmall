package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by www on 2019/9/7.
 */
public interface IShippingService {
    public ServerResponse add(Integer userId, Shipping shipping);

    public ServerResponse<String> del(Integer userId, Integer shippingId);

    public ServerResponse update(Integer userId, Shipping shipping);

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
