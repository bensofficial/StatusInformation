package org.benjaminschmitz.statusinformation.api;

public class API {
    private final SubstitutionPlanApi substitutionPlanApi;

    public API() {
        substitutionPlanApi = new SubstitutionPlanApi();
    }

    public String getSubstitutionPlan() {
        return substitutionPlanApi.get();
    }
}
