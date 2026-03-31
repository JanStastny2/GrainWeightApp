package cz.uhk.grainweight.model.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpec {
    private ProcessingMode mode;
    private Integer size;
}
