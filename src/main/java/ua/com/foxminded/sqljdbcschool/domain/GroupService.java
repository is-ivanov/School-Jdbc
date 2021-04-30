package ua.com.foxminded.sqljdbcschool.domain;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@SuppressWarnings("java:S106")
public class GroupService {
    
    public void findGroupsWithLessEqualsStudentCount(int studentCount) {
        GroupDao groupDao = new GroupDao();
        
        try {
            List<Group> groups = groupDao.getGroupsWithLessEqualsStudentCount(studentCount);
            groups.stream().forEach(System.out::println);
        } catch (DAOException e) {
            throw new DomainException("Can't get groups", e);
        }
    }
}
