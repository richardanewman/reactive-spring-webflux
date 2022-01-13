package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){

        return Flux.fromIterable(List.of("alex", "ben", "cloe"))
                .log(); //db or remote service call
    }

    public Mono<String> nameMono(){

        return Mono.just("alex")
                .log();
    }

    public Flux<String> namesFluxMap(){

        return Flux.fromIterable(List.of("alex", "ben", "cloe"))
                //.map(s -> s.toUpperCase())
                .map(String::toUpperCase)
                .log(); //db or remote service call
    }

    public Flux<String> namesFluxImmutability(){
        //demonstrates flux immutability
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "cloe"));
        namesFlux.map(String::toUpperCase);

        return namesFlux;
    }


    public Flux<String> namesFluxFilter(int stringLength){
        //filter string whose length is greater than 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s) //4-ALEX, 5-CHLOE
                .log(); //db or remote service call
    }

    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> {
                    System.out.println("Name is " + name);
                });

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> {
                    System.out.println("Mono name is " + name);
                });

    }
}
