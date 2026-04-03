package cz.uhk.grainweight.rest;

import cz.uhk.grainweight.model.ApiResponse;
import cz.uhk.grainweight.model.Field;
import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.service.processing.ProcessingMode;
import cz.uhk.grainweight.service.processing.ProcessingStrategy;
import cz.uhk.grainweight.service.processing.WorkSpec;
import cz.uhk.grainweight.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/work")
@RequiredArgsConstructor
public class WorkController extends BaseController {

    private final ProcessingRouter router;
    private final UserService userService;
    private final WorkSimulator workSimulator;
    private final WeightRecordService weightRecordService;
    private final FieldService fieldService;


    @GetMapping("/records")
    public ResponseEntity<ApiResponse<List<WeightRecord>>> listRecords(
            @RequestParam(defaultValue = "SERIAL") ProcessingMode mode,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer delayMs) {

        WorkSpec spec = new WorkSpec(mode, size);

        final int safeDelay = Math.max(0, delayMs);

        String msg = "records: mode=" + mode
                + (size != null ? (" size=" + size) : "")
                + (safeDelay > 0 ? (" delayMs=" + safeDelay) : "");


        return wrapResponseProcessed(()-> {
            ProcessingStrategy s =router.pick(spec);
            return s.execute(() -> {
                if(safeDelay > 0)
                    workSimulator.simulateIoDelay(safeDelay);

                return weightRecordService.getAllWeightRecords();
            });
        }, HttpStatus.OK, msg);
    }


    @PostMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<WeightRecord>> createRecord(
            @RequestParam(defaultValue = "SERIAL") ProcessingMode mode,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer delayMs,
            @RequestParam(required = false) Long fieldId,
            @RequestParam(required = false) Long userId,
            @RequestBody WeightRecord record) {

        WorkSpec spec = new WorkSpec(mode, size);

        final int safeDelay = Math.max(0, delayMs);

        String msg = "createRecord: mode=" + mode
                + (size != null ? (" size=" + size) : "")
                + (safeDelay > 0 ? (" delayMs=" + safeDelay) : "")
                + (fieldId != null ? (" fieldId=" + fieldId) : " fieldId=null")
                + (userId  != null ? (" userId="  + userId)  : " userId=null");

        return wrapResponseProcessed(() -> {
            ProcessingStrategy s = router.pick(spec);
            return s.execute(()-> {
                if (safeDelay > 0) workSimulator.simulateIoDelay(safeDelay);


                if (record.getDate() == null)
                    record.setDate(LocalDateTime.now());

                if (record.getGrossWeight() < 0 || record.getTareWeight() < 0)
                    throw new IllegalArgumentException("Weight records must be > 0");

                if (userId != null) {
                    User createdBy = userService.getUser(userId).orElse(null);
                    record.setCreatedBy(createdBy);
                } else {
                    record.setCreatedBy(null);
                }

                if (fieldId != null) {
                    Field field = fieldService.getField(fieldId).orElse(null);
                    record.setField(field);
                } else {
                    record.setField(null);
                }

                WeightRecord saved = weightRecordService.saveWeightRecord(record);
                saved.setCreatedBy(null);
                saved.setField(null);
                return saved;
            });
        }, HttpStatus.CREATED, msg);

    }



    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getUsers(
            @RequestParam(defaultValue = "SERIAL") ProcessingMode mode,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer delayMs
    ) {
        final Integer normalizedSize = (size != null && size > 0) ? size : null;
        final int safeDelay = (delayMs != null && delayMs > 0) ? delayMs : 0;

        WorkSpec spec = new WorkSpec(mode, normalizedSize);

        String msg = "Processed with " + mode
                + (normalizedSize != null ? (" size=" + normalizedSize) : "")
                + (safeDelay > 0 ? (" delayMs=" + safeDelay) : "");

        return wrapResponseProcessed(() -> {
            ProcessingStrategy s = router.pick(spec);
            return s.execute(() -> {
                if (safeDelay > 0) workSimulator.simulateIoDelay(safeDelay);
                return userService.getAllUsers();
            });
        }, HttpStatus.OK, msg);
    }


}
