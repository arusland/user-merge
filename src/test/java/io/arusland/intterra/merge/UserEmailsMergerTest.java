package io.arusland.intterra.merge;

import io.arusland.intterra.entity.User;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserEmailsMergerTest {
    @Test
    public void testSimpleInput() throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("input1.txt");

        List<User> users = merger.parse(stream);

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
