package ru.job4j.todo.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Egor Bekhterev
 * @date: 10.03.2023
 * @project: job4j_todo
 */
@Configuration
public class HibernateConfiguration {

    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
