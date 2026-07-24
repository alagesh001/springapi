package com.oxad.admin_api.dto;

public class RegisterRequest {

    private String owner;
    private String gymname;
    private String location;
    private String email;
    private Long mob;
    private Integer plantype = 1;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGymname() {
        return gymname;
    }

    public void setGymname(String gymname) {
        this.gymname = gymname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMob() {
        return mob;
    }

    public void setMob(Long mob) {
        this.mob = mob;
    }

    public Integer getPlantype() {
        return plantype;
    }

    public void setPlantype(Integer plantype) {
        this.plantype = plantype;
    }
}
