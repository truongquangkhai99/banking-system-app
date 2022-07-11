package com.unvise.bankingsystemapp.domain.common;

import com.unvise.bankingsystemapp.exception.resource.ResourceException;

import java.io.Serializable;
import java.util.List;

public interface BaseService<Dto, ID extends Serializable> {

    List<Dto> getAll();

    Dto getById(ID id) throws ResourceException;

    Dto save(Dto dto) throws ResourceException;

    Dto updateById(ID id, Dto dto) throws ResourceException;

    Dto deleteById(ID id) throws ResourceException;
}
