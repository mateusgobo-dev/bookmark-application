import axios from "axios";
const bookmarkApi = axios.create({
    baseURL: "http://localhost:8081"
});
export default bookmarkApi;