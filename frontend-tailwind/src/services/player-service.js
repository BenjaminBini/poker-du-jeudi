import { Axios } from './axios-service'

export default {
    getPlayers() {
        return Axios.get('/players/');
    },
    getPlayer(id) {
       return Axios.get(`/players/${id}`);
    }
}