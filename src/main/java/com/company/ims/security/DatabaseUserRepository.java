package com.company.ims.security;

import com.company.ims.entity.User;
import io.jmix.core.DataManager;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Primary
@Component("UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    @Autowired
    DataManager dataManager;

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void initSystemUser(User systemUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccessRole.CODE)
                .build();
        systemUser.setAuthorities(authorities);
    }

    public Collection<GrantedAuthority> getAuthorityByCode(String code) {
        return getGrantedAuthoritiesBuilder()
                .addResourceRole(code)
                .build();
    }

    public void addResourceRoleToUser(User user, String roleCode) {
        RoleAssignmentEntity roleAssignmentEntity = dataManager.create(RoleAssignmentEntity.class);
        roleAssignmentEntity.setRoleCode(roleCode);
        roleAssignmentEntity.setUsername(user.getUsername());
        roleAssignmentEntity.setRoleType(RoleAssignmentRoleType.RESOURCE);
        dataManager.save(roleAssignmentEntity);
    }

    @Override
    protected void initAnonymousUser(User anonymousUser) {
    }
}