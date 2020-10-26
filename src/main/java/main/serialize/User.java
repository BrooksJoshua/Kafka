package main.serialize;

import java.io.Serializable;
import java.util.Date;

/**
 * created by Joshua.H.Brooks on 2020.10月.23.08.28
 */
public class User implements Serializable {
   private Integer id;
   private String name;
   private Date bod;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bod=" + bod +
                '}';
    }
    public User() {
       super();
    }


    public User(Integer id, String name, Date bod) {
        this.id = id;
        this.name = name;
        this.bod = bod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBod() {
        return bod;
    }

    public void setBod(Date bod) {
        this.bod = bod;
    }
}
