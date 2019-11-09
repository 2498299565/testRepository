package com.demo.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomRealm extends AuthorizingRealm{
    @Override
    public void setName(String name)
    {
        super.setName("custom");
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       /* //获取用户名
        String username = (String) token.getPrincipal();
        //获取密码
        String password="456";

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password,this.getName());

        return authenticationInfo;*/


        //获取用户名
        String username = (String) token.getPrincipal();
        //获取密码
        String password="456";

        //对密码进行加密
        String salt="wzs";
        int hashIterations=3;
        //给“666”进行加密，salt表示 加密时的佐料，hashIterations表示加密的次数
        Md5Hash hash = new Md5Hash(password,salt,hashIterations);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, hash.toString(),
                ByteSource.Util.bytes(salt), this.getName());

        return authenticationInfo;
    }
}
