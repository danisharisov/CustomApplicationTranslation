package translate.repository;

import translate.entity.CustomTranslation;

import java.util.List;
import java.util.UUID;

public interface CustomTranslationRepository {
    void save(CustomTranslation translation);
    CustomTranslation findById(UUID id);
    List<CustomTranslation> findAll();
}

