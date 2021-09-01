package by.rest.messenger;

import by.rest.messenger.model.entity.User;
import by.rest.messenger.utils.ObjectsMerger;

import java.time.LocalDate;

public class ObjectsMergeTest {

    public static void main(String[] args) {

        User oldUser = new User(
                "melodiclark56",
                "Name",
                "Second Name",
                LocalDate.now(),
                "11111111"
        );


        User newUser = new User();

        newUser.setLogin("melodiclark56");
        newUser.setFirstName("Vlas");
        newUser.setLastName("Kastsiukovich");
        newUser.setPassword("12345678");

        System.out.println(oldUser);
        System.out.println(newUser);

        newUser = ObjectsMerger.merge(oldUser, newUser).get();


        System.out.println(newUser);


    }

}
