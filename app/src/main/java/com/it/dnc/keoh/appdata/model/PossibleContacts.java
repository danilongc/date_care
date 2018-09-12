package com.it.dnc.keoh.appdata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnc on 05/09/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PossibleContacts implements Serializable {


    private List<ContactsLikes> contactsLikes;
    private List<Integer> ids;


    public PossibleContacts() {

    }

    public List<ContactsLikes> getContactsLikes() {
        return contactsLikes;
    }

    public void setContactsLikes(List<ContactsLikes> contactsLikes) {
        this.contactsLikes = contactsLikes;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public void addId(Integer id){
        if(ids == null ){
            ids = new ArrayList<>();
        }

        ids.add(id);
    }


    @JsonIgnore
    public ContactsLikes getById(Integer id){
        int index = 0 ;

        while(contactsLikes.get(index) != null){
            if(contactsLikes.get(index).getId().equals(id)){
                return contactsLikes.get(index);
            }
            index++;
        }

        return null;
    }
}
