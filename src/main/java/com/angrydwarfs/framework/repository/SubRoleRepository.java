package com.angrydwarfs.framework.repository;

import com.angrydwarfs.framework.models.Enums.ESubRole;
import com.angrydwarfs.framework.models.SubRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRoleRepository extends JpaRepository<SubRole, Integer> {
    Optional<SubRole> findBySubRoleName(ESubRole subRoleName);
}
