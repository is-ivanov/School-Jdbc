package ua.com.foxminded.sqljdbcschool.dao;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public interface Dao<T> {

    void add(T t) throws DAOException;
    
    Optional<T> getById(int id) throws DAOException;
    
    List<T> getAll() throws DAOException;
    
    void update(T t) throws DAOException;
    
    void delete(T t) throws DAOException;
    
}
