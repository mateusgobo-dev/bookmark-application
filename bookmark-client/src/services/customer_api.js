import axios from "axios";
const customer_api = axios.create({
    baseURL: "http://localhost:8082",
    headers: { "Content-Type": "application/json" }
});
export default customer_api;