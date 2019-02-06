package io.arusland.intterra;

import io.arusland.intterra.entity.User;
import io.arusland.intterra.merge.UserEmailsMerger;
import io.arusland.intterra.merge.UserEmailsOutput;

import java.io.FileInputStream;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws Exception {
        UserEmailsMerger merger = new UserEmailsMerger();
        List<User> users;

        if (args.length > 0) {
            users = merger.parse(new FileInputStream(args[0]));
        } else {
            users = merger.parse(System.in);
        }

        UserEmailsOutput output = new UserEmailsOutput(users);

        output.write(System.out);
    }
}
