package com.psu.Lionchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LionchatApplication {

	public static void main(String[] args) {
//		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(LionchatApplication.class);
//		System.out.println("Bean Names: ");
//		for(String beanName : applicationContext.getBeanDefinitionNames()){
//			System.out.println(beanName);
//		}

		SpringApplication.run(LionchatApplication.class, args);
	}
}
