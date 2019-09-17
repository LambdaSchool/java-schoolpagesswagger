package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.User;
import com.lambdaschool.school.model.UserRole;
import com.lambdaschool.school.repository.RoleRepository;
import com.lambdaschool.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userrepos.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id)
    {
        if (userrepos.findById(id).isPresent())
        {
            userrepos.deleteById(id);
        }
        else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRole> newRoles = new ArrayList<>();
        for (UserRole ur : user.getUserRole())
        {
            newRoles.add(new UserRole(newUser, ur.getRole()));
        }
        newUser.setUserRole(newRoles);

        return userrepos.save(newUser);
    }

    @Override
    public User findUserByName(String name)
    {
        User currentUser = userrepos.findByUsername(name);

        if (currentUser != null)
        {
            return currentUser;
        }
        else
        {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public User update(User user, long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        if (currentUser != null)
        {
            if (id == currentUser.getUserid())
            {
                if (user.getUsername() != null)
                {
                    currentUser.setUsername(user.getUsername());
                }

                if (user.getPassword() != null)
                {
                    currentUser.setPasswordNoEncrypt(user.getPassword());
                }

                if (user.getUserRole().size() > 0)
                {
                    // with so many relationships happening, I decided to go
                    // with old school queries
                    // delete the old ones
                    rolerepos.deleteUserRoleByUserId(currentUser.getUserid());

                    // add the new ones
                    for (UserRole ur : user.getUserRole())
                    {
                        rolerepos.insertUserRole(id, ur.getRole().getRoleid());
                    }
                }

                return userrepos.save(currentUser);
            }
            else
            {
                throw new ResourceNotFoundException(Long.toString(id) + " Not current user");
            }
        }
        else
        {
            throw new ResourceNotFoundException(authentication.getName());
        }

    }
}