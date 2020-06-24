package com.rp.largegarbage.config;

import com.rp.largegarbage.entity.Permission;
import com.rp.largegarbage.entity.Role;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.service.ShiroService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    /**
     * 进行权限校验的时候回调用
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权 doGetAuthorizationInfo");
        User newUser = (User)principals.getPrimaryPrincipal();
        User user = shiroService.findByUsername(newUser.getUsername());
        List<String> stringRoleList = new ArrayList<>();
        List<String> stringPermissionList = new ArrayList<>();
        Set<Role> roleList = user.getRoles();
        for(Role role : roleList){
            stringRoleList.add(role.getRoleName());
            Set<Permission> permissions = role.getPermissions();
            for(Permission p: permissions){
                if(p!=null){
                    stringPermissionList.add(p.getPermissionName());
                }
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleList);
        simpleAuthorizationInfo.addStringPermissions(stringPermissionList);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户登录的时候会调用
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("认证 doGetAuthenticationInfo");
        //从token获取用户信息，token代表用户输入
        String username = (String)token.getPrincipal();
        User user =  shiroService.findByUsername(username);
        //取密码
        Integer pwd = user.getPassword();
        if(pwd == null || "".equals(pwd)){
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
