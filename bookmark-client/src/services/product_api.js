import axios from "axios";

const product_api = axios.create({
    baseURL: "https://fakestoreapi.com"
});

export  default product_api;