package com.angrydwarfs.framework.models;

import com.angrydwarfs.framework.models.Enums.EMainRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Модель ролей по доступу к данным пользователей и возможности редактирования и удаления данных.
 * @version 0.001
 * @author habatoo
 * @see EMainRole (перечень возможных ролей пользователя).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MAIN_ROLES")
@ToString(of = {"id", "mainRoleName"})
@EqualsAndHashCode(of = {"id"})
public class MainRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MAIN_ROLE_ID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="MAIN_ROLE_NAME", length = 20)
    private EMainRole mainRoleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="MAIN_ROLE_USER")
    private User mainRoleUser;

    public MainRole(EMainRole mainRoleName) {
        this.mainRoleName = mainRoleName;
    }

    /**
     * Конструктор ролей по доступу к данным пользователей и возможности редактирования и удаления данных.
     * @return  mainRoleName - наименовение роли.
     */
    @Override
    public String getAuthority() {
        return mainRoleName.getAuthority();
    }
}
