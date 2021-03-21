package com.angrydwarfs.framework.models.Enums;

import com.angrydwarfs.framework.models.SubRole;

/**
 * Перечень возможных ролей по пользователя по доступам к сервисам и необходимым полям
 * по предоставлению своих данных.
 * @see SubRole (таблица ролей).
 * @version 0.001
 * @author habatoo
 */
public enum ESubRole {
    COMMON_USER,
    SILVER_USER,
    GOLD_USER;

    public String getSubRoleName() {
        return name();
    }
}
