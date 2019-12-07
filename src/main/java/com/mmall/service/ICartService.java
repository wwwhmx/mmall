package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by www on 2019/9/5.
 */
public interface ICartService {
    public ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);

    public ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    public ServerResponse<CartVo> deletProduct(Integer userId, String productIds);

    public ServerResponse<CartVo> list(Integer userId);

    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    public ServerResponse<Integer> getCartProductCount(Integer userId);


}
