package com.foh.data.entities.ch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import com.foh.data.entities.usermgmt.UserEntity;

@Entity
@Table(schema = "chat", name = "GroupUsers")
@IdClass(GroupUserId.class)
public class GroupUser {

    @Id
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Column(nullable = false)
    private Instant addedAt = Instant.now();

    // Getters and Setters
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}
