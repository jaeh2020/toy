package com.projext.web.upload;

import com.projext.domain.upload.Upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UploadRepository {
    private final Map<Long, Upload> store = new HashMap<>();
    private long sequence = 0L;

    public Upload save(Upload upload){
        upload.setUploadId(++sequence);
        store.put(upload.getUploadId(), upload);
        return upload;
    }

    public Upload findById(Long id){
        return store.get(id);
    }

    public List<Upload> findAll() {
        return new ArrayList<>(store.values());
    }
}
