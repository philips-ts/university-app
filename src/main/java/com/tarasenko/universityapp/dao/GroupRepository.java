package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
