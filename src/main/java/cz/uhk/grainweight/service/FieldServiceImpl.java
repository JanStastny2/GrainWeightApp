package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Field;
import cz.uhk.grainweight.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;

    @Override
    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    @Override
    public Field getField(Long id) {
        return fieldRepository.findById(id).orElse(null);
    }

    @Override
    public Field saveField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public void deleteField(Long id) {
        fieldRepository.deleteById(id);
    }
}