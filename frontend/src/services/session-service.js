import { Axios } from './axios-service'

export default {
    getSessions() {
        return Axios.get('/sessions/');
    },
    getSession(id) {
        return Axios.get('/sessions/' + id)
    }
}