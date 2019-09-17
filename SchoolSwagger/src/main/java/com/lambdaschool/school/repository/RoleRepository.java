package com.lambdaschool.school.repository;

import com.lambdaschool.school.model.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    @Transactional
    @Modifying
    @Query(value = "DELETE from UserRole where userid = :userid")
    void deleteUserRoleByUserId(long userid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO UserRole(userid, roleid) values (:userid, :roleid)", nativeQuery = true)
    void insertUserRole(long userid, long roleid);

}