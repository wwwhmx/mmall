package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by www on 2019/8/19.
 */
public interface ICategoryService {
    public ServerResponse addCategory(String categoryName, Integer parentId);

    public ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);


}
