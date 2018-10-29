package com.sharefile.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sharefile.domain.FileVO;

@Repository
public interface FileRepository  extends CrudRepository<FileVO, String> {

	Iterable<FileVO> findAllByUploaderId(String uploaderId);

	FileVO findByNameAndUploaderId(String fileName, String uploaderId);

}
