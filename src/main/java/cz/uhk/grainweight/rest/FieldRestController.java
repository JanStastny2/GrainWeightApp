package cz.uhk.grainweight.rest;

import cz.uhk.grainweight.model.Field;
import cz.uhk.grainweight.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
public class FieldRestController {

    private final FieldService fieldService;

    @Autowired
    public FieldRestController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/getall")
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Field> getField(@PathVariable long id) {
        return fieldService.getField(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public Field createOrUpdateField(@RequestBody Field field) {
        return fieldService.saveField(field);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Field> deleteField(@PathVariable long id) {
        return fieldService.getField(id)
                .map(field -> {
                    fieldService.deleteField(id);
                    return ResponseEntity.ok(field);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
