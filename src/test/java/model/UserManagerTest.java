package test.java.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import model.UserManager;
import model.UserTypeEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Assert;

/**
 * Created by evankaplan on 11/13/16.
 */
public class UserManagerTest {
    @Test
    public void getUsersTest() {

        UserManager manager = new UserManager();

        //add "Bob Waters" to system
        manager.createUser("Bob Waters", "Evan", "Evan Kaplan", UserTypeEnum.USER);


        //Bob Waters User
        User BobWaters = new User();
        BobWaters.set_username("Bob Waters");

        //create an observable list with "Bob Waters" user in it
        List<User> listWithBob = new ArrayList<>();
        listWithBob.add(BobWaters);
        ObservableList<User> observableBob = FXCollections.observableList(listWithBob);

        //get the output of getUsers hoping to not have "Bob Waters"
        ObservableList<User> test1 = manager.getUsers(BobWaters);

        //check1 should remain false
        boolean check1 = false;
        boolean bool;
        for (int i = 0; i < test1.size(); i++) {
            bool = observableBob.get(0).get_username().equals(test1.get(i).get_username());
            check1 = check1 || bool;
        }

        assertEquals("The current user was outputted.", false, check1);

        ////////////////

        //dummy input to getUsers
        User fakeInput = new User();
        fakeInput.set_username("not a username");

        //get the output of getUsers hoping have "Bob Waters"
        ObservableList<User> test2 = manager.getUsers(fakeInput);

        //check2 should be true
        boolean check2 = false;
        boolean bool2;
        for (int i = 0; i < test2.size(); i++) {
            bool2 = observableBob.get(0).get_username().equals(test2.get(i).get_username());
            check2 = check2 || bool2;
        }

        assertEquals("The new added user was not outputted", true, check2);


        ///////////////////////


        //user that gets checked for exception
        User badUser = null;

        try {
            manager.getUsers(badUser);
        } catch (Exception e) {
            Assert.fail("An exception should be caught for getting a user that does not exist");
        }
        ObservableList<User> badList = manager.getUsers(badUser);
        assertEquals("An exception should be caught for getting a user that does not exist", null, badList);

        //after code delete the user created "Bob Waters"
        List<User> list1 = new ArrayList<>();
        list1.add(BobWaters);
        manager.deleteUsers(list1, list1);

        /////////////

        //check against users in database
        ObservableList<User> test3 = manager.getUsers(fakeInput);
        int userNumber = 13;
        String[] currUserList = {"abhayd", "evan", "evankaplan", "ft", "ftsenn", "ftsenn-admin", "ftsenn-manager",
                "ftsenn-worker", "m", "u", "user", "w", "a"};

        boolean check3 = true;
        int count = 0;
        int prevCount;
        for (int j = 0; j < userNumber; j++) {
            prevCount = count;
            for (int i = 0; i < userNumber; i++) {
                if (currUserList[j].equals(test3.get(i).get_username())) {
                    count++;
                }
            }
            if (prevCount != count - 1) {
                check3 = false;
            }
        }

        assertEquals("Not all the users at the current state of this database were outputted", true, check3);


    }

    @Test
    public void deleteUsersTest() {
        UserManager manager = new UserManager();
        User delUser = new User("foo_delete", "Foo Bar", 0, UserTypeEnum.USER.toString(), "foo@bar.io", "", "", "", "");
        User testUser = new User("local", "Local User", 0, UserTypeEnum.ADMIN.toString(), "","","","","");

        // test 1: delete a known existent user
        manager.createUser(delUser.get_username(), "password", delUser.get_fullname(), UserTypeEnum.USER);

        List<User> mainList = manager.getUsers(testUser);

        ArrayList<User> removeList = new ArrayList<>();
        for(User u : mainList) {
            if(delUser.get_username().equals(u.get_username())) {
                removeList.add(u);
            }
        }
        assertTrue("deletion user not found in database", removeList.size() > 0);

        manager.deleteUsers(removeList, mainList);
        assertFalse("deletion user was not removed from the main list", mainList.contains(removeList.get(0)));

        List<User> listWithoutTestUser = manager.getUsers(delUser);
        assertFalse("deletion user was not removed from the database", listWithoutTestUser.contains(removeList.get(0)));

        // test 2: attempt to delete a user that does not exist in the database
        mainList = manager.getUsers(testUser);
        int beforeSize = mainList.size();
        removeList = new ArrayList<>();
        removeList.add(testUser); //testUser is local only
        manager.deleteUsers(removeList, mainList);

        assertEquals("something was changed in the view list", mainList.size(), beforeSize);

        mainList = manager.getUsers(testUser);
        assertEquals("something was changed in the database", mainList.size(), beforeSize);
    }

    /**
     * Created by Alex McArthur on 11/16/16.
     */
    @Test
    public void banUsersTest() {
        UserManager manager = new UserManager();
        User evilUser = new User("hacker", "Evil Hacker", 0, UserTypeEnum.USER.toString(), "evil@hacks.io", "", "", "", "");
        User testUser = new User("local", "Local User", 0, UserTypeEnum.ADMIN.toString(), "","","","","");

        // test 1: ban a known existent user
        manager.createUser(evilUser.get_username(), "password", evilUser.get_fullname(), UserTypeEnum.USER);

        List<User> mainList = manager.getUsers(testUser);

        ArrayList<User> banList = new ArrayList<>();
        for(User u : mainList) {
            if(evilUser.get_username().equals(u.get_username())) {
                banList.add(u);
            }
        }
        assertTrue("evil user not found in database", banList.size() > 0);
        manager.banUsers(banList, mainList);
        assertFalse("evil user was not removed from the main list", mainList.contains(banList.get(0)));
        try {
            assertTrue("evil user was not successfully banned", manager.getUsersWithUsername(evilUser.get_username()).getInt(3) == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<User> listWithoutTestUser = manager.getUsers(evilUser);
        assertFalse("evil user was not removed from the database", listWithoutTestUser.contains(banList.get(0)));

        // test 2: attempt to ban a user that does not exist in the database
        mainList = manager.getUsers(testUser);
        int beforeSize = mainList.size();
        banList = new ArrayList<>();
        banList.add(testUser); //testUser is local only
        manager.banUsers(banList, mainList);

        assertEquals("something was changed in the view list", mainList.size(), beforeSize);

        mainList = manager.getUsers(testUser);
        assertEquals("something was changed in the database", mainList.size(), beforeSize);
    }

}