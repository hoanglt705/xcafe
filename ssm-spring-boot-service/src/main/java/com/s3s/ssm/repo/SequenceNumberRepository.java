package com.s3s.ssm.repo;

import org.springframework.data.repository.CrudRepository;

import com.sunrise.xdoc.entity.system.SequenceNumber;

public interface SequenceNumberRepository extends CrudRepository<SequenceNumber, Long> {

}
