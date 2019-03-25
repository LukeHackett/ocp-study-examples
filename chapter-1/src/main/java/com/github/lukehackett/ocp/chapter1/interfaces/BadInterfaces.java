package com.github.lukehackett.ocp.chapter1.interfaces;

public class BadInterfaces {

    @FunctionalInterface
    interface Speak {
        public abstract void speak(String message);

        static void shout() {  }

        // does not compile - cannot be default and static
        // default static void whisper() {}

    }

    @FunctionalInterface
    interface Chatter {
        void chatter(String[] messages);

        static void shout() {  }

        // does not compile - cannot be final as it will never be implementable
        // final void whisper() {}
        // default final void whisper2() {}
    }

    // Does not compile with below annotation, as is not a valid functional interface
    // @FunctionalInterface
    interface Speach extends Speak, Chatter {

    }

}
