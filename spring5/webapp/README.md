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
Thymeleaf is a Java XML/XHTML/HTML5 template engine that can work both in web (servlet-based) and non-web environments. It is better suited for serving XHTML/HTML5 at the view layer of MVC-based web applications, but it can process any XML file even in offline environments. It provides full Spring Framework integration.

It is extremely easy to integrate Thymeleaf with HTML, CSS, and JavaScript frameworks, such Bootstrap. Webjars project allows us to use.