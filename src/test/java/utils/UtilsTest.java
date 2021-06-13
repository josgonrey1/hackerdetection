package utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtilsTest.class)
public class UtilsTest {

    @Test
    public void timeCalculationTest() throws ParseException {
        long minutes = Utils.timeCalculation("Wed, 4 Jul 2001 12:08:56 -0700", "Wed, 4 Jul 2001 12:09:56 -0700");
        assertEquals(minutes, 6);
    }
}