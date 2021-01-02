import { Axios } from "./axios-service";

export default {
  getSessions() {
    return Axios.get("/sessions/");
  },
  getSession(id) {
    return Axios.get("/sessions/" + id);
  },
  getLatestSessionByYear(year) {
    return Axios.get("/sessions/latest/" + year);
  },
  getLatestSession() {
    return Axios.get("/sessions/latest/");
  },
  updatePlayerSessionResult(sessionId, playerId, result, buyIns) {
    return Axios.post(`/sessions/${sessionId}/player/${playerId}`, {
      result,
      buyIns,
    });
  },
  deletePlayerSessionResult(sessionId, playerId) {
    return Axios.post(`/sessions/${sessionId}/player/${playerId}/delete`);
  },
};
