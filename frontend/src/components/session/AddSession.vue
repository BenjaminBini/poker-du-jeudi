<template>
  <div>
    <form id="create-session" @submit="save">
      <div>
        <div class="md:grid md:grid-cols-3 md:gap-6">
          <div class="md:col-span-1">
            <div class="px-4 sm:px-0">
              <h3 class="text-lg font-medium leading-6 text-white">
                Informations générales
              </h3>
            </div>
          </div>
          <div class="mt-5 md:mt-0 md:col-span-2">
            <div class="overflow-hidden rounded-md shadow">
              <div class="px-4 py-5 bg-white sm:p-6">
                <div class="grid grid-cols-6 gap-6">
                  <div class="col-span-6 sm:col-span-3">
                    <label
                      for="season"
                      class="block text-sm font-medium text-gray-700"
                      >Saison</label
                    >
                    <select
                      id="season"
                      name="season"
                      v-model="selectedSeasonId"
                      class="block w-full px-3 py-2 mt-1 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    >
                      <option
                        v-for="season in seasons"
                        :key="season.seasonId"
                        :value="season.seasonId"
                      >
                        {{ season.name }}
                      </option>
                    </select>
                  </div>

                  <div class="col-span-6 sm:col-span-3">
                    <label
                      for="date"
                      class="block text-sm font-medium text-gray-700"
                      >Date</label
                    >
                    <div class="relative">
                      <input
                        v-model="selectedDate"
                        type="date"
                        name="date"
                        id="date"
                        class="block w-full px-3 py-2 mt-1 bg-white border rounded-md shadow-sm focus:outline-none sm:text-sm"
                        :class="
                          isSubmitted && !selectedDate
                            ? 'border-red-300 text-red-900 placeholder-red-300 focus:ring-red-500 focus:border-red-500 '
                            : 'border-gray-300  focus:ring-indigo-500 focus:border-indigo-500'
                        "
                      />
                      <div
                        v-if="isSubmitted && !selectedDate"
                        class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none"
                      >
                        <exclamation-circle-icon
                          class="w-5 h-5 text-red-500"
                        ></exclamation-circle-icon>
                      </div>
                    </div>
                  </div>

                  <div class="col-span-6 sm:col-span-3">
                    <label
                      for="place"
                      class="block text-sm font-medium text-gray-700"
                    >
                      Lieu
                    </label>
                    <div class="relative">
                      <select
                        v-model="selectedPlaceId"
                        id="place"
                        name="place"
                        class="block w-full px-3 py-2 mt-1 bg-white border rounded-md shadow-sm focus:outline-none sm:text-sm"
                        :class="
                          isSubmitted && !selectedPlaceId
                            ? 'border-red-300 text-red-900 placeholder-red-300 focus:ring-red-500 focus:border-red-500 '
                            : 'border-gray-300  focus:ring-indigo-500 focus:border-indigo-500'
                        "
                      >
                        <option
                          v-for="place in places"
                          :key="place.placeId"
                          :value="place.placeId"
                        >
                          {{ place.name }}
                        </option>
                      </select>
                      <div
                        v-if="isSubmitted && !selectedPlaceId"
                        class="absolute inset-y-0 right-0 flex items-center pr-8 pointer-events-none"
                      >
                        <exclamation-circle-icon
                          class="w-5 h-5 text-red-500"
                        ></exclamation-circle-icon>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="hidden sm:block" aria-hidden="true">
        <div class="py-5">
          <div class="border-t border-gray-200"></div>
        </div>
      </div>

      <div class="mt-10 sm:mt-0">
        <div class="md:grid md:grid-cols-3 md:gap-6">
          <div class="md:col-span-1">
            <div class="px-4 sm:px-0">
              <h3 class="text-lg font-medium leading-6 text-gray-900">
                Joueurs
              </h3>
            </div>
          </div>
          <div class="mt-5 md:mt-0 md:col-span-2">
            <div class="overflow-auto rounded-md shadow max-h-40vh">
              <div class="px-4 py-5 bg-white sm:p-6">
                <div class="grid grid-cols-2 gap-4 md:grid-cols-3">
                  <selectable-player
                    v-for="player in players"
                    :key="player.playerId"
                    :player="player"
                    :selected="
                      selectedPlayers.filter(
                        (p) => p.playerId === player.playerId
                      ).length === 1
                    "
                    @player-select="addPlayer"
                    @player-remove="removePlayer"
                  >
                  </selectable-player>
                </div>
              </div>
            </div>
            <div class="mt-6 overflow-auto rounded-md shadow max-h-1/2-vh">
              <div class="px-4 py-5 bg-white sm:p-6">
                <label class="block mb-3 text-sm font-medium text-gray-700"
                  >Joueurs sélectionnés</label
                >
                <div
                  v-if="selectedPlayers.length === 0"
                  class="text-sm italic text-gray-500"
                >
                  Aucun joueur sélectionné
                </div>
                <div class="grid grid-cols-2 gap-4 md:grid-cols-3">
                  <selectable-player
                    v-for="player in selectedPlayers"
                    :key="player.playerId"
                    :player="player"
                    @player-remove="removePlayer"
                    :selected="true"
                    :force-select="true"
                  >
                  </selectable-player>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="flex justify-end mt-6">
        <router-link
          to="/sessions"
          class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Annuler
        </router-link>
        <button
          type="submit"
          class="inline-flex justify-center px-4 py-2 ml-3 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Save
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import SessionService from "@/services/session-service";
import PlayerService from "@/services/player-service";
import SeasonService from "@/services/season-service";
import PlaceService from "@/services/place-service";
import SelectablePlayer from "@/components/session/SelectablePlayer.vue";
import { ExclamationCircleIcon } from "@vue-hero-icons/outline";

export default {
  components: { SelectablePlayer, ExclamationCircleIcon },
  data: function () {
    return {
      seasons: [],
      places: [],
      players: [],
      selectedPlaceId: undefined,
      selectedDate: undefined,
      selectedSeasonId: undefined,
      selectedPlayers: [],
      isSubmitted: false,
    };
  },
  methods: {
    fetchPlaces: async function () {
      const response = await PlaceService.getPlaces();
      this.places = response.data;
    },
    fetchPlayers: async function () {
      const response = await PlayerService.getPlayers();
      this.players = response.data;
    },
    fetchSeasons: async function () {
      const response = await SeasonService.getSeasons();
      this.seasons = response.data.sort((s1, s2) => s2.year - s1.year);
      if (this.seasons.length > 0) {
        this.selectedSeasonId = this.seasons[0].seasonId;
      }
    },
    addPlayer: function (player) {
      this.selectedPlayers.push(player);
    },
    removePlayer: function (player) {
      this.selectedPlayers = this.selectedPlayers.filter(
        (p) => p.playerId != player.playerId
      );
    },
    save: async function (e) {
      e.preventDefault();
      this.isSubmitted = true;
      if (
        !this.selectedPlaceId ||
        !this.selectedDate ||
        !this.selectedSeasonId
      ) {
        return;
      }

      const requestBody = {
        playerIds: this.selectedPlayers.map((p) => p.playerId),
        seasonId: this.selectedSeasonId,
        date: new Date(this.selectedDate),
        placeId: this.selectedPlaceId,
      };
      const response = await SessionService.addSession(requestBody);
      const sessionId = response.data.sessionId;
      this.$router.push(`/sessions/${sessionId}`);
    },
  },
  mounted() {
    Promise.all([this.fetchSeasons(), this.fetchPlaces(), this.fetchPlayers()]);
  },
};
</script>

<style>
</style>