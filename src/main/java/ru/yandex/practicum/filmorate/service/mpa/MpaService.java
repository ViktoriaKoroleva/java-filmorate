package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

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
        Map<Long, Mpa> mpaMap = mpaStorage.getAll();
        if (mpaMap.containsKey(id)) {
            return mpaMap.get(id);
        } else {
            throw new NotFoundException("В бд нет MPA с id=" + id);
        }
    }
}