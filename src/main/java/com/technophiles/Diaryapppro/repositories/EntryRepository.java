package com.technophiles.Diaryapppro.repositories;

import com.technophiles.Diaryapppro.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
