package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Field;

import java.util.List;
import java.util.Optional;

public interface FieldService {
    List<Field> getAllFields();
    Optional<Field> getField(Long id);
    Field saveField(Field field);
    void deleteField(Long id);
}