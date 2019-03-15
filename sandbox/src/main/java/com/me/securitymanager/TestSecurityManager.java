package com.me.securitymanager;

import java.security.Policy;

/**
 * Created by llb on 2019/1/10.
 */
public class TestSecurityManager {
    public static void main(String[] args) {
        SecurityManager security =  System.getSecurityManager();
        System.out.println("");
        security = new SecurityManager();
        System.setSecurityManager(security);
        security =  System.getSecurityManager();
        System.out.println("");


        //--------------------------------------


        Policy policy =  Policy.getPolicy();
        System.out.println("");
    }
}
