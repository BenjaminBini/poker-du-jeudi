import { Axios } from "./axios-service";

export default {
  getPlayers() {
    return Axios.get("/players/");
  },
  getPlayer(id) {
    return Axios.get(`/players/${id}`);
  },
  getPlayerTotalResult(player) {
    if (player && player.playerResults) {
      return player.playerResults
        .map((pr) => pr.result)
        .reduce((acc, curr) => acc + curr);
    }
    return 0;
  },
};
