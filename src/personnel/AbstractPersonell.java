package personnel;

/**
 * Abstract class containing functionality for personnel
 */
public abstract class AbstractPersonell implements Personnel{
    private int salary;
    protected AbstractPersonell(int salary) {
        this.salary = salary;
    }

    @Override
    public int getSalary() {
        return salary;
    }

}
