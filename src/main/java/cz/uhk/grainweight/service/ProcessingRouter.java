package cz.uhk.grainweight.service;

import cz.uhk.grainweight.service.processing.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingRouter {

    private final SerialStrategy serial;
    private final PoolStrategy pool;
    private final VirtualStrategy virtual;

    public ProcessingStrategy pick(WorkSpec spec) {
        ProcessingMode mode = spec.getMode();
        Integer size = spec.getSize();

        log.info("ROUTER pick: mode={} size={}", mode, size);

        return switch (mode) {
            case POOL -> {
                pool.setCap(size);
                log.info("ROUTER pick -> PoolStrategy (size={})", size);
                yield pool;
            }
            case VIRTUAL -> {
                virtual.setConcurrencyCap(size);
                log.info("ROUTER pick -> VirtualStrategy (size={})", size);
                yield virtual;
            }
            case SERIAL -> {
                log.info("ROUTER pick -> SerialStrategy");
                yield serial;
            }
        };
    }
}
