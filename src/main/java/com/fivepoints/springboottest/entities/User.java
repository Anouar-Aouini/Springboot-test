package com.fivepoints.springboottest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt",  "role"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private long id;

    @NonNull
    @Column(name = "firstName")
    private String firstName;
    @NonNull
    @Column(name = "lastName")
    private String lastName;
    @NonNull
    @Column(name = "email")
    private String email;
    @NonNull
    @Column(name = "password")
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Product> products;

    // ManyToMany Relations
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> role = new HashSet<>();

    @Setter(value = AccessLevel.NONE)
    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Setter(value = AccessLevel.NONE)
    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}
