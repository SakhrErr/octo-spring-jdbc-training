package ma.octo.aop.repository.impl;

import ma.octo.aop.entity.Language;
import ma.octo.aop.repository.LanguageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class LanguageRepositoryImpl implements LanguageRepository {

  private final DataSource dataSource;

  private JdbcTemplate jdbcTemplate;

  public LanguageRepositoryImpl(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }


  @Override
  public Optional<Language> findByExtension(final String extension) {
    return jdbcTemplate.query(String.format("select * from LANGUAGE where fileExtension='%s'", extension),
            (resultSet, i) -> toLanguage(resultSet))
            .stream().findAny();
  }

  @Override
  public Optional<Language> findById(final String id) {
    return jdbcTemplate.query(String.format("select * from LANGUAGE where fileExtension='%s'", id),
            (resultSet, i) -> toLanguage(resultSet))
            .stream().findAny();
  }

  @Override
  public List<Language> findAll() {
    return jdbcTemplate.query("select * from LANGUAGE", (resultSet, i) -> toLanguage(resultSet));
  }

//  private Predicate<Language> languageExtensionPredicate(final String extension) {
//    return language -> Objects.equals(extension, language.getFileExtension());
//  }
//
//  private Predicate<Language> languageIdPredicate(final String id) {
//    return language -> Objects.equals(id, language.getId());
//  }

  private Language toLanguage(ResultSet resultSet) throws SQLException {
    return new Language(
            resultSet.getString("id"),
            resultSet.getString("name"),
            resultSet.getString("author"),
            resultSet.getString("fileExtension")
    );
  }

}
