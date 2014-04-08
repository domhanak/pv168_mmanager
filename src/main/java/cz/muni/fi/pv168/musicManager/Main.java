package cz.muni.fi.pv168.musicManager;

/**
 * Created by Adam on 19.3.14.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;
public class Main {

    public static void main(String[] args) throws AlbumException, IOException
    {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        AlbumManager albumManager = ctx.getBean(AlbumManager.class);

        Album album = new Album();
        albumManager.createAlbum(album);
        long id = album.getAlbumId();
        albumManager.deleteAlbum(album);
        albumManager.getAlbumById(id);

    }

    @Configuration  //je to konfigurace pro Spring
    @EnableTransactionManagement //bude řídit transakce u metod označených @Transactional
    public static class SpringConfig
    {
        @Autowired
        Environment env;

        @Bean
        public DataSource dataSource()
        {
            return new EmbeddedDatabaseBuilder()
                    .setType(DERBY)
                    .addScript("classpath:my-schema.sql")
                    .addScript("classpath:my-test-data.sql")
                    .build();

          /*  BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
            bds.setUrl(env.getProperty("jdbc.url"));
            bds.setUsername(env.getProperty("jdbc.user"));
            bds.setPassword(env.getProperty("jdbc.password"));
            return bds;*/
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
}
