Removing a child from parent children list doesn't remove it from the database.
Even if merging afterwards the parent with cascade has no effect.
Removing a child must be performed directly, manually on the child to be removed.

This example shows a 1-n bidirectional mapping