package cz.uhk.grainweight.service.processing;

import java.util.function.Supplier;

public interface ProcessingStrategy {
    <T> ProcessingResult<T> execute(Supplier<T> task);
}
