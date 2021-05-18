package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.GroupDao;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@SuppressWarnings("java:S106")
public class GroupService {
    private GroupDao groupDao;
    
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }


    public List<Group> getGroupsWithLessEqualsStudentCount(int studentCount) {
        
        try {
            return groupDao.getGroupsWithLessEqualsStudentCount(studentCount);
        } catch (DAOException e) {
            throw new DomainException("Can't get groups", e);
        }
    }
}
