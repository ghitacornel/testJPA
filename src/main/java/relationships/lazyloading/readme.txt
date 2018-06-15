relationships encapsulated by collections can always be loaded lazily.

relationships encapsulated by a single reference can be loaded lazily if and only if
the entity holding the reference is the same entity holding the database foreign key.
in this case the referenced entity foreign key column is available without having the referenced entity fully loaded.