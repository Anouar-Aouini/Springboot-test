package com.fivepoints.springboottest.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt", "users"})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private long id;

    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Column(name = "description")
    private String description;
    @NonNull
    @Column(name = "quantity")
    private String quantity;
    @NonNull
    @Column(name = "price")
    private String price;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;


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