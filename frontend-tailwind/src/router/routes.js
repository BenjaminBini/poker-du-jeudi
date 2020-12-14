import Sessions from "../components/Sessions";
import Players from "../components/player/players-list/Players";
import Player from "../components/player/player-detail/Player";
import Home from "@/components/Home";
import Session from "@/components/session/Session";

const routes = [
    {
        path: "/",
        component: Home,
        meta: {
            label: "Accueil",
            pageTitle: "Poker du jeudi",
            displayInMenu: true
        }
    }, {
        path: "/sessions",
        component: Sessions,
        meta: {
            label: "Sessions",
            displayInMenu: true
        },
    }, {
        path: "/sessions/:id",
        component: Session,
        meta: {
            displayInMenu: false
        },
    }, {
        path: "/players",
        component: Players,
        meta: {
            label: "Joueurs",
            displayInMenu: true
        },
    }, {
        path: "/players/:id",
        component: Player,
        meta:  {
            displayInMenu: false,
        }
    },
];

export default routes;
