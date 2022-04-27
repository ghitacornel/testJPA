MANY TO ONE is useful for some scenarios
 e.g. MANY persons [ can have / must have ] ONE preferred color
 e.g. MANY persons [ can have / must have ] ONE physical address

MANY TO ONE relationship can be seen as ONE TO ONE relationship when interpreted from the MANY part to the ONE part
 e.g. ONE persons [ can have / must have ] ONE preferred color
 e.g. ONE persons [ can have / must have ] ONE physical address
yet it becomes clear that it is not a ONE TO ONE relationship when interpreted in the reversed direction
 e.g. ONE color [ can be / must be ] preferred by MANY persons
 e.g. ONE physical address [ can be / must be ] used by MANY persons

MANY part entity can be independent or not independent depending on the business requirements
if MANY part entity is independent => NON STRICT MANY TO ONE relationship => database foreign key can be null
if MANY part entity is dependent => STRICT MANY TO ONE relationship => database foreign key cannot be null

MANY part entity owns the relationship
MANY part entity is the only one that knows about the relationship

ONE part entity is always independent
ONE part entity does not own the relationship
ONE part entity does not know about the relationship

ONE part entity is always independent +
only MANY part entity knows of the relationship +
only MANY part entity owns the relationship
=> CASCADING of any type are forbidden

when REMOVING the ONE part entity
manually marking the MANY part entities as orphaned by setting their corresponding ONE part reference to null is required