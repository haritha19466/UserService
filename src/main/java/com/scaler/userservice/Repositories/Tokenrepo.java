package com.scaler.userservice.Repositories;

import com.scaler.userservice.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface Tokenrepo extends JpaRepository<Token,Long> {
    Token save(Token token);

    Optional<Token> findByValue(String value);

    Optional<Token> findByValueAndActivestatusAndExpirydateGreaterThan(String value, boolean valid, Date currentTime);
}
