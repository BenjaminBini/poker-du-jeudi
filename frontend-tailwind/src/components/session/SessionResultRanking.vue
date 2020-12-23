<template>
  <div
    class="flex flex-col overflow-hidden border-gray-200 shadow sm:rounded-lg"
  >
    <div
      class="p-2 pl-3 font-bold tracking-wide text-center text-indigo-600 bg-white text-l"
    >
      <h2>{{ title }}</h2>
    </div>
    <div class="-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
      <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
        <div class="overflow-hidden border-t border-b border-gray-200">
          <div
            v-if="loading"
            class="flex items-center justify-center py-6 bg-white"
          >
            <tw-spinner></tw-spinner>
          </div>
          <table v-if="!loading" class="min-w-full divide-y divide-gray-200">
            <thead>
              <tr>
                <th
                  class="px-3 py-2 text-xs font-medium tracking-wider text-left text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  #
                </th>
                <th
                  class="px-0 py-3 text-xs font-medium tracking-wider text-left text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Joueur
                </th>
                <th
                  class="px-0 py-3 text-xs font-medium tracking-wider text-left text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Buy-ins
                </th>
                <th
                  class="px-0 py-3 text-xs font-medium tracking-wider text-left text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Total
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(row, index) in table"
                :key="row.playerId"
                :class="{
                  'bg-gray-50': index % 2 === 0,
                  'bg-white': index % 2 !== 0,
                }"
              >
                <td
                  class="px-3 py-2 text-sm font-medium text-gray-900 whitespace-nowrap"
                >
                  {{ row.rank }}
                </td>
                <td
                  class="px-0 py-3 text-sm font-medium text-indigo-600 whitespace-nowrap"
                  v-if="!row.isActive"
                >
                  <router-link :to="'/players/' + row.playerId">{{
                    row.playerName
                  }}</router-link>
                </td>
                <td class="px-0 py-3 text-sm text-gray-500 whitespace-nowrap">
                  {{ row.buyIns }}
                </td>
                <td class="px-0 py-3 text-sm text-gray-900 whitespace-nowrap">
                  {{ row.result }} €
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SessionResultRanking",
  props: ["results", "title", "loading"],
  computed: {
    table: function () {
      if (!Array.isArray(this.results)) return [];
      const results = [...this.results];
      results.sort((r1, r2) => r2.result - r1.result);
      return results.map((r, i) => {
        return {
          rank: i + 1,
          playerName: r.player.firstName,
          playerId: r.player.playerId,
          result: r.result,
          buyIns: r.buyIns,
        };
      });
    },
  },
};
</script>

<style scoped>
</style>