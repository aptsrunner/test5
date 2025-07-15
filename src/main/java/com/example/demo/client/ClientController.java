package com.example.demo.client;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final RegisteredClientRepository clientRepository;
    private final JdbcTemplate jdbcTemplate;

    public ClientController(RegisteredClientRepository clientRepository, JdbcTemplate jdbcTemplate) {
        this.clientRepository = clientRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<String> listClients() {
        return jdbcTemplate.queryForList("select client_id from oauth2_registered_client", String.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ClientRequest request) {
        RegisteredClient registeredClient = RegisteredClient.withId(request.id())
                .clientId(request.clientId())
                .clientSecret(request.clientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri(request.redirectUri())
                .scope("read")
                .clientSettings(ClientSettings.builder().build())
                .tokenSettings(TokenSettings.builder().build())
                .build();
        clientRepository.save(registeredClient);
    }

    @GetMapping("/{clientId}")
    public RegisteredClient getByClientId(@PathVariable String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @PutMapping("/{clientId}")
    public void update(@PathVariable String clientId, @RequestBody RegisteredClient client) {
        clientRepository.save(client);
    }

    public record ClientRequest(String id, String clientId, String clientSecret, String redirectUri) {}
}
