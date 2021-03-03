package com.ehigon.tasks.finished;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinishedRepository extends CrudRepository<Finished, Long> {
}
