package edu.agh.reactive;

import java.util.Objects;

public class SuperMethod {
    private String age;
    private Integer money;
    private int prob;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuperMethod)) return false;
        SuperMethod that = (SuperMethod) o;
        return prob == that.prob &&
                age.equals(that.age) &&
                money.equals(that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, money, prob);
    }
}
