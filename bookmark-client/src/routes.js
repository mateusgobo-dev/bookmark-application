import {BrowserRouter, Routes, Route} from "react-router-dom";

import "./pages/Home"
import "./pages/Produtos"
import Home from "./pages/Home";
import Filme from "./pages/Produtos";
import Header from "./components/Header";
import Erro from "./pages/Erro";

//themoviedb.org
function RoutesApp(){
    return (
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={<Home/>}></Route>
                <Route path="/filme/:id" element={<Filme/>}></Route>
                <Route path="*" element={<Erro />}></Route>
            </Routes>
        </BrowserRouter>
    )
}

export default RoutesApp;