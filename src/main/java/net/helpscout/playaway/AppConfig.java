package net.helpscout.playaway;

import com.google.common.collect.ImmutableMap;
import lombok.val;
import net.helpscout.playaway.data.DbContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static net.helpscout.playaway.AppConfig.DataSourceName.MASTER;
import static net.helpscout.playaway.AppConfig.DataSourceName.SLAVE;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan("net.helpscout")
public class AppConfig {

    public enum DataSourceName {
        MASTER, SLAVE
    }

    @Bean
    public DataSource dataSource() {
        val master = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("customers-master.sql")
                .setName("master")
                .build();

        val slave = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("customers-slave.sql")
                .setName("slave")
                .build();

        val routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DbContextHolder.getDbType();
            }
        };

        routingDataSource.setTargetDataSources(ImmutableMap.of(MASTER, master, SLAVE, slave));
        routingDataSource.setDefaultTargetDataSource(master);
        return routingDataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        val jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.H2);
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        val lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(dataSource);
        lemfb.setJpaVendorAdapter(jpaVendorAdapter());
        lemfb.setPackagesToScan("net.helpscout");
        return lemfb;
    }
}
