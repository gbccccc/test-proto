package example;

import example.protobuf.addressbook.AddressBook;
import example.protobuf.addressbook.Person;
import example.protobuf.service.SearchById;
import example.protobuf.service.SearchService;
import example.protobufimp.service.SearchServiceChannelLocal;
import example.protobufimp.service.SearchServiceImp;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // read file
        AddressBook addressBookInput;
        try (InputStream is = new FileInputStream("./protobuf.dat")) {
            addressBookInput = AddressBook.parseFrom(is);
        } catch (IOException e) {
            addressBookInput = AddressBook.newBuilder()
                    .addPeople(Person.newBuilder()
                            .setId(0)
                            .setEmail("000")
                            .setName("name0")
                            .build())
                    .addPeople(Person.newBuilder()
                            .setId(1)
                            .setEmail("111")
                            .setName("name1")
                            .build())
                    .addPeople(Person.newBuilder()
                            .setId(2)
                            .setEmail("222")
                            .setName("name2")
                            .build())
                    .build();
        }
        List<Person> peopleList = addressBookInput.getPeopleList();
        for (Person person : peopleList) {
            System.out.println(person);
        }

        // modify protobuf message object
        AddressBook addressBookChanged = AddressBook.newBuilder(addressBookInput)
                .addPeople(Person
                        .newBuilder()
                        .setId(peopleList.size())
                        .setEmail("email")
                        .setName("name" + peopleList.size())
                        .build())
                .build();

        // write file
        try (OutputStream os = new FileOutputStream("./protobuf.dat")) {
            addressBookChanged.writeTo(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // protobuf service
        final int searchedId = 10;
        SearchService service = SearchService.newStub(new SearchServiceChannelLocal(new SearchServiceImp(addressBookChanged)));
        service.searchPerson(null, SearchById.newBuilder().setId(searchedId).build(),
                person -> {
                    System.out.println("Search Service Respond:");
                    System.out.println(person);
                });
    }
}
