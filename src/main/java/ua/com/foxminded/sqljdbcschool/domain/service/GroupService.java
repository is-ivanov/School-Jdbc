package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.GroupDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class GroupService {
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save group %s in base";

    private GroupDao groupDao;
    private Generator<Group> generator;

    public GroupService(GroupDao groupDao, Generator<Group> generator) {
        this.groupDao = groupDao;
        this.generator = generator;
    }

    public void createTestGroups(int numberGroups) {
        List<Group> groups = generator.generate(numberGroups);
        groups.forEach(this::addGroupToBase);
    }

    public List<Group> getGroupsWithLessEqualsStudentCount(int studentCount) {

        try {
            return groupDao.getGroupsWithLessEqualsStudentCount(studentCount);
        } catch (DAOException e) {
            throw new DomainException("Can't get groups", e);
        }
    }

    public void addGroupToBase(Group group) {
        try {
            groupDao.add(group);
        } catch (DAOException e) {
            throw new DomainException(
                    String.format(MESSAGE_MASK_EXCEPTION, group), e);
        }
    }
}
