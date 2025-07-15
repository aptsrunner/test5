# Demo Authorization Server

This project demonstrates a minimal Spring Authorization Server configuration
backed by an H2 database. Default credentials are loaded on startup and
can be used by other microservices to obtain OAuth2 tokens. Users and OAuth clients
are stored in the database so that new ones can be created at runtime.

```
username: user
password: password
```

Other services can validate issued JWT tokens using the exposed JWK endpoint.

## Managing Users

The server exposes REST endpoints to create and update users:

```bash
curl -X POST http://localhost:8080/users \
    -H 'Content-Type: application/json' \
    -d '{"username":"api","password":"secret","roles":"USER"}'

curl http://localhost:8080/users
```

## Managing OAuth Clients

Clients are also persisted and can be created via the `/clients` endpoint:

```bash
curl -X POST http://localhost:8080/clients \
    -H 'Content-Type: application/json' \
    -d '{"id":"1","clientId":"my-client","clientSecret":"my-secret","redirectUri":"http://localhost:8080/login/oauth2/code/my-client"}'
```

After registering a client, obtain a token using the standard OAuth2 Authorization Code flow.

Run the application using:

```bash
./gradlew bootRun
```
