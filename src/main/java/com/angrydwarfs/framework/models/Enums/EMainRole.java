package com.angrydwarfs.framework.models.Enums;

import com.angrydwarfs.framework.models.MainRole;
import org.springframework.security.core.GrantedAuthority;

/**
 * Перечень возможных ролей по доступу пользователя.
 * @see MainRole (таблица ролей).
 * @version 0.001
 * @author habatoo
 */
public enum EMainRole implements GrantedAuthority {
    ROLE_USER,
    FACEBOOK_USER,
    GOOGLE_USER,
    VK_USER,
    ROLE_MODERATOR,
    ROLE_ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}