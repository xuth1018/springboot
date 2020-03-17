package com.company.project.service.dict;

import com.company.project.core.Result;
import com.company.project.model.dict.Dict;

import java.util.List;

public interface DictService {
    /**
     * 字典添加数据
     * @param dict
     * @return
     */
    Result addDict(List<Dict> dict);

    /**
     * 字典重新放入缓存
     * @return
     */
    Result refreshRedis();

    /**
     * 获取字典
     * @param key
     * @return
     */
    Result getDictFromRedis(String key);
}
