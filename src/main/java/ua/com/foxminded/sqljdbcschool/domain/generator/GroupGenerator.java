package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.entity.Group;

public class GroupGenerator implements Generator<Group> {
    private static final String GROUP_DELIMITER = "-";

    private Random random;

    public GroupGenerator(Random random) {
        this.random = random;
    }

    public List<Group> generate(int numberGroups) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberGroups; i++) {
            Group group = new Group(generateName());
            groups.add(group);
        }
        return groups;
    }

    private String generateName() {
        int leftLimit = 65;
        int rightLimit = 90;
        int targetStringLength = 2;

        String leftPartName = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        int rightPartName = random.nextInt(90) + 10;

        return leftPartName + GROUP_DELIMITER + rightPartName;
    }

}
