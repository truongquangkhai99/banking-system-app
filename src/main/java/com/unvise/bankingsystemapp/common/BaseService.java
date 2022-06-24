package com.unvise.bankingsystemapp.common;

import java.io.Serializable;
import java.util.List;

public interface BaseService<Dto, ID extends Serializable> {

    List<Dto> getAll();

    Dto getById(ID id);

    Dto save(Dto dto);

    Dto updateById(ID id, Dto dto);

    Dto deleteById(ID id);
}
