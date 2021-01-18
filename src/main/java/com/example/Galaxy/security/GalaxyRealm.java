package com.example.Galaxy.security;

import com.example.Galaxy.entity.SysRole;
import com.example.Galaxy.entity.SysUser;
import com.example.Galaxy.service.SystemService;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.service.RedisService;
import com.example.Galaxy.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


import java.util.List;

public class GalaxyRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private RedisService redisService;

    @Lazy
    @Autowired
    private SystemService systemService;

    @Lazy
    @Autowired
    private UserService userService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = JWTUtil.getAccount(principals.toString());
        SysUser sysUser = userService.selectByAccount(account);
        List<SysRole> sysRoles = systemService.selectRoleByUserId(sysUser.getUserId());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        sysRoles.forEach(item -> {
            simpleAuthorizationInfo.addRole(item.getRoleName());
        });
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String account = JWTUtil.getAccount(token);
        if (account == null) {
            throw new AuthenticationException();
        }

        SysUser sysUser = userService.selectByAccount(account);
        if (sysUser == null) {
            throw new AuthenticationException();
        }

        if (!JWTUtil.verify(token, account, sysUser.getPasswd())) {
            throw new AuthenticationException();
        }

        //判断redis是否有缓存，没有缓存则认为已经注销登录
        if (redisService.hget("token", sysUser.getUserId().toString()) == null) {
            throw new AuthenticationException();
        }

        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
