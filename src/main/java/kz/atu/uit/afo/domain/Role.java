package kz.atu.uit.afo.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    Гость,ADMIN,Профориентатор,Куратор;

    @Override
    public String getAuthority() {
        return name();
    }
}
