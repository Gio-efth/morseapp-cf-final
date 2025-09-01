package gr.aueb.cf.morseapp.model;

import gr.aueb.cf.morseapp.core.enums.TranslationDirection;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "translations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_text", columnDefinition = "TEXT", nullable = false)
    private String inputText;

    @Column(name = "output_text", columnDefinition = "TEXT", nullable = false)
    private String outputText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TranslationDirection direction;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
