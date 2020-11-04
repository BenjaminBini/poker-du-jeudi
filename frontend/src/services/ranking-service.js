import { Axios } from './axios-service'

export default {
    getUntil(sessionId) {
        return Axios.get('/ranking/until/' + sessionId);
    }
}