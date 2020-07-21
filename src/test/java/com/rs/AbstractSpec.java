package com.rs;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rs.service.IdGeneratorService;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RSApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = {RSProfile.UNITTEST})
public abstract class AbstractSpec {
  @Autowired
  protected IdGeneratorService idGeneratorService;
}
