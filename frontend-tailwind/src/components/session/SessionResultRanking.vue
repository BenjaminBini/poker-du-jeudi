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
                  class="px-0 py-3 text-xs font-medium tracking-wider text-right text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Buy-ins
                </th>
                <th
                  class="px-0 py-3 pr-4 text-xs font-medium tracking-wider text-right text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Résultat
                </th>
                <th class="bg-white" scope="col" v-if="showEditControls"></th>
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
                <template
                  v-if="
                    loadingPlayersToDelete.filter((p) => p == row.playerId)
                      .length > 0
                  "
                >
                  <td colspan="100">
                    <div class="flex justify-center py-3">
                      <tw-spinner></tw-spinner>
                    </div>
                  </td>
                </template>
                <template v-else>
                  <td
                    class="px-3 py-2 text-sm font-medium text-gray-900 whitespace-nowrap"
                  >
                    {{ row.rank }}
                  </td>
                  <td
                    class="px-0 py-3 text-sm font-medium text-indigo-600 whitespace-nowrap"
                  >
                    <router-link :to="'/players/' + row.playerId">{{
                      row.playerName
                    }}</router-link>
                  </td>
                  <td class="px-0 text-sm text-gray-500 whitespace-nowrap">
                    <session-buy-ins-edit
                      :showEditControls="showEditControls"
                      :player-id="row.playerId"
                      :buy-ins="row.buyIns"
                      :loading="
                        loadingPlayerBuyIns.filter((id) => row.playerId == id)
                          .length > 0
                      "
                      @edit-buy-ins="
                        (result) =>
                          $emit('edit-result', {
                            ...result,
                            result: row.result,
                            type: 'buy-ins',
                          })
                      "
                    />
                  </td>
                  <td class="pl-0 pr-4 text-sm text-gray-900 whitespace-nowrap">
                    <session-result-edit
                      :result="row.result"
                      :player-id="row.playerId"
                      :show-edit-controls="showEditControls"
                      :loading="
                        loadingPlayerResults.filter((id) => row.playerId == id)
                          .length > 0
                      "
                      @edit-result="
                        (result) =>
                          $emit('edit-result', {
                            ...result,
                            buyIns: row.buyIns,
                            type: 'result',
                          })
                      "
                    ></session-result-edit>
                  </td>
                  <td v-if="showEditControls">
                    <button
                      @click="openDeleteModal(row)"
                      class="flex items-center justify-center w-6 h-6 text-red-500 border border-red-500 rounded-md focus:outline-none hover:bg-red-500 hover:text-white"
                    >
                      <x-icon class="w-4 h-4"></x-icon>
                    </button>
                  </td>
                </template>
              </tr>
              <tr
                :class="{
                  'bg-white': results.length % 2 !== 0,
                  'bg-gray-50': results.length % 2 === 0,
                }"
              >
                <td
                  colspan="100"
                  class="px-3 py-1 text-sm italic text-center text-gray-500 whitespace-nowrap"
                >
                  {{ -results.map((r) => r.result).reduce((a, b) => a + b) }} €
                  de monnaie
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <tw-modal
      title="Confirmer la suppression"
      :text="`Confirmer la suppression de la participation de ${playerToDeleteFirstName}`"
      confirm-label="Oui, supprimer"
      cancel-label="Non"
      :is-open="showDeleteModal"
      @confirm="confirmDelete"
      @cancel="closeDeleteModal"
    ></tw-modal>
  </div>
</template>

<script>
import SessionBuyInsEdit from "./SessionBuyInsEdit.vue";
import { XIcon } from "@vue-hero-icons/outline";
import SessionResultEdit from "@/components/session/SessionResultEdit.vue";
import settings from "@/settings";
export default {
  name: "SessionResultRanking",
  props: [
    "results",
    "title",
    "loading",
    "editMode",
    "loadingPlayerResults",
    "loadingPlayerBuyIns",
    "loadingPlayersToDelete",
  ],
  data: function () {
    return {
      showEditControls: false,
      showDeleteModal: false,
      playerToDeleteId: undefined,
      playerToDeleteFirstName: undefined,
    };
  },
  components: {
    SessionBuyInsEdit,
    XIcon,
    SessionResultEdit,
  },
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
  methods: {
    openDeleteModal: function (row) {
      this.playerToDeleteId = row.playerId;
      this.playerToDeleteFirstName = row.playerName;
      this.showDeleteModal = true;
    },
    confirmDelete: async function () {
      this.$emit("delete-result", {
        playerId: this.playerToDeleteId,
      });
      this.showDeleteModal = false;
    },
    closeDeleteModal: function () {
      this.showDeleteModal = false;
    },
  },
  mounted: function () {
    this.showEditControls = this.editMode
      ? this.editMode
      : settings.forceEditMode;
  },
};
</script>

<style scoped>
</style>