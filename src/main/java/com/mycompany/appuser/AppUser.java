/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appuser;

import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author layea
 */
@Entity
@Table(name = "APPUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppUser.findAll", query = "SELECT a FROM AppUser a"),
    @NamedQuery(name = "AppUser.findById", query = "SELECT a FROM AppUser a WHERE a.id = :id"),
    @NamedQuery(name = "AppUser.findByUserId", query = "SELECT a FROM AppUser a WHERE a.userId = :userId"),
    @NamedQuery(name = "AppUser.findByPassword", query = "SELECT a FROM AppUser a WHERE a.password = :password"),
    @NamedQuery(name = "AppUser.findByGroupname", query = "SELECT a FROM AppUser a WHERE a.groupName = :groupName")
})
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "USERID")
    private String userId;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "GROUPNAME")
    private String groupName;

    public AppUser() {
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return "";
    }
/**
 * Function to salt/hash a user given password before it is stored in DB
 * @param password New or changed password entry to be hashed
 */
    public void setPassword(String password) {
        if (password.length() < 1) {
            return;
        }
        // initialize a PasswordHash object which will generate password hashes
        Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
        PasswordHash passwordHash = instance.get();
        passwordHash.initialize(new HashMap<>());
        // now we can generate a password entry for a given password
        password = passwordHash.generate(password.toCharArray());
        //at this point, password var refers to a salted/hashed password entry corresponding to the given password
        this.password = password;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.appuser.AppUser[ id=" + id + " ]";
    }

}
