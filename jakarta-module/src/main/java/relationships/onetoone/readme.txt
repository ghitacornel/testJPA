ONE to ONE relationship comes in two forms : STRICT or NOT STRICT
ONE to ONE relationship is a form of ONE to MANY relationship where there is a UNIQUE constraint on the MANY side
ONE to ONE can be simulated using EMBEDDED
CASCADE of other type than REMOVE can be defined

[ONE to ONE STRICT]
one entity is independent and the other one is fully dependent
the dependent entity owns the relationship
the dependent entity knows of the relationship
the independent entity might know of the relationship
CASCADE REMOVE must be defined from the independent entity to the dependent entity

[ONE to ONE NOT STRICT]
one entity is independent and the other one dependent but can be independent sometimes
the dependent entity owns the relationship
the dependent entity knows of the relationship
the independent entity might know of the relationship
CASCADE REMOVE must not be defined