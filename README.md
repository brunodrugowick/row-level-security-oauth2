# Row Level Security with OAuth2

## Thanks

Thanks to @oscarlnetoo for the first implementation of this solution.

## Quick usage tips

You need an Authorization Server. This repository is already configured to use my own [spring-authorization-server-ui project](https://github.com/brunodrugowick/spring-authorization-server-ui) (work in progress) on the cloud to make things easier.

- Authorization Server: https://drugo-authorization-server.herokuapp.com/ (there's an interface to edit user's roles)
- User: `admin@email.com`
- Password: `password`

Also, use this [Insomnia Workspace](https://github.com/brunodrugowick/row-level-security-oauth2/blob/master/row-level-security-oauth2-insomnia_2020-10-12) to make things even easier (request chaining is a time-saver for architectures with a separated auth server).

## What

Ok, this is a very specific problem that I'll try to summarize as

> I want to segregate access to a REST (collection) resource `examples` and its sub-resources based on the users having a role/authority (OAuth2) to that specific sub-resource.

So, if I have the following list of `examples`:
 
 - Example with id `1`;
 - Example with id `2`; and 
 - Example with id `3`.
 
And the following list of users and their roles:
 
 - User `John` with roles `EXAMPLE_1` and `EXAMPLE_2`; and
 - User `Mary` with roles `EXAMPLE_2` and `EXAMPLE_3`.
 
**John** gets the following list with a `GET /examples`:
 
 ```json
[
    {
      "id": 1
    },
    {
      "id": 2
    }  
]
```

While **Mary** gets the following: 

```json
[
    {
      "id": 2
    },
    {
      "id": 3
    }  
]
```

Also, any operation (`PUT`, `DELETE` or `GET` the specific resource) on the sub-resources must also only be available to someone with access to that sub-resource. So: 

- John can `GET`, `PUT` or `DELETE` on `examples/1` and `examples/2`; and
- Mary can `GET`, `PUT` or `DELETE` on `examples/2` and `examples/3`.

## Why

Well, _that's not_ a case of owner-based access, where there's an owner for each resource and one can access only its own resources. Instead, I want to define in runtime, on a web solution with OAuth2-based security, which users have access to which resources.

The real scenario is even more complicated with `examples/1` having its own sub-resources like `examples/1/data` or `examples/1/data/1`.

## After `POST`ing problem

Well, if a user can `POST` to `/examples` (probably by having a role `EXAMPLES_WRITER`), how will they access the resource they just created? A role that identifies the resource, like `EXAMPLE_99` must be created for that user, which, in a OAuth2-based system is another application, one with the responsibility yo manage users.

However, just after creating the resource, the user doesn't have access to it. So, **maybe this solution must be combined with an owner-based access solution** (?!). 
