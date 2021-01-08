import AddSession from "@/components/session/AddSession";
import Home from "@/components/Home";
import Login from "../components/Login";
import Player from "../components/player/player-detail/Player";
import Players from "../components/player/players-list/Players";
import Session from "@/components/session/Session";
import Sessions from "../components/Sessions";

const routes = [
  {
    path: "/",
    component: Home,
    meta: {
      label: "Accueil",
      pageTitle: "Poker du jeudi",
      displayInMenu: true,
    },
  },
  {
    path: "/login",
    component: Login,
    meta: {
      pageTitle: "Se connecter",
      displayInMenu: false,
    },
  },
  {
    path: "/sessions",
    component: Sessions,
    meta: {
      label: "Sessions",
      displayInMenu: true,
    },
  },
  {
    path: "/sessions/add",
    component: AddSession,
    meta: {
      pageTitle: "Nouvelle session",
      displayInMenu: false,
    },
  },
  {
    path: "/sessions/:id",
    component: Session,
    meta: {
      displayInMenu: false,
    },
  },
  {
    path: "/players",
    component: Players,
    meta: {
      label: "Joueurs",
      displayInMenu: true,
    },
  },
  {
    path: "/players/:id",
    component: Player,
    meta: {
      displayInMenu: false,
    },
  },
];

export default routes;
