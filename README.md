# Demo Authorization Server

This project demonstrates a minimal Spring Authorization Server configuration
backed by a simple H2 database. Default credentials are loaded on startup and
can be used by other microservices to obtain OAuth2 tokens.

```
username: user
password: password
```

Other services can validate issued JWT tokens using the exposed JWK endpoint.
