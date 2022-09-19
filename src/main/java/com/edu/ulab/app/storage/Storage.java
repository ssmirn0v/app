package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.HavingId;

import java.util.Optional;

public interface Storage<T extends HavingId> {
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции

    void save(T e);

    void delete(Long id);

    Optional<T> get(Long id);

    boolean existsById(Long id);


}
