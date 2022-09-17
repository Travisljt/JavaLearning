package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class DBTests {

  private DBServer server;

  // we make a new server for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup(@TempDir File dbDir) {
    // Notice the @TempDir annotation, this instructs JUnit to create a new temp directory somewhere
    // and proceeds to *delete* that directory when the test finishes.
    // You can read the specifics of this at
    // https://junit.org/junit5/docs/5.4.2/api/org/junit/jupiter/api/io/TempDir.html

    // If you want to inspect the content of the directory during/after a test run for debugging,
    // simply replace `dbDir` here with your own File instance that points to somewhere you know.
    // IMPORTANT: If you do this, make sure you rerun the tests using `dbDir` again to make sure it
    // still works and keep it that way for the submission.

    server = new DBServer(dbDir);
  }

  // Here's a basic test for spawning a new server and sending an invalid command,
  // the spec dictates that the server respond with something that starts with `[ERROR]`
  @Test
  void testInvalidCommandIsAnError() throws IOException {
    assertTrue(
        server
            .handleCommand("insert into tableName values('st',age,mark);")
            .startsWith("[ERROR]\nUse database first"));
    assertTrue(server.handleCommand("create database abc;").startsWith("[OK]"));
    assertTrue(server.handleCommand("use tableName;").startsWith("[ERROR]\nUnknown database"));
    assertTrue(server.handleCommand("foo").startsWith("[ERROR]"));
    assertTrue(server.handleCommand("use abc;").startsWith("[OK]"));
    assertTrue(server.handleCommand("create table;").startsWith("[ERROR]"));
    assertTrue(server.handleCommand("create table tableName;").startsWith("[OK]"));
    assertTrue(server.handleCommand("insert into values('st',age,mark);").startsWith("[ERROR]"));
    assertTrue(
        server
            .handleCommand("insert into mark values('st',age,mark);")
            .startsWith("[ERROR]\nTable isn't exist"));
    assertTrue(
        server.handleCommand("SELECT * FROM tableName").startsWith("[ERROR]\nSemi colon missing"));
    assertTrue(
        server.handleCommand("SELECT FROM tableName;").startsWith("[ERROR]\nattributes is null"));
    assertTrue(server.handleCommand("create table mark(name,sex,grade,pass);").startsWith("[OK]"));
    assertTrue(
        server.handleCommand("insert into mark values('ste',male,40,fail);").startsWith("[OK]"));
    assertTrue(
        server.handleCommand("insert into mark values('aaa',male,50,pass);").startsWith("[OK]"));
    assertTrue(server.handleCommand("SELECT * FROM tableName name=='ste';").startsWith("[ERROR]"));
    assertTrue(
        server
            .handleCommand("alter table mark drop age;")
            .startsWith("[ERROR]\nCan not find the attribute"));
    assertTrue(server.handleCommand("drop database easy;").startsWith("[ERROR]"));
    assertTrue(server.handleCommand("drop table easy;").startsWith("[ERROR]"));
    assertTrue(server.handleCommand("drop table easy;").startsWith("[ERROR]"));
    assertTrue(
        server.handleCommand("update tableName set where name=='ste';").startsWith("[ERROR]"));
    assertTrue(server.handleCommand("delete tableName where name=='ste';").startsWith("[ERROR]"));
    assertTrue(server.handleCommand("delete from where name=='ste';").startsWith("[ERROR]"));
    assertTrue(
        server.handleCommand("join tableName and mark on name and pe;").startsWith("[ERROR]"));
  }

  @Test
  void testValidCommandIsOK() throws IOException {
    assertEquals(server.handleCommand("CREATE DATABASE markBook;"), ("[OK]\n"));
    assertEquals(server.handleCommand("USE markBook;"), ("[OK]\n"));
    assertEquals(server.handleCommand("CREATE TABLE marks (name, mark, pass);"), ("[OK]\n"));
    assertEquals(server.handleCommand("INSERT INTO marks VALUES ('Steve', 65, TRUE);"), ("[OK]\n"));
    assertEquals(server.handleCommand("INSERT INTO marks VALUES ('Dave', 55, TRUE);"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("INSERT INTO marks VALUES ('Travis', 35, FALSE);"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("INSERT INTO marks VALUES ('Clive', 20, FALSE);"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks;"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n2\tDave\t55\tTRUE\t\n3\tTravis\t35\tFALSE\t\n4\tClive\t20\tFALSE\t\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks WHERE name != 'Dave';"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n3\tTravis\t35\tFALSE\t\n4\tClive\t20\tFALSE\t\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks WHERE pass == TRUE;"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n2\tDave\t55\tTRUE\t\n"));
    assertEquals(
        server.handleCommand("SELECT id,name,pass FROM marks;"),
        ("[OK]\nid\tname\tpass\t\n1\tSteve\tTRUE\t\n2\tDave\tTRUE\t\n3\tTravis\tFALSE\t\n4\tClive\tFALSE\t\n"));
    assertEquals(
        server.handleCommand(
            "SELECT * FROM marks where (name!='Dave')and((pass==TRUE)or(mark>=35));"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n3\tTravis\t35\tFALSE\t\n"));
    assertEquals(server.handleCommand("CREATE TABLE coursework (task, grade);"), ("[OK]\n"));
    assertEquals(server.handleCommand("INSERT INTO coursework VALUES ('OXO', 3);"), ("[OK]\n"));
    assertEquals(server.handleCommand("INSERT INTO courseworkVALUES ('DB', 1);"), ("[OK]\n"));
    assertEquals(server.handleCommand("INSERT INTO coursework VALUES ('OXO', 4);"), ("[OK]\n"));
    assertEquals(server.handleCommand("INSERT INTO coursework VALUES ('STAG', 2);"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("JOIN coursework AND marks ON grade AND id;"),
        ("[OK]\nid\ttask\tname\tmark\tpass\t\n1\tOXO\tSteve\t65\tTRUE\t\n2\tDB\tDave\t55\tTRUE\t\n3\tOXO\tTravis\t35\tFALSE\t\n4\tSTAG\tClive\t20\tFALSE\t\n"));

    assertEquals(
        server.handleCommand("UPDATE marks SET mark = 38 WHERE name == 'Clive';"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks WHERE name == 'Clive';"),
        ("[OK]\nid\tname\tmark\tpass\t\n4\tClive\t38\tFALSE\t\n"));
    assertEquals(server.handleCommand("DELETE FROM marks WHERE name == 'Dave';"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks;"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n3\tTravis\t35\tFALSE\t\n4\tClive\t38\tFALSE\t\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks WHERE (pass==FALSE)AND(mark>35);"),
        ("[OK]\nid\tname\tmark\tpass\t\n4\tClive\t38\tFALSE\t\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks WHERE name LIKE 've';"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n4\tClive\t38\tFALSE\t\n"));
    assertEquals(
        server.handleCommand("SELECT name FROM marks WHERE mark>60;"), ("[OK]\nname\t\nSteve\t\n"));
    assertEquals(server.handleCommand("DELETE FROM marks WHERE mark<40;"), ("[OK]\n"));
    assertEquals(
        server.handleCommand("SELECT * FROM marks;"),
        ("[OK]\nid\tname\tmark\tpass\t\n1\tSteve\t65\tTRUE\t\n"));
    assertEquals(server.handleCommand("DROP table marks;"), ("[OK]\n"));
    assertTrue(
            server.handleCommand("SELECT * FROM marks;").startsWith("[ERROR]"));
    assertEquals(server.handleCommand("DROP database markBook;"), ("[OK]\n"));
    assertTrue(
            server.handleCommand("SELECT * FROM marks;").startsWith("[ERROR]"));
  }

  // Add more unit tests or integration tests here.
  // Unit tests would test individual methods or classes whereas integration tests are geared
  // towards a specific use case (i.e. creating a table and inserting rows and asserting whether the
  // rows are actually inserted)

}
