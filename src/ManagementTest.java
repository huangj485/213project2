import org.junit.Test;

import static org.junit.Assert.*;

public class ManagementTest {

    @Test
    public void calculatePayment() {
        Management m1 = new Management(new Profile("name", "CS",new Date()), 26, 1);
        assertEquals(m1.toString(), "name::CS::" + new Date().toString() + "::Payment $0.00::FULL TIME::Annual Salary $26.00::Manager Compensation $192.31");
        //asserting data is the same as it should be; payment = 0

        m1.calculatePayment();
        assertEquals(m1.toString(), "name::CS::" + new Date().toString() + "::Payment $193.31::FULL TIME::Annual Salary $26.00::Manager Compensation $192.31");
        //asserting data is the same as it should be; payment = 193.31

        m1.calculatePayment();
        assertEquals(m1.toString(), "name::CS::" + new Date().toString() + "::Payment $193.31::FULL TIME::Annual Salary $26.00::Manager Compensation $192.31");
        //asserting data doesn't change after calculation with no changes

        Management m2 = new Management(new Profile("name", "CS",new Date()), 26, 2);
        m2.calculatePayment();
        assertEquals(m2.toString(), "name::cs::" + new Date().toString() + "::Payment $366.38::FULL TIME::Annual Salary $26.00::Department Head Compensation $365.38");
        Management m3 = new Management(new Profile("name", "CS",new Date()), 26, 3);
        assertEquals(m2.toString(), "name::cs::" + new Date().toString() + "::Payment $462.54::FULL TIME::Annual Salary $26.00::Director Compensation $461.54");
    }
}