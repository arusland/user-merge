# Users merge by emails

## Build
```bash
mvn clean package
cd target
# rename file
mv user-merge-jar-with-dependencies.jar user-merge.jar
```

## Usage
Merge users from input file (see format below)

```bash
java -jar user-merge.jar input.txt
```

Or you can merge users from stdin

```bash
java -jar user-merge.jar
```

### Input format sample
```
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru
user2 -> foo@gmail.com, ups@pisem.net
user3 -> xyz@pisem.net, vasya@pupkin.com
user4 -> ups@pisem.net, aaa@bbb.ru
user5 -> xyz@pisem.net
```
