package io.arusland.intterra.merge;

import io.arusland.intterra.entity.User;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserEmailsMergerTest {
    @Test
    public void testSimpleInput() throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("input1.txt");

        List<User> users = merger.parse(stream)
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(toList());

        assertEquals(2, users.size());

        User user1 = users.get(0);
        assertEquals("user1", user1.getName());
        assertEquals(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru",
                "ups@pisem.net", "aaa@bbb.ru"), new ArrayList<String>(user1.getEmails()));

        User user2 = users.get(1);
        assertEquals("user3", user2.getName());
        assertEquals(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com"),
                new ArrayList<String>(user2.getEmails()));
    }

    @Test
    public void testParseAnyUsersIsUnique() throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("input3.txt");

        List<User> users = merger.parse(stream)
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(toList());

        assertEquals(5, users.size());

        User user1 = users.get(0);
        assertEquals("superuser1", user1.getName());
        assertEquals(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"),
                new ArrayList<String>(user1.getEmails()));

        User user2 = users.get(1);
        assertEquals("superuser2", user2.getName());
        assertEquals(Arrays.asList("bar@gmail.com", "ups@pisem.net"),
                new ArrayList<String>(user2.getEmails()));

        User user3 = users.get(2);
        assertEquals("superuser3", user3.getName());
        assertEquals(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com"),
                new ArrayList<String>(user3.getEmails()));

        User user4 = users.get(3);
        assertEquals("superuser4", user4.getName());
        assertEquals(Arrays.asList("miror@pisem.net", "aaa@bbb.ru"),
                new ArrayList<String>(user4.getEmails()));

        User user5 = users.get(4);
        assertEquals("superuser5", user5.getName());
        assertEquals(Arrays.asList("abc@pisem.net"),
                new ArrayList<String>(user5.getEmails()));
    }

    @Test
    public void testSingleEmail() throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("input2.txt");

        List<User> users = merger.parse(stream);

        assertEquals(1, users.size());

        User user1 = users.get(0);
        assertEquals("user1", user1.getName());
        assertEquals(Arrays.asList("xxx@ya.ru"), new ArrayList<String>(user1.getEmails()));
    }

    @Test
    public void testParseEmptyFile() throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("empty.txt");

        List<User> users = merger.parse(stream);

        assertEquals(0, users.size());
    }

    @Test
    public void testFailWhenFormatIsInvalid() {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("inputInvalid.txt");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> merger.parse(stream));
        assertEquals("Invalid input format at line (3): '-> xyz@pisem.net, vasya@pupkin.com'",
                ex.getMessage());
    }
}
