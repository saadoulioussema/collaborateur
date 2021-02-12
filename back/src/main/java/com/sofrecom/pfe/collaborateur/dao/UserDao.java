package com.sofrecom.pfe.collaborateur.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sofrecom.pfe.collaborateur.model.*;


@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {
	DAOUser findByUsername(String username);
}