package hbue.it.pojo;

public class Column_info {
    private Integer id;

    private Integer sequence;

    private String name;

    private String icontype;

    private Boolean choose;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcontype() {
        return icontype;
    }

    public void setIcontype(String icontype) {
        this.icontype = icontype == null ? null : icontype.trim();
    }

    public Boolean getChoose() {
        return choose;
    }

    public void setChoose(Boolean choose) {
        this.choose = choose;
    }
}