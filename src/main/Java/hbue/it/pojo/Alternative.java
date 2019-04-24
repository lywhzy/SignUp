package hbue.it.pojo;

public class Alternative {
    private Integer id;

    private Integer cid;

    private String value;

    private Boolean user_permit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Boolean getUser_permit() {
        return user_permit;
    }

    public void setUser_permit(Boolean user_permit) {
        this.user_permit = user_permit;
    }
}