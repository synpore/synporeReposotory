package com.synpore.Java8Characteristic;

import java.util.Optional;

public class OptionalsDemon {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("bam");

        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"


        String s=null;
        Optional<String> optional2 = Optional.ofNullable(s);
        optional2.ifPresent((p)-> System.out.println(p));//print nothing
    }
}
