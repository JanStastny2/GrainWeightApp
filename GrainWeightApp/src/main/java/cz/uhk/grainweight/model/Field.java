package cz.uhk.grainweight.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Double area;
    private String location;





}
