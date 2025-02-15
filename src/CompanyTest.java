import static org.junit.Assert.*;

public class CompanyTest {

    @org.junit.Test
    public void add() {
        Company company = new Company();
        Employee e1 = new Employee(new Profile("name", "CS",new Date()));
        assertTrue(company.add(e1)); //testing adding a new employee
        assertFalse(company.add(e1)); //testing adding the same employee
        for(int i = 0; i < 3; i++){
            Employee e = new Employee(new Profile("name" + String.valueOf(i), "CS",new Date()));
            assertTrue(company.add(e));
        } //adding multiple to test grow
        Fulltime f1 = new Fulltime(new Profile("name", "CS",new Date()), 1);
        assertTrue(company.add(f1));
        Management m1 = new Management(new Profile("name", "CS",new Date()), 1, 1);
        assertTrue(company.add(m1));
        Management m2 = new Management(new Profile("name", "CS",new Date()), 1, 2);
        assertTrue(company.add(m2));
        Management m3 = new Management(new Profile("name", "CS",new Date()), 1, 3);
        assertTrue(company.add(m3));
        Parttime p1 = new Parttime(new Profile("name", "CS",new Date()), 5);
        assertTrue(company.add(p1));
    }

    @org.junit.Test
    public void remove() {
        Company company = new Company();
        Employee e1 = new Employee(new Profile("name", "CS",new Date()));
        assertFalse(company.remove(e1)); //testing that it can't remove, doesn't exist
        company.add(e1); //add
        assertTrue(company.remove(e1)); //testing whether it can remove
        assertFalse(company.remove(e1)); //testing that it can't remove, no longer exists

    }

    @org.junit.Test
    public void setHours() {
        Company company = new Company();
        Parttime p1 = new Parttime(new Profile("name", "CS",new Date()), 10.00);
        Parttime p2 = new Parttime(new Profile("name2", "CS",new Date()), 10.00);
        Management m1 = new Management(new Profile("name", "CS",new Date()), 1, 1);
        Fulltime f1 = new Fulltime(new Profile("name", "CS",new Date()), 1);
        company.add(f1);
        company.add(m1);
        company.add(p1);
        p1.setHours(50);
        assertTrue(company.setHours(p1)); //testing setting hours
        assertFalse(company.setHours(p2)); //testing against setting of a nonexistent employee
        assertTrue(company.setHours(p1)); //testing setting hours to the same value; should still be true
        assertFalse(company.setHours(m1)); 
        assertFalse(company.setHours(f1));

    }
}