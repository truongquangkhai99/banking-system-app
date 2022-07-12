package com.unvise.bankingsystemapp.domain.common;

import com.unvise.bankingsystemapp.exception.resource.ResourceException;

import java.io.Serializable;
import java.util.List;

/**
 * The interface from which all services interfaces must be inherited.
 *
 * @param <Dto> Dto class
 * @param <ID> Id class
 * @author unvise
 * @version 0.1
 */
public interface BaseService<Dto, ID extends Serializable> {

    /**
     * @return List<Dto>. Returns all existing records for the resource from the database
     */
    List<Dto> getAll();

    /**
     * @param id Selected id type
     * @return Returns the existing record for the resource from the database or throws an exception.
     * @throws ResourceException Throws an exception that is associated with a resource. e.g.
     * the resource is not found, the resource already exists, etc.
     */
    Dto getById(ID id) throws ResourceException;

    /**
     * @param dto Selected Dto type
     * @return Returns the saved dto or throws a resource exception
     * @throws ResourceException Throws an exception that is associated with a resource. e.g.
     * the resource is not found, the resource already exists, etc.
     */
    Dto save(Dto dto) throws ResourceException;

    /**
     * @param id Selected id type
     * @param dto Selected Dto type
     * @return Returns an updated dto or throws an exception related to the resource
     * @throws ResourceException Throws an exception that is associated with a resource. e.g.
     * the resource is not found, the resource already exists, etc.
     */
    Dto updateById(ID id, Dto dto) throws ResourceException;

    /**
     * @param id Selected id type
     * @return Returns a remote dto or throws an exception related to the resource
     * @throws ResourceException Throws an exception that is associated with a resource. e.g.
     * the resource is not found, the resource already exists, etc.
     */
    Dto deleteById(ID id) throws ResourceException;
}
