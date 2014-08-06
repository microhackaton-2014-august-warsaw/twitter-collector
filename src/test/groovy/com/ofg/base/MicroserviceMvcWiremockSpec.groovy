package com.ofg.base
import com.ofg.infrastructure.base.MvcWiremockIntegrationSpec
import com.ofg.microservice.Profiles
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import static com.ofg.microservice.Profiles.TEST
import static Profiles.TEST

@ContextConfiguration(classes = [ServiceDiscoveryStubbingApplicationConfiguration], loader = SpringApplicationContextLoader)
@ActiveProfiles(TEST)
class MicroserviceMvcWiremockSpec extends MvcWiremockIntegrationSpec {}
