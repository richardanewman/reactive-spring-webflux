package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {

        var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        //step verifier expectNext only. create automatically invokes subscribe
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "cloe")
                .verifyComplete();

        //step verifier expectNextCount only
        StepVerifier.create(namesFlux)
                .expectNextCount(3)
                .verifyComplete();

        //mixing expectNext and expectNextCount: equal to all expected
        StepVerifier.create(namesFlux)
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {

        var namesFlux = fluxAndMonoGeneratorService.namesFluxMap();

        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "CLOE")
                .verifyComplete();
    }

    @Test
    void namesFluxImmutability() {

        var namesFlux = fluxAndMonoGeneratorService.namesFluxImmutability();

        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "cloe")
                .verifyComplete();
    }

    @Test
    void namesFluxFilter() {

        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxFilter(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatmap() {

        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatmap(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatmapAsync() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatmapAsync(stringLength);

        StepVerifier.create(namesFlux)
//                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatMap() {
        int stringLength = 3;

        var namesFlux = fluxAndMonoGeneratorService.namesFluxConcatMap(stringLength);

        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMap() {

        var nameMono = fluxAndMonoGeneratorService.nameMonoFlatMap();

        StepVerifier.create(nameMono)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }
}