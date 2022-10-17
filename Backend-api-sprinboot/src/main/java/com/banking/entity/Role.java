package com.banking.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    private String roleName;
    private String roleDescription;
    public Role(String roleName, String roleDescription) {
        super();
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }
    
    
    

}
