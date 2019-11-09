package com.demo.java;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

public class Demo1 {
    public static void main(String[] args) {
        //创建securityManager工厂对象
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-one.ini");
        //创建securityManager对象
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //设置到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        //创建主体
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("12345", "456");

        //认证
        //ctrl+alt+t  插入一段代码
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        //是否通过
        boolean b = subject.isAuthenticated();

        System.out.println("结果："+b);
    }

    @Test//加密 基本使用
    public void demo1(){
        String salt="wzs";
        int hashIterations=3;

        //fae0b27c451c728867a567e8c1bb4e53
        //salt    ==>b362ae2dc346ffbfacb745147edcd32f
        //给“666”进行加密，salt表示 加密时的佐料，hashIterations表示加密的次数
        Md5Hash hash = new Md5Hash("666",salt,hashIterations);

        System.out.println(hash.toString());
    }

    @Test
    public void demo2(){
        //认证
        //创建securityManager对象
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-one.ini");
        SecurityManager instance = securityManagerFactory.getInstance();
        //设置到当前运行环境
        SecurityUtils.setSecurityManager(instance);
        //获取主体
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        //认证
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        boolean b = subject.isAuthenticated();
        System.out.println("认证："+b);

        //授权
        //判断单个角色
        boolean hasRole = subject.hasRole("role1");
        //
        boolean[] hasRoles = subject.hasRoles(Arrays.asList("role1", "role2"));
        System.out.println("是否有这个角色："+hasRole+"--------------"+hasRoles[0]+"="+hasRoles[1]);

        //判断单个权限
        boolean permitted = subject.isPermitted("user:create");
        //
        boolean permittedAll = subject.isPermittedAll("user:create", "user:update");
        System.out.println("是否有这个权限："+permitted+"----------------"+permittedAll);
    }
}
