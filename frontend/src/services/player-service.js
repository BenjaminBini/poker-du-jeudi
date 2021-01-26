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
        const currentYear = new Date().getFullYear();
        if (player && player.playerResults) {
            return player.playerResults.filter(pr => pr.session.season.year === currentYear)
                .map(pr => pr.result).reduce((acc, curr) => acc + curr, 0);
        }
        return 0;
    }
}