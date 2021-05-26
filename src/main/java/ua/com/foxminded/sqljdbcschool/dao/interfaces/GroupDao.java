package ua.com.foxminded.sqljdbcschool.dao.interfaces;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public interface GroupDao extends Dao<Group> {

    List<Group> getGroupsWithLessEqualsStudentCount(int studentCount) throws DAOException;

}
