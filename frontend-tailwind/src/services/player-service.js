import { Axios } from './axios-service'

export default {
    getPlayers() {
        return Axios.get('/players/');
    },
    getPlayer(id) {
       return Axios.get(`/players/${id}`);
    },
    getPlayerTotalResult(player) {
        if (player && player.playerResults) {
            return player.playerResults.map(pr => pr.result).reduce((acc, curr) => acc + curr);
        }
        return 0;
    },
    getCurrentSeasonResult(player) {
        if (player && player.playerResults) {
            const currentSeason = player.playerResults.map(pr => pr.session.season.year)
                .reduce((acc, curr) => acc > curr ? acc : curr);
            return player.playerResults.filter(pr => pr.session.season.year === currentSeason)
                .map(pr => pr.result).reduce((acc, curr) => acc + curr);
        }
        return 0;
    }
}