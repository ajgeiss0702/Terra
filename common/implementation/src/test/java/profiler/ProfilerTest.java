package profiler;

import org.junit.jupiter.api.Test;

import com.dfsek.terra.api.profiler.Profiler;
import com.dfsek.terra.profiler.ProfilerImpl;


public class ProfilerTest {
    private static final Profiler PROFILER = new ProfilerImpl();
    
    private static void doThing() throws InterruptedException {
        PROFILER.push("thing");
        Thread.sleep(1);
        doOtherThing();
        thing4();
        PROFILER.pop("thing");
    }
    
    private static void doOtherThing() throws InterruptedException {
        PROFILER.push("thing2");
        Thread.sleep(2);
        doThirdOtherThing();
        thing4();
        PROFILER.pop("thing2");
    }
    
    private static void doThirdOtherThing() throws InterruptedException {
        PROFILER.push("thing3");
        Thread.sleep(2);
        PROFILER.pop("thing3");
    }
    
    private static void thing4() throws InterruptedException {
        PROFILER.push("thing4");
        Thread.sleep(2);
        PROFILER.pop("thing4");
    }
    
    @Test
    public void testProfiler() throws InterruptedException {
        //PROFILER.start();
        for(int i = 0; i < 100; i++) {
            doThing();
        }

        for(int i = 0; i < 100; i++) {
            doThirdOtherThing();
        }

        for(int i = 0; i < 100; i++) {
            doOtherThing();
        }
        PROFILER.stop();
        PROFILER.push("thing");
        PROFILER.push("thing2");
        PROFILER.start();
        PROFILER.pop("thing2");
        PROFILER.pop("thing");
        PROFILER.push("thing4");
        PROFILER.pop("thing4");

        PROFILER.getTimings().forEach((id, timings) -> System.out.println(id + ": " + timings.toString()));
    }
}
