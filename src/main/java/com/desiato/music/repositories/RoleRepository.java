package com.desiato.music.repositories;

import com.desiato.music.models.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    boolean existsByName(String roleName);
    Role findRoleByName(String roleName);

}
