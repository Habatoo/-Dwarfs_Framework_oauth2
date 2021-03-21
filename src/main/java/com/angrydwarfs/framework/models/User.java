package com.angrydwarfs.framework.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USR")
@ToString(of = {"id", "username", "password", "userEmail", "creationDate", "activationEmailCode"})
@EqualsAndHashCode(of = {"id"})
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = -1399500801576919731L;

    @Id
    @Column(name="USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.AdminData.class)
    private Long id;

    @Column(name="SOCIAL_NET_ID")
    @JsonView(Views.ModData.class)
    private String socialNetId;

    @Column(name="USER_LOCALE")
    @JsonView(Views.UserAllData.class)
    private String userLocale;

    @NotBlank(message = "Username cannot be empty")
    @Column(name="USER_NAME", length = 100, nullable = false)
    @JsonView(Views.UserShortData.class)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Column(name="USER_PASSWORD", length = 100)
    @JsonView(Views.AdminData.class)
    private String password;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    @Column(name="USER_EMAIL", length = 100, nullable = false)
    @JsonView(Views.UserAllData.class)
    private String userEmail;

    @JsonView(Views.ModData.class)
    @Column(name="USER_EMAIL_ACTIVATION_STATUS")
    private boolean activationEmailStatus;

    @JsonView(Views.ModData.class)
    @Column(name="USER_EMAIL_ACTIVATION_CODE")
    private String activationEmailCode;

    @Column(name="USER_CREATION_DATE", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.UserShortData.class)
    private LocalDateTime creationDate;

    @Column(name="USER_LAST_VISITED_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.UserShortData.class)
    private LocalDateTime lastVisitedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference
    @JsonView(Views.ModData.class)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JoinTable(	name = "USER_MAIN_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MAIN_ROLE_ID"))
    private Set<MainRole> mainRoles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference
    @JsonView(Views.ModData.class)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JoinTable(	name = "USER_SUB_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUB_ROLE_ID"))
    private Set<SubRole> subRoles = new HashSet<>();

    @OneToMany(mappedBy = "userTokens", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIdentityReference
    @JsonView(Views.AdminData.class)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @Column(name="USER_TOKENS")
    private Set<Token> tokens = new HashSet<>();

    /**
     * Конструктор для создания пользователя.
     * @param username - имя пользователя - предпоалагается строковоя переменная Имя + Фамилия.
     * @param userEmail - email пользователя.
     * @param password - пароль, в БД хранится в виде хешированном виде.
     * activationStatus - поле подтверждения email пользователя.
     * userStatus - поле статуса пользователя на возможность чтения и записи и доступа к аккаунту
     *
     */
    public User(String username, String userEmail, String password) {
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.activationEmailStatus = false;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getMainRoles();
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean isEnabled() {
        return getActivationEmailStatus();
    }

    public boolean getActivationEmailStatus() {
        return activationEmailStatus;
    }

}
