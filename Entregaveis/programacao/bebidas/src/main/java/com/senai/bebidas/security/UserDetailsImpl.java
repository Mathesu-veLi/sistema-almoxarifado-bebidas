package com.senai.bebidas.security;

import com.senai.bebidas.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;
    private String senha;
    private boolean ativo;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getSenhaHash(),
                usuario.isAtivo(),
                authorities);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** RN-14 / contrato: usuario inativo nao pode autenticar. */
    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
