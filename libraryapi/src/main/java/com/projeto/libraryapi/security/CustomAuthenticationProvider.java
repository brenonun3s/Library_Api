package com.projeto.libraryapi.security;

import com.projeto.libraryapi.model.Usuario;
import com.projeto.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);

        if (usuarioEncontrado == null) {
            throw getErroUsuarioNaoEncontrato();
        }
        String senhaCriptografada = usuarioEncontrado.getSenha();

        boolean senhasBatem = passwordEncoder.matches(senhaDigitada, senhaCriptografada);

        if (senhasBatem) {
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw getErroUsuarioNaoEncontrato();
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrato() {
        return new UsernameNotFoundException("Usuário e/ou senha não encontrados!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
