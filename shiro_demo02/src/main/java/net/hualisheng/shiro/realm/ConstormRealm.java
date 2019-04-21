package net.hualisheng.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ConstormRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("授权");
        String userName = (String) principals.getPrimaryPrincipal();
        List<String> permissionList=new LinkedList<String>();
        permissionList.add("user:add");
        permissionList.add("user:delete");
        permissionList.add("user:create");

        if ("zhou".equals(userName)){
            permissionList.add("user:query");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Object> keys = (List<Object>) session.getAttributeKeys();
        for (Object key : keys) {
            System.err.println(key+"::"+session.getAttribute(key));
        }
        PrincipalCollection principals1 = subject.getPrincipals();
        System.err.println("p"+"::"+principals1);


        //Simple Authorization Info 简单的授权信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissionList);
        authorizationInfo.addStringPermission("admin");
        authorizationInfo.addRole("admin");
        authorizationInfo.addRole("administrator");
        authorizationInfo.addRole("create");
        authorizationInfo.addRole("ROLE_ADMIN");
        List<String> roles=new LinkedList<>();
        roles.add("ROLE_SERVICE");

        authorizationInfo.addRoles(roles);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        System.err.println("认证");
        if ("".equals(principal)){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, "123456", this.getName());
        return authenticationInfo;
    }
}
