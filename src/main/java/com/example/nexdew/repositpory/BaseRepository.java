package com.example.nexdew.repositpory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    @Query("select t from #{#entityName} t where t.id = ?1")
    Optional<T> findById(ID id);
}
