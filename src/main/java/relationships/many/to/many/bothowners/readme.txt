this is the right way to map this relationship
both entities are independent
both entities own the relationship
same join table MUST BE used
can use lists or maps as Java Collections for relationship data holder

Cascading is NOT allowed => additional checks needs to be performed on CREATE UPDATE REMOVE
Cascading can be allowed for PERSIST AND MERGE => no additional checks needs to be performed on CREATE UPDATE
Cascading must not be allowed for REMOVE => leads to removal of whole related entities
