package us.paradyme.function;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Hello hello = new Hello();
        hello.myHandler(new ScheduledEvent(),null);
    }
}
