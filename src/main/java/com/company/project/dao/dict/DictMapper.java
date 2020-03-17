package com.company.project.dao.dict;

import com.company.project.core.Mapper;
import com.company.project.model.dict.Dict;

public interface DictMapper extends Mapper<Dict> {
    Integer addDict(Dict dict);
}
