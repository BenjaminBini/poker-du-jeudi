<template>
  <div
    class="overflow-hidden bg-white divide-y divide-gray-200 rounded-lg shadow"
    v-if="results.length > 1"
  >
    <div class="px-4 py-5 bg-white sm:px-6">
      <div
        class="flex flex-wrap items-center justify-between -mt-2 -ml-4 sm:flex-nowrap"
      >
        <div class="mt-2 ml-4">
          <h3 class="text-xl font-bold leading-6 text-indigo-500">Résultats</h3>
        </div>
        <div
          class="flex-shrink-0 mt-2 ml-4 space-x-3"
          v-if="yearsPlayed.length > 1"
        >
          <YearFilter
            :year="0"
            label="Tout"
            :selected-year="selectedYear"
            @year-selected="yearSelected"
          ></YearFilter>
          <YearFilter
            v-for="year in yearsPlayed"
            :key="year"
            :year="year"
            :label="year"
            :selected-year="selectedYear"
            @year-selected="yearSelected"
          ></YearFilter>
        </div>
      </div>
      <!-- Content goes here-->
      <!-- We use less vertical padding on card headers on desktop than on body sections-->
    </div>
    <div class="px-4 py-5 sm:p-6">
      <ResultChart
        class="relative"
        :results="filteredResults"
        style="height: 400px"
      ></ResultChart>
    </div>
  </div>
</template>

<script>
import ResultChart from "./ResultChart";
import YearFilter from "./YearFilter";

export default {
  name: "ResultChartContainer",
  components: {
    YearFilter,
    ResultChart,
  },
  props: ["results", "yearsPlayed", "selectedYear"],
  data: () => ({}),
  computed: {
    filteredResults: function () {
      let results = [...this.results];
      return results
        .sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date))
        .filter(
          (r) =>
            this.selectedYear === 0 ||
            r.session.season.year === this.selectedYear
        );
    },
  },
  methods: {
    yearSelected(event, year) {
      this.$emit("year-selected", event, year);
    },
  },
  mounted() {},
};
</script>

<style scoped>
</style>