package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Audited
@Table(name = "mtp_user",
        uniqueConstraints= {
                @UniqueConstraint(columnNames = "mobileNumber")
        },
        indexes = {
                @Index(columnList = "mobileNumber, status")
        }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Configurable
public class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String mobileNumber;
    @JsonIgnore
    private String deviceToken;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToOne(fetch=FetchType.EAGER)
    @JsonIgnoreProperties({ "legalRepresentativeName",
            "companyNumber",
            "lastModifiedDate" })
    private Company company;
    @NonNull
    private UserStatus status;
    @Override
    @JsonIgnore
    /**
     * IMP: ROLE_ is prefixed for the preauthorize to work.
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return mobileNumber;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return mobileNumber;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}