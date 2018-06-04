this embedded notion is perfect for simulating 1(A) ---> 0..1(B) kind of relationships:

A is the parent and B is a child of A.
A can have 0 or at most 1 child of type B.
A is the owner of the association, meaning the deletion of A implies the deletion of B automatically.
instead of using 2 linked tables (one for each entity) embedded entity concept allows us to use 1 single table for storing both entities

B is not seen as an entity, but just a part of an entity
mapping of B can be overwritten in class hierarchies who reuse the concept