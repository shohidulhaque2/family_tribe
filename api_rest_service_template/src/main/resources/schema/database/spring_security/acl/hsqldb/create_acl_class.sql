create table acl_class(
                          id bigint generated by default as identity(start with 100) not null primary key,
                          class varchar_ignorecase(100) not null,
                          constraint unique_uk_2 unique(class)
);
