package domain;

public class Student extends Entity<String>{
    private String name;
    private Integer group;
    private String email;

    public Student(String id,String name, Integer group, String email) {
        this.setId(id);
        this.name = name;
        this.group = group;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getId().toString()+' '+name + ' ' +
                + group
                ;
    }
}
