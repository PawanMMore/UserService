package com.pawan.userservice.repositories;

import com.pawan.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
}
