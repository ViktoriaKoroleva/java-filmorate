package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MpaService {
    @Autowired
    private MpaStorage mpaStorage;

    public List<Mpa> getAll() {
        return new ArrayList<>(mpaStorage.getAll().values());
    }

    public Mpa get(Long id) {
        Map<Long, Mpa> mpas = mpaStorage.getAll();
        if (mpas.containsKey(id)) {
            return mpas.get(id);
        } else {
            throw new ValidationException("В бд нет MPA с id=" + id);
        }
    }
}