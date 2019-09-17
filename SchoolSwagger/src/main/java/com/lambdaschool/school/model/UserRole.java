package com.lambdaschool.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userrole")
public class UserRole extends Auditable implements Serializable
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"userRole", "hibernateLazyInitializer"})
    @JoinColumn(name = "userid")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid")
    @JsonIgnoreProperties({"userRole", "hibernateLazyInitializer"})
    private Role role;

    public UserRole()
    {
    }

    public UserRole(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserRole))
        {
            return false;
        }
        UserRole UserRole = (UserRole) o;
        return getUser().equals(UserRole.getUser()) && getRole().equals(UserRole.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(), getRole());
    }
}