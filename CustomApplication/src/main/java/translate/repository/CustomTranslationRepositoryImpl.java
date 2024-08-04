package translate.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import translate.entity.CustomTranslation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomTranslationRepositoryImpl implements CustomTranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomTranslationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(CustomTranslation translation) {
        String sql = "INSERT INTO custom_translation_requests (id, ip_address, source_lang, target_lang, input_text, translated_text) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID(), translation.getIpAddress(), translation.getSourceLang(), translation.getTargetLang(), translation.getInputText(), translation.getTranslatedText());
    }

    @Override
    public CustomTranslation findById(UUID id) {
        String sql = "SELECT * FROM custom_translation_requests WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomTranslationRowMapper());
    }

    @Override
    public List<CustomTranslation> findAll() {
        String sql = "SELECT * FROM custom_translation_requests";
        return jdbcTemplate.query(sql, new CustomTranslationRowMapper());
    }

    private static class CustomTranslationRowMapper implements RowMapper<CustomTranslation> {
        @Override
        public CustomTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CustomTranslation.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .ipAddress(rs.getString("ip_address"))
                    .sourceLang(rs.getString("source_lang"))
                    .targetLang(rs.getString("target_lang"))
                    .inputText(rs.getString("input_text"))
                    .translatedText(rs.getString("translated_text"))
                    .build();
        }
    }
}

