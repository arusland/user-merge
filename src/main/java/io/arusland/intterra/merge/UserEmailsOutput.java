package io.arusland.intterra.merge;

import io.arusland.intterra.entity.User;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static io.arusland.intterra.merge.Shared.DELIM;

public class UserEmailsOutput {
    private final List<User> users;

    public UserEmailsOutput(List<User> users) {
        this.users = users;
    }

    public void write(OutputStream output) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output))) {

            for (User user: users) {
                bw.write(user.getName());
                bw.write(DELIM);
                bw.write(user.getEmails().stream().collect(Collectors.joining(", ")));
                bw.newLine();
            }
        }
    }
}
