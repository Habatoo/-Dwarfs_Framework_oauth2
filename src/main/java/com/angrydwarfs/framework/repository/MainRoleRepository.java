package com.angrydwarfs.framework.repository;

import com.angrydwarfs.framework.models.Enums.EMainRole;
import com.angrydwarfs.framework.models.MainRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainRoleRepository extends JpaRepository<MainRole, Integer> {
    Optional<MainRole> findByMainRoleName(EMainRole mainRoleName);
}
