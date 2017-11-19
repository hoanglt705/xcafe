package com.s3s.ssm.repo;

import org.springframework.data.repository.CrudRepository;

import com.sunrise.xdoc.entity.config.UploadFile;

public interface UploadFileRepository extends CrudRepository<UploadFile, Long> {

}
