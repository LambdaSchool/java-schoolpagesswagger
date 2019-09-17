package com.lambdaschool.school;

import com.lambdaschool.school.model.Role;
import com.lambdaschool.school.model.User;
import com.lambdaschool.school.model.UserRole;
import com.lambdaschool.school.repository.RoleRepository;
import com.lambdaschool.school.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{

    RoleRepository rolerepos;
    UserRepository userrepos;


    public SeedData(RoleRepository rolerepos, UserRepository userrepos) {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;

    }

    @Override
    public void run(String[] args) throws Exception {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        rolerepos.save(r1);
        rolerepos.save(r2);

        ArrayList<UserRole> users = new ArrayList<>();
        users.add(new UserRole(new User(), r2));
        User u1 = new User("barnbarn", "ILuvM4th!", users);
        userrepos.save(u1);

        ArrayList<UserRole> admins = new ArrayList<>();
        admins.add(new UserRole(new User(), r1));
        admins.add(new UserRole(new User(), r2));
        User u2 = new User("admin", "password", admins);
        userrepos.save(u2);

        users = new ArrayList<>();
        users.add(new UserRole(new User(), r2));
        User u3 = new User("Bob", "password", users);
        userrepos.save(u3);

        users = new ArrayList<>();
        users.add(new UserRole(new User(), r2));
        User u4 = new User("Jane", "password", users);
        userrepos.save(u4);
    }
}