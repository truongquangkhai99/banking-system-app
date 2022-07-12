package com.unvise.bankingsystemapp.domain.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * The interface from which all repositories interfaces must be inherited.
 *
 * @param <T> Entity class
 * @param <ID> Id class
 * @author unvise
 * @version 0.1
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
}
