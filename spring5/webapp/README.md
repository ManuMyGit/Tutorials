# Subjects covered in this tutorial

## JPA

### @ManyToMany annotation
Used to map a n-m relationship between two entities. By default, with no configuration, this annotation creates a secondary table to map the relationship n-1 and 1-m per entity (in the example, one to map the relationship Author-Book and another one to map Book-Author). However, in the relational world this relationship is done by using just one intermediate table. Let's see an example:

- Book has a set of Author.
- Author has a set of Book.
- With no configuration, two tables would be created: Author_Books, Book_Authors.

@ManyToMany configuration:

 - targetEntity: the entity class that is the target of the association. Optional only if the collection-valued relationship property is defined using Java generics. Must be specified otherwise.
 - cascade: the operations that must be cascaded to the target of the association. Values: CascadeType.ALL (cascade all operations), CascadeType.PERSIST (cascade only persist operations), CascadeType.MERGE (cascade only merge operations), CascadeType.REMOVE (cascade only remove operations), CascadeType.REFRESH (cascade only refresh operations), CascadeType.DETACH (cascade only detacth operations).
 - fetch: whether the association should be lazily loaded or must be eagerly fetched. The EAGER strategy is a requirement on the persistence provider runtime that the associated entities must be eagerly fetched.  The LAZY strategy is a hint to the persistence provider runtime. Values: FetchType.LAZY (default), FetchType.EAGER.
 - mappedBy: the field that owns the relationship. Required unless the relationship is unidirectional. ManyToMany relationship has always two sides: the owning side and the inverse side. In this example, the owning side is the Book.

This annotation is used in conjunction with:

 - @JoinTable
 - @JoinColumn

### @JoinTable annotation
This annotation is used to define the join/link table of the relationship. Properties:

 - name: the name of the join table.
 - catalog: the catalog of the table.
 - schema: the schema of the table.
 - joinColumns: the foreign key columns of the join table which reference the primary table of the entity owning the association. In the example, book.
 - inverseJoinColumns: the foreign key columns of the join table which reference the primary table of the entity that does not own the association. In the example, author.
 - uniqueConstraints: unique constraints that are to be placed on the table. These are only used if table generation is in effect.
 - indexes: indexes for the table. These are only used if table generation is in effect.

### @JoinColumn annotation
@JoinColumn is used to specify the join/linking column with the main table. Here, the join column is book_id and author_id is the inverse join column since Author is on the inverse side of the relationship.

## H2 embedded database

H2 is a relational database management system written in Java. It can be embedded in Java applications or run in the client-server mode. H2 supports a sub set of the SQL standard. H2 also provides a web console to maintain the database.

Some of the properties of H2 which can be configured:

 - spring.h2.console.enabled: used to enable or disable h2 console (true/false).
 - spring.h2.console.path: path of the console.

## Spring @Controller annotation
This annotation is part of Spring MVC. It is used to create classic controllers for Model-View-Controller pattern.

## Spring Data
Spring Data is an umbrella project consisting of independent projects with, in principle, different release cadences. In the example, we used Spring Data JPA. Spring Data JPA provides repository support for the Java Persistence API (JPA). It eases development of applications that need to access JPA data sources.

To use Spring Data JPA is extremely easy. The only thing needed is to create an interface and to extend one of the Spring Data JPA interfaces:

 - CrudRepository
 - PagingAndSortingRepository
 - ...

## Thymeleaf
Thymeleaf is a Java XML/XHTML/HTML5 template engine (it is not a web framework) that can work both in web (servlet-based) and non-web environments. It is better suited for serving XHTML/HTML5 at the view layer of MVC-based web applications, but it can process any XML file even in offline environments. It provides full Spring Framework integration.

Thymeleaf is a replacement of JSPs (Java Server Pages), and it is not tied to web environment (it can be used for example for producint HTML emails).

Thymeleaf templates are valid HTML documents and it can be viewed browser, in contrast to JSP. The natural templating ability allow you to perform rapid development, without the need to run a container to parse the template/JSP to view the product in a browser.

It is extremely easy to integrate Thymeleaf with HTML, CSS, and JavaScript frameworks, such Bootstrap. Webjars project allows us to use.

## HTTP
HTTP Methods: GET, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, ...

 - GET: it is a request for a resource (html file, javascript, some entity, ...).
 - HEAD: it is like GET, but only ask for metadata information without body.
 - POST: it is used to post a data to the server.
 - PATCH: it applies partial modifications to the specified resource.
 - PUT: it is a request for the enclosed entity to be stored at the supply URI. If the entity already exists, it is expected to be updated.
 - DELETE: it is a request to delete the specified resource.
 - OPTIONS: it returns the HTTP methods supported by the specified URL.
 - TRACE: it will echo the received request. It can be used to check if the request was altered by intermediate servers.

Some of those methos are considered secure because they don't modify the status of the resource: GET, OPTIONS, HEAD and TRACE.

### Idempotence
It is a quality of an action such that repetitions of the action have no further effect on the outcome. For instance, PUT and DELETE are idempotent methods. Safe methods are idempotent as well.

But being trully idempotent is not enforced by the protocol, it needs to be implemented.

Table      Request Body     Response Body        Safe         Idempotent       Cacheable
GET            No               Yes               Yes            Yes              Yes
HEAD           No               No                Yes            Yes              Yes
POST           Yes              Yes               No             No               Yes
PUT            Yes              Yes               No             Yes              No
DELETE         No               Optional?         No             Yes              No
OPTIONS        Optional         Yes               Yes            Yes              No
TRACE          No               Yes               Yes            Yes              No
PATCH          Yes              Yes               Yes            No               Yes

### HTTP status codes
Every HTTP request returns a code with the result of the process.
 - 100 series are information in nature.
 - 200 series indicate successful request (200: Ok, 201: Created, 204: Accepted).
 - 300 series are redirections (301: Moved permanently).
 - 400 series indicate client side errors (400: Bad Request, 401: Unauthorized, 403: Forbidden, 404: Not found).
 - 500 series indicate server side errors (500: Internal server error, 503: Service unavailable).