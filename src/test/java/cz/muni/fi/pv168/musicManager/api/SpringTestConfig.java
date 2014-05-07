package cz.muni.fi.pv168.musicManager.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

/**
 * Created by Hany on 30.4.2014.
 */
@Configuration
@EnableTransactionManagement
public class SpringTestConfig {

    @Bean
    public DataSource dataSource() {
        //embedded databáze
        return new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("classpath:my-schema.sql")
                .addScript("classpath:my-test-data.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public AlbumManager albumManager() throws AlbumException
    {
        return new AlbumManagerImpl(dataSource());
    }

    @Bean
    public SongManager songManager() throws SongException
    {
        return new SongManagerImpl(dataSource());
    }

    @Bean
    public MusicManager musicManager() throws MusicManagerException, AlbumException, SongException
    {
        MusicManagerImpl musicManager = new MusicManagerImpl(dataSource());
        musicManager.setAlbumManager(albumManager());
        musicManager.setSongManager(songManager());

        return musicManager;
    }
}
