package planet;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "planet")
public class Planet {

    @Id
    @Column(length = 100, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

}