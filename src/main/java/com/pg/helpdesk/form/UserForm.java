package com.pg.helpdesk.form;

import com.pg.helpdesk.entity.User;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserForm {
    @Size(min=5)
    @NotNull
    String username;
    @NotNull
    @Email
    String email;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @Size(min=4)
    @NotNull
    String password;
    @Size(min=4)
    @NotNull
    String passwordConfirm;
    Set<Long> roles = new HashSet<>();

    @Valid
    public boolean isPasswordMatch() {
        return password.equals(passwordConfirm);
    }

    public void fromEntity(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
