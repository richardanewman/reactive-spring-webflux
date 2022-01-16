package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){

        return Flux.fromIterable(List.of("alex", "ben", "cloe"))
                .log(); //db or remote service call
    }

    public Mono<String> nameMono(){

        return Mono.just("alex")
                .log();
    }

//    public Mono<Mono<List<String>>> nameMonoMap(){ notice how flatMap "flattens" out the return type below
//
//        return Mono.just("alex")
//                .map(this::splitStringMono)
//                .log();
//    }
// ********vs*********
    public Mono<List<String>> nameMonoFlatMap(){

        return Mono.just("alex")
                .map(s -> s.toUpperCase())
                .flatMap(this::splitStringMono)
                .log();
    }

    public Mono<List<String>> splitStringMono(String name){
        var charArray = name.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
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

    public Flux<String> namesFluxFlatmap(int stringLength){

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s))
                .log(); //db or remote service call
    }

    public Flux<String> namesFluxFlatmapAsync(int stringLength){

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitStringWithDelay(s))
                .log(); //db or remote service call
    }

    public Flux<String> namesFluxConcatMap(int stringLength){

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.toUpperCase())
                .filter(s -> s.length() > stringLength)
                .concatMap(s -> splitStringWithDelay(s))
                .log(); //db or remote service call
    }

    public Flux<String> splitString(String name){
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitStringWithDelay(String name){
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
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
