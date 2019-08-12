# Subjects covered in this tutorial

## SOLID Principles of OOP

### Single Responsibility Principle
This principle says every class should have a single responsibility. It implies there should never be more than one reason for a class to change, so the class should be small.
An anti pattern is the classic 'god' class, which huge methods, in charge of a whole functionality. This class should be splitted into smaller classes.
One of the main advantages of this principle is to have easily maintainable.

### Open/Closed Principle
This principle says your classes should be open for extensions but closed for modifications. It means you should be able to extend a class behavior, without modifying it.
Private variables with getter and setter methods should be used only when you need them, if the private variable won't be used outside the class, there shouldn't be any getter and setter method to access (and to modify) the private variable.
A good practice is to use abstract classes with abstract methods you expect to be implemented, then you can encapsulate the common code inside the abstract base class.

### Liskov Substitution Principle
If it looks like a duck, quacks like a duck, but needs batteries, you probably have the wrong abstraction.
Objects in a program should be replaceable with instances of their subtypes without altering the correctness of the program.
The violation of this principle will often fail the "Is a" text: a square "Is a" rectangle, however, a rectangle "Is not" a square.

### Interface Segregation Principle
It is better to use many client specific interfaces than one "general purpose" interface ('god' interfaces). Keep you components focused and minimize dependencies between then. This principle is linked to the Single Responsibility Principle.

### Dependency Inversion Principle
Why would you solder a lamp directly to the electrical wiring in a wall instead using a plug? Abstraction shouldn't depend upon details, but details should depend upon abstractions. Higher level and lower level objects should depend on same abstract interactions. Thanks to that, we can do our application code core to be independent of implementation details, such us the framework you use, database, server connections, ... All this aspects will be done via interfaces so core don't have to know the concrete implementation of each one of them.

## Spring Core

### Spring Context
The Application Context is Spring's advanced container. Similar to BeanFactory, it can load bean definitions, wire beans together, and dispense beans upon request. Additionally, it adds more enterprise-specific functionality such as the ability to resolve textual messages from a properties file and the ability to publish application events to interested event listeners. This container is defined by org.springframework.context.ApplicationContext interface.

The ApplicationContext includes all functionality of the BeanFactory, It is generally recommended over BeanFactory. BeanFactory can still be used for lightweight applications like mobile devices or applet-based applications.

### Dependency Injection (DI)
This is a basic example of dependency injection. When you go and get things out of the refrigerator for yourself, you can cause problems. You might leave the door open, you might get something mom of dad doesn't want you to have. You might even be looking for something we don't even have or which has expired. What you should be doing is starting a need, "I need something to drink with lunch", and then we'll make sure you have something when you sit down to eat (John Munsch, 28 October 2009).

Dependency injection is where a needed dependency is injected by another object, so the class being injected has no responsibility in instantiating the object being injected.

There are several types of Dependency Injection:
 - By class properties: this is actually an anti pattern (doing private properties public to be able to instantiate properties outside the class).
 - By setters.
 - By constructor: most preferred because if your class need and object, the best moment of instantiating it is when you create the object. Otherwise, you could have NullPointerEcxeption if you use the object before injecting it with a setter method.
 
Dependency injection can be done with concrete classes or with interfaces. You should avoid dependency injection with concrete classes because you create a dependency between your object and the injected object.
Dependency injection via interfaces allows runtime to decide implementation to inject, follows interface Segregation Principle of SOLID and makes the code more testable.

### Inversion of control (IoC)
This is a technique which allows dependency to be injected in runtime. The framework will determine at runtime which implementation of the object will be injected.

### DI vs IoC
DI refers much to the composition of your classes (ie - you compose your classes with DI in mind), whereas IoC is the runtime environment of your code (ie - Spring Framework's IoC container).


## Spring Core and IoC annotations
### Spring bean life cycle
Each bean has properties, constructors, dependencies, ..., ..., ... To create each bean, Spring has its own bean life cycle.
Everything is done by Sprint, we don't need to worry about that, but it is important to know what the life cycle of a bean is:
 - Instantiate
 - Populate properties
 - Call setBeanName of BeanNameAware
 - Call setBeanFactory of BeanFactoryAware
 - Call setApplicationContext of ApplicationContextAware.
 - Preinitialization (Bean PostProcessor, postProcessBeforeInitialization).
 - afterPropertiesSet of Initializing Beans.
 - Custom Init Method (@PostConstruct).
 - Post Initialization (BeanPostProcessor, postProcessAfterInitialization).
 - Bean Ready to use.

Ending cycle:
 - Container shutdown.
 - Disposable Bean's destroy.
 - Call Custom Destroy Method (@PreDestroy).
 - Terminated.

### @Component
This is a general-purpose stereotype annotation indicating that the class is a spring component.

### @Service
@Service beans hold the business logic and call methods in the repository layer.

### @Repository
This is to indicate that the class defines a data repository.

### @Autowired
It is used in Spring in order to inject dependencies.

### @Qualifier
Used in case there are more than one type of bean which can match with the dependency and we want to specify a concrete one by name.

### @Controller
The @Controller annotation indicates that a particular class serves the role of a controller. The @Controller annotation acts as a stereotype for the annotated class, indicating its role.

### Injection via reflection name
GreetingService greetingServiceImpl

### @Primary
Used to set a primary bean in case of multiple matches.

## Spring profiles
When we create an application, we need to deploy it in different kinds of environments, such as development, QA, staging and production. The application configuration in each of these environments will be different.

One of the approaches to handling application configuration is to create something called a profile. Spring Boot has the concept of a profile built in.

You can define default configuration in application.properties. Environment specific overrides can be configured in specific files:
- application-dev.properties
- application-qa.properties
- application-stage.properties
- application-prod.properties

Here are a couple of ways of setting the active profile:

At the time of launching the Java application
- -Dspring.profiles.active=qa - in the VM properties, OR
- Do the following in the application.properties file:
spring.application.profiles=qa.

@Profile annotation can be used in code to define your beans. If a bean is defined with @Profile("dev") it will be available only with dev profile.

There is a special profile, "default", which is used when no profile is set.
