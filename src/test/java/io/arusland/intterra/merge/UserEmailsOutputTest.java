package io.arusland.intterra.merge;

import io.arusland.intterra.entity.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEmailsOutputTest {
    @Test
    public void testSimpleInput() throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream("input1.txt");
        List<User> users = merger.parse(stream);

        UserEmailsOutput output = new UserEmailsOutput(users);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        output.write(buffer);

        assertEquals("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n", buffer.toString("UTF-8"));
    }
}
