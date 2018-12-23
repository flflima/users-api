package br.com.dev.users.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LoginServiceTest.class, PerfilUserServiceTest.class, UserServiceTest.class })
public class AllServicesTests {

}
