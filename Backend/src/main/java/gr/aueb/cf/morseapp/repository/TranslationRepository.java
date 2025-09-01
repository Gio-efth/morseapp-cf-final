package gr.aueb.cf.morseapp.repository;

import gr.aueb.cf.morseapp.model.Translation;
import gr.aueb.cf.morseapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

    List<Translation> findAllByUser(User user);

    List<Translation> findAllByUserOrderByTimestampDesc(User user);

    List<Translation> findByUser(User user);
}
