package ru.javawebinar.topjava.web.user.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.web.user.AbstractAdminRestControllerTest;


import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = JDBC, inheritProfiles = false)
public class JdbcAdminRestControllerTest extends AbstractAdminRestControllerTest {
}
