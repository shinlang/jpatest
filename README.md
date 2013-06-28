jpatest
=======

Just a simple project trying to clear up JPAs merge() and persist(). If you want to deploy it, you probably have to change the persistence.xml, as I'm using the sample JDBC resource (Derby Pool) that comes with glassfish.

From the examples in the MyModel class I came up with these simple rules:

* Use container managed transactions, annotate @TransactionAttribute to your classes and methods if necessary (REQUIRED is default)
* If you want to insert an entity into the database -> use persist()
* If you want to save an entity to the database, but you're not sure if it already exists -> use merge()
* If you want to update an existing entity -> get an attached object and use neither merge() nor persit()
