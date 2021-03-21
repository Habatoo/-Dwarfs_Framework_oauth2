package com.angrydwarfs.framework.models;

/**
 * Уровни видимости для пользователя USER с разной степенью детализации
 * Уровни видимости для пользователей с разными уровнями доступа USER, MODERATOR, ADMINISTRATOR
 */
public final class Views {
    public interface UserShortData {}

    public interface UserMiddleData extends UserShortData {}

    public interface UserAllData extends UserMiddleData {}

    public interface ModData extends UserAllData {}

    public interface AdminData extends ModData {}

}
