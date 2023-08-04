package example.protobufimp.service;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import example.protobuf.addressbook.AddressBook;
import example.protobuf.addressbook.Person;
import example.protobuf.service.SearchById;
import example.protobuf.service.SearchService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchServiceImp extends SearchService {
    private AddressBook addressBook;
    @Override
    public void searchPerson(RpcController controller, SearchById request, RpcCallback<Person> done) {
        for (Person person : addressBook.getPeopleList()) {
            if (person.getId() == request.getId()) {
                done.run(person);
                return;
            }
        }
        done.run(null);
    }
}
