package io.sh4.server.controller

import io.sh4.server.controller.request.AuthenticationRequest
import io.sh4.server.controller.request.RegisterUserRequest
import io.sh4.server.controller.response.AuthenticationResponse
import io.sh4.server.service.AuthenticationService
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
class AuthenticationController(
    private val service: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(service.registerAndCreateNewUser(request))


    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(service.authenticate(request))
}
