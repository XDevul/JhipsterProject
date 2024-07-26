package com.jhipster.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AlloyHttpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AlloyHttp getAlloyHttpSample1() {
        return new AlloyHttp().id(1L).name("name1").address("address1").modul("modul1").api("api1").env("env1").hostname("hostname1");
    }

    public static AlloyHttp getAlloyHttpSample2() {
        return new AlloyHttp().id(2L).name("name2").address("address2").modul("modul2").api("api2").env("env2").hostname("hostname2");
    }

    public static AlloyHttp getAlloyHttpRandomSampleGenerator() {
        return new AlloyHttp()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .modul(UUID.randomUUID().toString())
            .api(UUID.randomUUID().toString())
            .env(UUID.randomUUID().toString())
            .hostname(UUID.randomUUID().toString());
    }
}
