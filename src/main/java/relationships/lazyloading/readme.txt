relationships encapsulated by collections can always be loaded lazily.

relationships encapsulated by a single reference can be loaded lazily
if and only if the entity holding the reference is the one holding the foreign key also.
in this case the referenced entity referenced field is available without having the referenced entity fully loaded.