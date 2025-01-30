//Base to url: https://api.themoviedb.org/3/
//URL da api: /movie/now_playing?api_key=b2de013b0dc09bf310dbc0f42353f745&language=pt=BR
import axios from "axios";

const api = axios.create({
    baseURL: "https://fakestoreapi.com"
});

export default api