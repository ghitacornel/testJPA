Removing a parent makes the children orphaned and the children will be removed also.

Making a child orphan by setting his parent to null and/or removing him from his parent children list will not remove
the orphaned child from the database. It will only remove the link between the child and his parent.