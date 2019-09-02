ONE to MANY relationship comes in two forms : STRICT or NOT STRICT
ONE to MANY can be simulated using EMBEDDED

[GENERIC]
ONE part entity is an independent entity
ONE part entity is an independent entity => CASCADE of any type from MANY to ONE is strictly forbidden.
MANY part entity can or cannot be independent depending on the business requirements
MANY part entity owns the relationship
both ONE and MANY part entities know about the relationship
if MANY part entity cannot be independent then we have a ONE to MANY STRICT type
if MANY part entity can be independent then we have a ONE to MANY NOT STRICT type

[ONE to MANY STRICT]
ONE part entity is the actual manager of the relationship
MANY part entity is fully dependent of the ONE part entity
removing the ONE part entity triggers the removal of the all MANY part entities
CASCADE REMOVE from ONE to MANY must be defined, CASCADE of other types from ONE to MANY can be defined

[ONE to MANY NOT STRICT]
both entities manage the relationship
MANY part entity is not fully dependent of the ONE part entity; it can be independent sometimes
removing the ONE part entity must not trigger the removal of the all MANY part entities
CASCADE REMOVE from ONE to MANY must not be defined, CASCADE of other types from ONE to MANY can be defined