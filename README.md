### Steps Counter service

- Authentication: base auth + session support
- Thread-Save logic: 
  - Increment Counter logic protected by synchronized `incrementValue()` method + `valatile` key word for primitive.
  - Read/Write Locks are necessary mostly for operations synchronization and returned data consistency (other words Increment Counter logic shouldn't be applied to Counter which is deleting at the same moment, in this case operations should be applied sequentially)
  - `ConcurrentHashMap` allows concurrent modifications in Map