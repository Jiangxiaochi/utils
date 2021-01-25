package org.xc.utils.spring.test;

public class Tree {

    private Integer id;
    private Integer pid;
    private String name;

    public Tree() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                '}';
    }
}
