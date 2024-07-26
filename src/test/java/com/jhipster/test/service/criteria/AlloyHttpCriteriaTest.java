package com.jhipster.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AlloyHttpCriteriaTest {

    @Test
    void newAlloyHttpCriteriaHasAllFiltersNullTest() {
        var alloyHttpCriteria = new AlloyHttpCriteria();
        assertThat(alloyHttpCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void alloyHttpCriteriaFluentMethodsCreatesFiltersTest() {
        var alloyHttpCriteria = new AlloyHttpCriteria();

        setAllFilters(alloyHttpCriteria);

        assertThat(alloyHttpCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void alloyHttpCriteriaCopyCreatesNullFilterTest() {
        var alloyHttpCriteria = new AlloyHttpCriteria();
        var copy = alloyHttpCriteria.copy();

        assertThat(alloyHttpCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(alloyHttpCriteria)
        );
    }

    @Test
    void alloyHttpCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var alloyHttpCriteria = new AlloyHttpCriteria();
        setAllFilters(alloyHttpCriteria);

        var copy = alloyHttpCriteria.copy();

        assertThat(alloyHttpCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(alloyHttpCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var alloyHttpCriteria = new AlloyHttpCriteria();

        assertThat(alloyHttpCriteria).hasToString("AlloyHttpCriteria{}");
    }

    private static void setAllFilters(AlloyHttpCriteria alloyHttpCriteria) {
        alloyHttpCriteria.id();
        alloyHttpCriteria.name();
        alloyHttpCriteria.address();
        alloyHttpCriteria.modul();
        alloyHttpCriteria.api();
        alloyHttpCriteria.env();
        alloyHttpCriteria.hostname();
        alloyHttpCriteria.createTime();
        alloyHttpCriteria.updateTime();
        alloyHttpCriteria.distinct();
    }

    private static Condition<AlloyHttpCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getAddress()) &&
                condition.apply(criteria.getModul()) &&
                condition.apply(criteria.getApi()) &&
                condition.apply(criteria.getEnv()) &&
                condition.apply(criteria.getHostname()) &&
                condition.apply(criteria.getCreateTime()) &&
                condition.apply(criteria.getUpdateTime()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AlloyHttpCriteria> copyFiltersAre(AlloyHttpCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getAddress(), copy.getAddress()) &&
                condition.apply(criteria.getModul(), copy.getModul()) &&
                condition.apply(criteria.getApi(), copy.getApi()) &&
                condition.apply(criteria.getEnv(), copy.getEnv()) &&
                condition.apply(criteria.getHostname(), copy.getHostname()) &&
                condition.apply(criteria.getCreateTime(), copy.getCreateTime()) &&
                condition.apply(criteria.getUpdateTime(), copy.getUpdateTime()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
