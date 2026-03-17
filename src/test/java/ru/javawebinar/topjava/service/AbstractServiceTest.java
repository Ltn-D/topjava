package ru.javawebinar.topjava.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.TimingRules;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public abstract class AbstractServiceTest {

//    hibernate.cache.use_second_level_cache=false"
//    @Autowired(required = false)
//    protected JpaUtil jpaUtil;

//    @Autowired
//    private Environment environment;

    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;


    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThatThrownBy(runnable::run)
                .isInstanceOfAny(rootExceptionClass, Exception.class)
                .satisfies(e -> {
                    Throwable cause = e;
                    while (cause != null) {
                        if (rootExceptionClass.isInstance(cause)) return;
                        cause = cause.getCause();
                    }
                });
    }

//    hibernate.cache.use_second_level_cache=false
//    public boolean isJdbc() {
//        return environment.acceptsProfiles(Profiles.of("jdbc"));
//    }
}