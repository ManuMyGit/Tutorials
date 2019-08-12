package org.mjjaenl.springdi;

import org.mjjaenl.springdi.controller.ConstructorInjectedController;
import org.mjjaenl.springdi.controller.MyController;
import org.mjjaenl.springdi.controller.PropertyInjectedController;
import org.mjjaenl.springdi.controller.SetterInjectedController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringdiApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringdiApplication.class, args);
		
		MyController controller = (MyController)ctx.getBean("myController");
		System.out.println(controller.hello());
		System.out.println(ctx.getBean(PropertyInjectedController.class).sayHello());
		System.out.println(ctx.getBean(SetterInjectedController.class).sayHello());
		System.out.println(ctx.getBean(ConstructorInjectedController.class).sayHello());
	}
}
