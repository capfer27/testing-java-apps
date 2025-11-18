package com.capfer.usersservice.shared;

import com.capfer.usersservice.service.UsersService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CONTEXT = context;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }

}