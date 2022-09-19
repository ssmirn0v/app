package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.HavingId;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Scope("prototype")
public class StorageHashMap<T extends HavingId> implements Storage {


    private final Map<Long, HavingId> storage;


    public StorageHashMap() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public void save(HavingId e) {
        storage.put(e.getId(),  e);
        log.debug("RequestId: {}, updated storage: {}",MDC.get("RequestId"), storage);
    }

    @Override
    public void delete(Long id) {
        log.debug("RequestId: {}, storage before del: {}",MDC.get("RequestId"), storage);
        storage.remove(id);
        log.debug("RequestId: {}, storage after del: {}",MDC.get("RequestId"), storage);
    }

    @Override
    public Optional<HavingId> get(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }


}
