package com.angrydwarfs.framework.models;

import com.angrydwarfs.framework.models.Enums.ESubRole;
import lombok.*;

import javax.persistence.*;

/**
 * Перечень возможных ролей по пользователя по доступам к сервисам и необходимым полям
 * @version 0.001
 * @author habatoo
 * @see ESubRole (перечень возможных ролей пользователя).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "SUB_ROLES")
@ToString(of = {"id", "subRoleName"})
@EqualsAndHashCode(of = {"id"})
public class SubRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SUB_ROLE_ID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUB_ROLE_NAME", length = 20)
    private ESubRole subRoleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SUB_ROLE_USER")
    private User subRoleUser;

    /**
     * Конструктор возможных ролей по пользователя
     * @param subRoleName - наименовение роли.
     */
    public SubRole(ESubRole subRoleName) {
        this.subRoleName = subRoleName;
    }

}
