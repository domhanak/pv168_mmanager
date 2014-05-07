package cz.muni.fi.pv168.musicManager.api;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Hany on 30.4.2014.
 */
@Configuration  //je to konfigurace pro Spring
@EnableTransactionManagement //bude řídit transakce u metod označených @Transactional
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {

        @Autowired
        Environment env;

        @Bean
        public DataSource dataSource()
        {
            BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
            bds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
            bds.setUrl(env.getProperty("jdbc.url"));
            bds.setUsername(env.getProperty("jdbc.user"));
            bds.setPassword(env.getProperty("jdbc.password"));
            return bds;
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
