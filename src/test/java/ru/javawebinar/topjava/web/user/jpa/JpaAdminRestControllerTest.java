package ru.javawebinar.topjava.web.user.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.web.user.AbstractAdminRestControllerTest;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = JPA, inheritProfiles = false)
public class JpaAdminRestControllerTest extends AbstractAdminRestControllerTest {
}
