package br.com.sobreiraromulo.controllers;

import br.com.sobreiraromulo.controllers.docs.AuthControllerDocs;
import br.com.sobreiraromulo.data.dto.PersonDTO;
import br.com.sobreiraromulo.data.dto.security.AccountCredentialsDTO;
import br.com.sobreiraromulo.data.dto.security.TokenDTO;
import br.com.sobreiraromulo.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs{

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    @Override
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {

        if(credentialsIsInvalid(credentials)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client");

        ResponseEntity<TokenDTO> tokenDTOResponseEntity = authService.signIn(credentials);

        if(tokenDTOResponseEntity == null)  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client");

        return tokenDTOResponseEntity;
    }

    @PutMapping("/refresh/{username}")
    @Override
    public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                          @RequestHeader("Authorization") String refreshToken) {

        if(parameterAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client");

        ResponseEntity<TokenDTO> tokenDTOResponseEntity = authService.refreshToken(username, refreshToken);

        if(tokenDTOResponseEntity == null)  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client");

        return tokenDTOResponseEntity;
    }

    @PostMapping(value = "/createUser",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials) {
        return authService.create(credentials);
    }

    private boolean parameterAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null || StringUtils.isBlank(credentials.getUsername()) || StringUtils.isBlank(credentials.getPassword());
    }
}
