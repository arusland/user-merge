package io.arusland.intterra.merge;

import io.arusland.intterra.entity.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.arusland.intterra.merge.Shared.DELIM;
import static java.util.stream.Collectors.toList;

public class UserEmailsMerger {
    private final Map<String, User> emailUsers = new HashMap<>();

    public List<User> parse(InputStream input) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            int linNumber = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                linNumber++;

                if (!line.isEmpty()) {
                    parseLine(line, linNumber);
                }
            }
        }

        return emailUsers
                .values()
                .stream()
                .distinct()
                .collect(toList());
    }

    private void parseLine(String line, int lineNumber) {
        int index = line.indexOf(DELIM);

        if (index <= 0) {
            throw new RuntimeException(String.format("Invalid input format at line (%d): '%s'", lineNumber, line));
        }

        String userName = line.substring(0, index);
        String[] emails = line.substring(index + DELIM.length()).split("[,\\s]+");

        mergeUser(userName, emails);
    }

    private void mergeUser(String userName, String[] emails) {
        List<User> users = Arrays.stream(emails)
                .map(e -> emailUsers.get(e))
                .filter(p -> p != null)
                .collect(toList());

        if (users.isEmpty()) {
            // create new  user
            User user = User.create(userName);

            for (String email : emails) {
                emailUsers.put(email, user);
                user.addEmail(email);
            }
        } else {
            // parse all found users as first one
            User firstUser = users.get(0);

            for (int i = 1; i < users.size(); i++) {
                users.get(i).mergeAsUser(firstUser);
            }

            for (String email : emails) {
                emailUsers.put(email, firstUser);
                firstUser.addEmail(email);
            }
        }
    }
}
